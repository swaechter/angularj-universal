package ch.swaechter.springular.renderer;

import java.util.concurrent.Future;

/**
 * The class RenderEngine provides an abstract implementation of the render engine. Each JavaScript engine like V8 can
 * extend this interface and implement the run() method.
 *
 * @author Simon Wächter
 */
public abstract class RenderEngine extends Thread {

    /**
     * Queue that contains all render requests.
     */
    protected final RenderQueue queue;

    /**
     * Render configuration that is used to get the server bundle and page content.
     */
    protected final RenderConfiguration configuration;

    /**
     * Process status of the engine.
     */
    protected boolean running;

    /**
     * Create a new render engine which requires a configuration that provides the configuration for the render engine.
     *
     * @param configuration Configuration for the render engine
     */
    public RenderEngine(RenderConfiguration configuration) {
        this.queue = new RenderQueue();
        this.configuration = configuration;
    }

    /**
     * Render a page and get an object that can be accessed in the future.
     *
     * @param uri URI of the page request
     * @return Future object that can be checked and accessed to get the rendered page content. Retrieving the content
     * of the future can be blocking as long the page isn't rendered
     */
    public Future<String> renderPage(String uri) {
        RenderEntity entity = queue.createRenderEntity(uri);
        return entity.getCompletableFuture();
    }

    /**
     * Shutdown the render engine. As long as render requests do exists, the shutdown is blocking and all remaining
     * requests will be rendered and the render engine will shutdown afterwards.
     */
    public void shutdown() {
        while (!queue.isEmpty()) {
            sleep(10);
        }
        running = false;
    }

    /**
     * Sleep fir the given amount of milliseconds. This method is used to prevent another layer of blocking calls.
     *
     * @param miliseconds Sleep time in milliseconds
     */
    protected void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (Exception exception) {
            throw new IllegalStateException("An error occurred while working with the engine: " + exception.getMessage(), exception);
        }
    }

    /**
     * Handle the incoming render requests and pass them to the render engine. Each JavaScript engine has to implement
     * this method to provide a working render engine.
     */
    public abstract void run();
}
