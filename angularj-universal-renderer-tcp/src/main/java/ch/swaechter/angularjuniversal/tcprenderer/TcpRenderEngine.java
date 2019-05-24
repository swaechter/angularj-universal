package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.exception.RenderException;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @NotNull
    private static final String NODE_PORT_ENVIRONMENT_VARIABLE_NAME = "NODEPORT";

    /**
     * Object mapper used to serialize/deserialize TCP request and responses.
     */
    @NotNull
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Create a new TCP based render engine that will access a NodeJS server for rendering
     */
    public TcpRenderEngine() {
    }

    /**
     * Start working and handle all incoming requests and resolve them. The engine will work as long it receives a valid
     * and non optional request and will shutdown itself as soon it received an optional request from the queue.
     *
     * @param renderRequests      Blocking queue with requests to read from
     * @param renderConfiguration Render configuration with the all required information
     */
    @Override
    public void startWorking(@NotNull BlockingQueue<Optional<RenderRequest>> renderRequests, @NotNull RenderConfiguration renderConfiguration) {
        try {
            // Start the Node.js render service
            @NotNull
            ProcessBuilder processBuilder = new ProcessBuilder(renderConfiguration.getNodePath(), renderConfiguration.getServerBundleFile().getAbsolutePath());
            @NotNull
            Map<String, String> processEnvironment = processBuilder.environment();
            processEnvironment.put(NODE_PORT_ENVIRONMENT_VARIABLE_NAME, String.valueOf(renderConfiguration.getNodePort()));

            @NotNull
            Process process = processBuilder.start();

            while (true) {
                Optional<RenderRequest> renderRequestItem = renderRequests.take();

                if (renderRequestItem.isPresent()) {
                    // Get the render request item
                    @NotNull
                    RenderRequest renderRequest = renderRequestItem.get();

                    try {
                        // Create the socket and initialize the writer reader
                        @NotNull
                        Socket socket = new Socket("localhost", renderConfiguration.getNodePort());
                        @NotNull
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        @NotNull
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        // Write the request
                        @NotNull
                        TcpRequest tcpRequest = new TcpRequest(renderRequest.getUuid(), renderRequest.getUri(), renderConfiguration.getTemplateContent());
                        writer.println(objectMapper.writeValueAsString(tcpRequest));
                        writer.flush();

                        // Read the response
                        @NotNull
                        TcpResponse tcpResponse = objectMapper.readValue(reader, TcpResponse.class);

                        // Get the error message if an error occurred on the render server
                        @Nullable
                        String errorMessage = tcpResponse.getError();

                        // check if an error occurred
                        if (errorMessage == null) {
                            renderRequest.getFuture().complete(tcpResponse.getHtml());
                        } else {
                            throw new RenderException(errorMessage);
                        }
                    } catch (Throwable exception) {
                        exception.printStackTrace();
                        if (exception instanceof RenderException) {
                            renderRequest.getFuture().completeExceptionally(exception);
                        } else {
                            renderRequest.getFuture().completeExceptionally(new RenderException(exception));
                        }
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
