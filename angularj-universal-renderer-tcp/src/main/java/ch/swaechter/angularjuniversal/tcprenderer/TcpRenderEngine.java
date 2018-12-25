package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * The class TcpRenderEngine provides a NodeJS and TCP  based implementation of the render engine.
 *
 * @author Simon Wächter
 */
public class TcpRenderEngine implements RenderEngine {

    /**
     * Name of the environment variable passed to Node.js to indicate the port.
     */
    private static final String NODE_PORT_ENVIRONMENT_VARIABLE_NAME = "NODEPORT";

    /**
     * Object mapper used to serialize/deserialize TCP request and responses.
     */
    private final ObjectMapper objectMapper;

    /**
     * Create a new TCP based render engine that will access a NodeJS server for rendering
     */
    public TcpRenderEngine() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Start working and handle all incoming requests and resolve them. The engine will work as long it receives a valid
     * and non optional request and will shutdown itself as soon it received an optional request from the queue.
     *
     * @param renderRequests      Blocking queue with requests to read from
     * @param renderConfiguration Render configuration with the all required information
     */
    @Override
    public void startWorking(BlockingQueue<Optional<RenderRequest>> renderRequests, RenderConfiguration renderConfiguration) {
        try {
            // Start the Node.js render service
            ProcessBuilder processBuilder = new ProcessBuilder(renderConfiguration.getNodePath(), renderConfiguration.getServerBundleFile().getAbsolutePath());
            Map<String, String> processEnvironment = processBuilder.environment();
            processEnvironment.put(NODE_PORT_ENVIRONMENT_VARIABLE_NAME, String.valueOf(renderConfiguration.getNodePort()));
            Process process = processBuilder.start();

            while (true) {
                Optional<RenderRequest> renderRequestItem = renderRequests.take();
                if (renderRequestItem.isPresent()) {
                    try {
                        // Get the render request item
                        RenderRequest renderRequest = renderRequestItem.get();

                        // Create the socket and initialize the writer reader
                        Socket socket = new Socket("localhost", renderConfiguration.getNodePort());
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        // Write the request
                        TcpRequest tcpRequest = new TcpRequest(renderRequest.getUuid(), renderRequest.getUri(), renderConfiguration.getTemplateContent());
                        writer.println(objectMapper.writeValueAsString(tcpRequest));
                        writer.flush();

                        // Read the response
                        TcpResponse tcpResponse = objectMapper.readValue(reader, TcpResponse.class);
                        renderRequest.getFuture().complete(tcpResponse.getHtml());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    process.destroy();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
