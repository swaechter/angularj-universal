package ch.swaechter.springular.renderer;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * The class V8RenderEngine implements a render engine based on the V8 JavaScript engine.
 *
 * @author Simon Wächter
 */
public class V8RenderEngine extends Thread implements RenderEngine {

    private static final String JAVASCRIPT_UNDEFINED = "undefined";

    //private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private final List<String> queue = new LinkedList<>();

    private boolean running;

    private NodeJS nodejs;

    private V8Object renderer;

    private V8Object rendercallback;

    private File serverfile;

    private String indexcontent;

    private String renderedrequest;

    public V8RenderEngine(File serverfile, String indexcontent) {
        this.serverfile = serverfile;
        this.indexcontent = indexcontent;
    }

    @Override
    public void startEngine() {
        running = true;
        start();
    }

    @Override
    public void stopEngine() {
        running = false;
    }

    @Override
    public String renderPage(String uri) {
        queue.add(uri);
        while(renderedrequest == null) {
        }
        String validrenderedrequest = renderedrequest;
        renderedrequest = null;
        return validrenderedrequest;
    }

    /**
     * Handle the NodeJS event loop and parse the incoming page requests.
     */
    @Override
    public void run() {
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
                String html = parameters.getString(0);
                if (html != null) {
                    renderedrequest = html;
                } else {
                    renderedrequest = "An internal error occurred!";
                }
            }, "renderCallback");

            // Get the render callback
            rendercallback = engine.getObject("renderCallback");

            // Load the server file
            nodejs.require(serverfile).release();

            // Listen for requests
            while (running) {
                String result = null;
                if (nodejs.handleMessage()) {
                    if (queue.size() != 0) {
                        result = queue.remove(0);
                    }
                }/* else {
                    result = queue.take();
                }*/

                if (queue.size() != 0) {
                    result = queue.remove(0);
                }

                if (result != null) {
                    V8Array parameters = new V8Array(engine);
                    try {
                        parameters.push(indexcontent);
                        parameters.push(result);
                        parameters.push(rendercallback);
                        renderer.executeVoidFunction("renderPage", parameters);
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
