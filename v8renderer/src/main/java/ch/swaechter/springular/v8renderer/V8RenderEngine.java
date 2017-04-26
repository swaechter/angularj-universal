package ch.swaechter.springular.v8renderer;

import ch.swaechter.springular.renderer.AbstractRenderEngine;
import ch.swaechter.springular.renderer.RenderConfiguration;
import ch.swaechter.springular.renderer.RenderRequest;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

import java.util.Optional;

/**
 * The class V8RenderEngine provides a reference implementation and is based on the AbstractRenderEngine. The render
 * engine will render incoming render requests and provide the rendered result that can be accessed later on.
 *
 * @author Simon Wächter
 */
public class V8RenderEngine extends AbstractRenderEngine {

    /**
     * NodeJS instance that will load the server bundle.
     */
    private NodeJS nodejs;

    /**
     * Method that will be called to render a page.
     */
    private V8Object renderer;

    /**
     * Method that will be called when a page is rendered.
     */
    private V8Object rendercallback;

    /**
     * Create a new V8 render engine based on the given configuration. The render engine has to be started and stopped
     * manually.
     *
     * @param configuration Configuration that will be used by the render engine.
     */
    public V8RenderEngine(RenderConfiguration configuration) {
        super(configuration);
    }

    /**
     * Let the render engine do it's work as long running is enabled.
     */
    @Override
    protected void work() {
        try {
            // Create the NodeJS server and get the V8 engine
            nodejs = NodeJS.createNodeJS();

            // Get the V8 engine from the NodeJS server
            V8 engine = nodejs.getRuntime();

            // Register a method to receive the JavaScript render engine that will render the page later on
            engine.registerJavaMethod((V8Object object, V8Array parameters) -> {
                renderer = parameters.getObject(0);
            }, "registerRenderEngine");

            // Register a method to handle the JavaScript callback after the page has been rendered
            engine.registerJavaMethod((V8Object object, V8Array parameters) -> {
                String uuid = parameters.getString(0);
                String content = parameters.getString(1);

                Optional<RenderRequest> renderitem = getRenderQueue().getRenderRequest(uuid);
                if (renderitem.isPresent()) {
                    getRenderQueue().removeRenderRequest(renderitem.get());
                    renderitem.get().setRendered(content);
                }
            }, "renderCallback");

            // Get the render callback
            rendercallback = engine.getObject("renderCallback");

            // Load the server file
            nodejs.require(getConfiguration().getBundleFile()).release();

            // Handle incoming requests
            while (isRunning()) {
                // Handle the next message
                nodejs.handleMessage();

                // Get the next render request
                Optional<RenderRequest> requestitem = getRenderQueue().getNextUntouchedRenderRequest();
                if (requestitem.isPresent()) {
                    RenderRequest request = requestitem.get();
                    V8Array parameters = new V8Array(engine);
                    try {
                        parameters.push(request.getUuid());
                        parameters.push(getConfiguration().getIndexPageContent());
                        parameters.push(request.getUri());
                        parameters.push(rendercallback);
                        renderer.executeVoidFunction("renderPage", parameters);
                        request.setRendering();
                    } finally {
                        parameters.release();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (renderer != null)
                renderer.release();

            if (rendercallback != null)
                rendercallback.release();

            if (nodejs != null)
                nodejs.release();
        }
    }
}
