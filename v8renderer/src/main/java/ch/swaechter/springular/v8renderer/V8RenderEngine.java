package ch.swaechter.springular.v8renderer;

import ch.swaechter.springular.renderer.assets.AssetProvider;
import ch.swaechter.springular.renderer.engine.AbstractRenderEngine;
import ch.swaechter.springular.renderer.queue.RenderRequest;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.MemoryManager;

import java.util.Optional;

/**
 * The class V8RenderEngine provides a NodeJS/V8 based implementation of the render engine. The implementation relies
 * on the reference implementation of the render engine.
 *
 * @author Simon Wächter
 */
public class V8RenderEngine extends AbstractRenderEngine {

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
     * Create a new NodeJS/V8 render engine that usses the asset provider to access the assets.
     *
     * @param provider Asset provider that is used to access the assets
     */
    public V8RenderEngine(AssetProvider provider) {
        super(provider);
    }

    /**
     * Create a NodeJS engine and get the V8 engine. The V8 engine will render all incoming requests and push them back.
     */
    @Override
    protected void doWork() {
        try {
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
                finishRenderRequest(uuid, content);
            }, "receiveRenderedPage");

            // Load the server file
            nodejs.require(getRenderProvider().getServerBundle()).release();

            // Handle incoming requests
            while (isEngineRunning()) {
                // Handle the next message
                nodejs.handleMessage();

                // Get the next render request
                Optional<RenderRequest> requestitem = getNextUntouchedRenderRequest();
                if (requestitem.isPresent()) {
                    RenderRequest request = requestitem.get();
                    V8Array parameters = new V8Array(nodejs.getRuntime());
                    try {
                        parameters.push(request.getUuid());
                        parameters.push(getRenderProvider().getIndexContent());
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
            memorymanager.release();
            if (nodejs != null) {
                nodejs.release();
            }
        }
    }
}
