package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.queue.RenderQueue;
import ch.swaechter.angularjuniversal.renderer.queue.RenderRequest;
import ch.swaechter.angularjuniversal.renderer.queue.RenderResponse;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

import java.util.Optional;

/**
 * The class V8RenderEngine provides a NodeJS/V8 based implementation of the render engine.
 *
 * @author Simon Wächter
 */
public class V8RenderEngine implements RenderEngine {

    /**
     * Memory manager that handles all memory and provides a garbage collection.
     */
    private MemoryManager memorymanager;

    /**
     * NodeJS instance that will load the server bundle.
     */
    private NodeJS nodejs;

    /**
     * Method that will be called to render a page.
     */
    private V8Object renderer;

    /**
     * Status if the render engine is running.
     */
    private boolean running;

    /**
     * Create a NodeJS engine that uses the queue to access incoming requests and resolve the responses.
     *
     * @param queue    Queue that provides the requests and makes it possible to resolve responses
     * @param provider Provider to access the assets
     */
    @Override
    public void doWork(RenderQueue queue, RenderAssetProvider provider) {
        try {
            // Start the render engine
            running = true;

            // Create the NodeJS server and get the V8 engine
            nodejs = NodeJS.createNodeJS();

            // Create the memory manager to enable auto garbage collection
            memorymanager = new MemoryManager(nodejs.getRuntime());

            // Register a method to receive the JavaScript render engine that will render the page later on
            nodejs.getRuntime().registerJavaMethod((V8Object object, V8Array parameters) -> {
                renderer = parameters.getObject(0);
            }, "registerRenderEngine");

            // Register a method to receive the rendered page content
            nodejs.getRuntime().registerJavaMethod((V8Object object, V8Array parameters) -> {
                String uuid = parameters.getString(0);
                String content = parameters.getString(1);
                queue.resolveRenderFuture(new RenderResponse(uuid, content));
            }, "receiveRenderedPage");

            // Load the server file
            nodejs.require(provider.getServerBundle()).release();

            // Handle incoming requests
            while (running) {
                // Handle the next message
                nodejs.handleMessage();

                // Get the next render request
                Optional<RenderRequest> requestitem = queue.getNextRenderRequest();
                if (requestitem.isPresent()) {
                    RenderRequest request = requestitem.get();
                    V8Array parameters = new V8Array(nodejs.getRuntime());
                    try {
                        parameters.push(request.getUuid());
                        parameters.push(provider.getIndexContent());
                        parameters.push(request.getUri());
                        renderer.executeVoidFunction("renderPage", parameters);
                    } finally {
                        parameters.release();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (memorymanager != null) {
                memorymanager.release();
            }

            if (nodejs != null) {
                nodejs.release();
            }
        }
    }

    /**
     * Finish the current requests and stop the engine.
     */
    public void stopWork() {
        running = false;
    }

    /**
     * Check if the render engine is working.
     *
     * @return Status if the render engine is working
     */
    public boolean isWorking() {
        return running;
    }
}
