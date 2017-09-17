package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * The class V8RenderEngine provides a NodeJS/V8 based implementation of the render engine.
 *
 * @author Simon Wächter
 */
public class V8RenderEngine implements RenderEngine {

    /**
     * Current render request which is handled.
     */
    private RenderRequest currentrenderrequest;

    /**
     * NodeJS instance that will load the server bundle.
     */
    private NodeJS nodejs;

    /**
     * Memory manager that handles all memory and provides a garbage collection.
     */
    private MemoryManager memorymanager;

    /**
     * Render adapter that will be used for rendering the module.
     */
    private V8Object renderadapter;

    /**
     * Start working and handle all incoming requests and resolve them. The engine will work as long it receives a valid
     * and non optional request and will shutdown itself as soon it received an ooptional request from the queue.
     *
     * @param renderrequests      Blocking queue with requests to read from
     * @param renderconfiguration Render configuration with the all required information
     */
    @Override
    public void startWorking(BlockingQueue<Optional<RenderRequest>> renderrequests, RenderConfiguration renderconfiguration) {
        try {
            // Create the NodeJS server and get the V8 engine
            nodejs = NodeJS.createNodeJS();

            // Create the memory manager to enable auto garbage collection
            memorymanager = new MemoryManager(nodejs.getRuntime());

            // Register a method that represents the register function
            nodejs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
                renderadapter = v8Array.getObject(0);
            }, "registerRenderAdapter");

            // Register a method that represents the finished render request function
            nodejs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
                String uuid = v8Array.getString(0);
                String html = v8Array.getString(1);
                V8Object error = v8Array.getObject(2);
                if (error == null) {
                    currentrenderrequest.getFuture().complete(html);
                } else {
                    System.err.println("The V8 render received a render response with an error for " + uuid + ": " + error);
                }
            }, "receiveRenderedPage");

            // Load the server bundle
            nodejs.exec(renderconfiguration.getServerBundleFile());
            nodejs.handleMessage();

            // Load the HTML
            V8Array parameters = new V8Array(nodejs.getRuntime());
            parameters.push(renderconfiguration.getTemplateContent());
            renderadapter.executeVoidFunction("setHtml", parameters);
            parameters.release();

            // Handle incoming requests
            while (true) {
                // Handle the next message
                nodejs.handleMessage();

                // Get the next render request and check for an end
                Optional<RenderRequest> renderrequest = renderrequests.take();
                if (renderrequest.isPresent()) {
                    currentrenderrequest = renderrequest.get();
                } else {
                    break;
                }

                // Render the request
                parameters = new V8Array(nodejs.getRuntime());
                parameters.push(currentrenderrequest.getUuid());
                parameters.push(currentrenderrequest.getUri());
                renderadapter.executeVoidFunction("renderPage", parameters);

                // Handle messages until the render request is resolved
                while (!currentrenderrequest.getFuture().isDone()) {
                    nodejs.handleMessage();
                }

                // Release the parameters
                parameters.release();
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            // Clean up
            if (memorymanager != null) {
                memorymanager.release();
            }

            // Clean up
            if (nodejs != null) {
                nodejs.release();
            }
        }
    }
}
