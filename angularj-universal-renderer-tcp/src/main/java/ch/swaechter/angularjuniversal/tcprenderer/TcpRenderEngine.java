package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * The class TcpRenderEngine provides a NodeJS and TCP  based implementation of the render engine.
 *
 * @author Simon Wächter
 */
public class TcpRenderEngine implements RenderEngine {

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
            // Start the Node.js process with the server bundle
            Process process = new ProcessBuilder(getNodeBinaryPath(), renderConfiguration.getServerBundleFile().getAbsolutePath()).start();
//            String scriptFilePath = renderConfiguration.getServerBundleFile();
            while(true) {
                Optional<RenderRequest> renderRequest = renderRequests.take();
                if(renderRequest.isPresent()) {

                } else {
                    break;
                }
            }
            // Destroy the process
            process.destroy();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void launchNodeJsServer() {

    }

    private String getNodeBinaryPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("win") ? "node.exe" : "node";
    }
}
