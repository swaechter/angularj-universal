package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * The class TcpRenderEngine provides a NodeJS and TCP  based implementation of the render engine.
 *
 * @author Simon Wächter
 */
public class TcpRenderEngine implements RenderEngine {

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
            while (true) {
                Optional<RenderRequest> renderRequestItem = renderRequests.take();
                if (renderRequestItem.isPresent()) {
                    try {
                        // Get the render request item
                        RenderRequest renderRequest = renderRequestItem.get();

                        // Create the socket and initialize the writer reader
                        Socket socket = new Socket("localhost", 9090);
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        // Write the request
                        TcpRequest tcpRequest = new TcpRequest(renderRequest.getUuid(), renderRequest.getUri(), "<app-root></app-root>");
                        writer.println(objectMapper.writeValueAsString(tcpRequest));
                        writer.flush();

                        // Read the response
                        TcpResponse tcpResponse = objectMapper.readValue(reader, TcpResponse.class);
                        renderRequest.getFuture().complete(tcpResponse.getHtml());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
