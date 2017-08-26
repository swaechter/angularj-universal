package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.queue.RenderQueue;
import ch.swaechter.angularjuniversal.renderer.queue.RenderRequest;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

import java.nio.charset.StandardCharsets;

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
     * Function that will be used for rendering the module.
     */
    private V8Object renderfunction;

    /**
     * Module that will be passed to the render function.
     */
    private V8Object rendermodule;

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
            // Load the function
            String functioncontent = V8Utils.getContentFromInputStream(this.getClass().getResourceAsStream("/Function.js"), StandardCharsets.UTF_8);

            // Start the render engine
            running = true;

            // Create the NodeJS server and get the V8 engine
            nodejs = NodeJS.createNodeJS();

            // Create the memory manager to enable auto garbage collection
            memorymanager = new MemoryManager(nodejs.getRuntime());

            // Register a method that represents the register function
            nodejs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
                renderfunction = v8Array.getObject(0);
                rendermodule = v8Array.getObject(1);
            }, "registerRenderElements");

            // Register a method that represents the finished render request function
            nodejs.getRuntime().registerJavaMethod((v8Object, v8Array) -> {
                String uuid = v8Array.getString(0);
                String html = v8Array.getString(1);
                V8Object error = v8Array.getObject(2);
                if (error == null) {
                    queue.resolveRenderReuest(uuid, html);
                } else {
                    System.err.println("The V8 render received a render response with an error for " + uuid + ": " + error);
                }
            }, "receiveRenderedPage");

            // Register the function
            nodejs.getRuntime().executeScript(functioncontent);

            // Load the server bundle
            nodejs.exec(provider.getServerBundle());

            // Handle the first two registered methods
            nodejs.handleMessage();
            nodejs.handleMessage();

            // Handle incoming requests
            while (running) {
                // Handle the next message
                nodejs.handleMessage();

                // Get the next render request
                RenderRequest renderrequest = queue.getNextRenderRequest();
                V8Array parameters = new V8Array(nodejs.getRuntime());
                try {
                    // Execute the render request
                    parameters.push(renderfunction);
                    parameters.push(rendermodule);
                    parameters.push(renderrequest.getUuid());
                    parameters.push(provider.getIndexContent());
                    parameters.push(renderrequest.getUri());
                    nodejs.getRuntime().executeVoidFunction("renderPage", parameters);

                    // Handle messages until the render request is resolved
                    while (!renderrequest.getFuture().isDone()) {
                        nodejs.handleMessage();
                    }
                } finally {
                    parameters.release();
                }
            }
        } catch (InterruptedException exception) {
            // Do nothing
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
