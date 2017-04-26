package ch.swaechter.springular.renderer;


import java.util.concurrent.Future;

/**
 * The class AbstractRenderEngine provides a sophisticated implementation of a render engine, where only the render
 * engine specific part has to be implemented. One of course can also create a totally new render engine based on the
 * RenderEngine interface.
 *
 * @author Simon Wächter
 */
public abstract class AbstractRenderEngine implements RenderEngine {

    /**
     * Configuration of the render engine.
     */
    private final RenderConfiguration configuration;

    /**
     * Queue that is used to store the render requests.
     */
    private final RenderQueue queue;

    /**
     * Thread that runs the render engine.
     */
    private final Thread thread;

    /**
     * Status if the thread is running.
     */
    private boolean running;

    /**
     * Create a new render engine based on the given configuration. The render engine has to be started and stopped
     * manually.
     *
     * @param configuration Configuration that will be used by the render engine.
     */
    public AbstractRenderEngine(RenderConfiguration configuration) {
        this.configuration = configuration;
        this.queue = new RenderQueue();
        this.thread = new Thread(this::work);
    }

    /**
     * Start the render engine and render page requests. If render requests were already added, the render engine will
     * render them after the engine has started.
     */
    public void startEngine() {
        if (isRunning()) {
            return;
        }

        running = true;
        thread.start();
    }

    /**
     * Stop the render engine, as soon all render requests have been finished (Method call is blocking).
     */
    public void stopEngine() {
        while (!queue.isEmpty()) {
            sleep(10);
        }
        running = false;
    }

    /**
     * Check if the render engine is running.
     *
     * @return Status of the check
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Render a page request based on the URI and return a Future that can be accessed later on.
     *
     * @param uri URI of the render request
     * @return Future with the page result that can be accessed later on
     */
    public Future<String> renderPage(String uri) {
        RenderRequest request = queue.createRenderRequest(uri);
        return request.getCompletableFuture();
    }

    /**
     * Get the current render configuration.
     *
     * @return Current render configuration
     */
    protected RenderConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Get the current render queue.
     *
     * @return Current render queue
     */
    protected RenderQueue getRenderQueue() {
        return queue;
    }

    /**
     * Sleep fir the given amount of milliseconds. This method is used to prevent another layer of blocking calls.
     *
     * @param miliseconds Sleep time in milliseconds
     */
    private void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (Exception exception) {
            throw new IllegalStateException("An error occurred while working with the engine: " + exception.getMessage(), exception);
        }
    }

    /**
     * Let the render engine do it's work as long running is enabled.
     */
    abstract protected void work();
}
