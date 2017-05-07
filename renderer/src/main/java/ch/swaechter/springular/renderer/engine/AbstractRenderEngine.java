package ch.swaechter.springular.renderer.engine;

import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.assets.AssetProvider;
import ch.swaechter.springular.renderer.queue.RenderQueue;
import ch.swaechter.springular.renderer.queue.RenderRequest;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * The class AbstractRenderEngine provides an abstract reference implementation of the render engine that can be used
 * to implement a custom render engine.
 *
 * @author Simon Wächter
 */
public abstract class AbstractRenderEngine implements RenderEngine {

    /**
     * Queue that is used to store the render requests.
     */
    private final RenderQueue queue;

    /**
     * Provider that is used to access the assets.
     */
    private final AssetProvider provider;

    /**
     * Status if the render engine is running.
     */
    private boolean running;

    /**
     * Create a new render engine and use the render provider to access the assets.
     *
     * @param provider Render provider to access the assets
     */
    public AbstractRenderEngine(AssetProvider provider) {
        this.queue = new RenderQueue();
        this.provider = provider;
        this.running = false;
    }

    /**
     * Start the render engine. If render requests were already added, the will be rendered.
     */
    @Override
    public void startEngine() {
        if (running) {
            return;
        }

        running = true;

        Thread thread = new Thread(this::doWork);
        thread.start();
    }

    /**
     * Stop the render engine. If render requests are still there, the render engine will render them before stopping.
     */
    @Override
    public void stopEngine() {
        if (!running) {
            return;
        }

        while (!queue.isEmpty()) {
            sleep(50);
        }
        running = false;
    }

    /**
     * Check if the render engine is running.
     *
     * @return Result of the check
     */
    @Override
    public boolean isEngineRunning() {
        return running;
    }

    /**
     * Add a new render request and receive a future, that can be resolved as soon the render request has been rendered.
     *
     * @param uri URI of the render request
     * @return Future that can be accessed later on to get the rendered content
     */
    @Override
    public Future<String> renderRequest(String uri) {
        RenderRequest request = queue.createRenderRequest(uri);
        return request.getCompletableFuture();
    }

    /**
     * Get the next untouched render request. If an untouched render request is available, the status of the render
     * request will be set to rendering.
     *
     * @return An optional untouched render request that is set as rendering
     */
    protected Optional<RenderRequest> getNextUntouchedRenderRequest() {
        Optional<RenderRequest> requestitem = queue.getNextUntouchedRenderRequest();
        if (requestitem.isPresent()) {
            requestitem.get().setRendering();
        }
        return requestitem;
    }

    /**
     * Finish a render request that is rendering and pass the result to the future, so the caller will be able to access
     * the rendered result.
     *
     * @param uuid    UUID of the render request
     * @param content Rendered content of the render request
     */
    protected void finishRenderRequest(String uuid, String content) {
        Optional<RenderRequest> requestitem = queue.getRenderRequest(uuid);
        if (requestitem.isPresent()) {
            queue.removeRenderRequest(requestitem.get());
            requestitem.get().setRendered(content);
        }
    }

    /**
     * Get the render provider to access the assets.
     *
     * @return Render provider to access the assets
     */
    protected AssetProvider getRenderProvider() {
        return provider;
    }

    /**
     * Sleep for the given amount of milliseconds.
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
     * Create the custom JavaScript engine and handle all incoming requests & parse and finish them. This method has to
     * be implemented by the specific JavaScript engine.
     */
    abstract protected void doWork();
}
