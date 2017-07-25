package ch.swaechter.angularjssr.renderer.queue;

import java.util.concurrent.CompletableFuture;

/**
 * The class RenderFuture represents a render request that provides a completable future to resolve the request to the
 * caller. The render future also provides the information if a render request is in the process of becoming rendered.
 *
 * @author Simon Wächter
 */
public class RenderFuture {

    /**
     * Completable future that will be provided to the caller. As soon the render request is rendered, the future will
     * be resolved an the value is accessible.
     */
    private final CompletableFuture<String> future;

    /**
     * RenderRequest that contains the real render request.
     */
    private final RenderRequest request;

    /**
     * Status if the render request is getting rendered.
     */
    private boolean rendering;

    /**
     * Create a new render future that uses the given render request and provides a future.
     *
     * @param request Provided render request
     */
    public RenderFuture(RenderRequest request) {
        this.future = new CompletableFuture<>();
        this.request = request;
        this.rendering = false;
    }

    /**
     * Get the future which will be completed as soon the page has been rendered.
     *
     * @return Completable future that provides the rendered page
     */
    public CompletableFuture<String> getCompletableFuture() {
        return future;
    }

    /**
     * Get the render request.
     *
     * @return Render request
     */
    public RenderRequest getRequest() {
        return request;
    }

    /**
     * Check if the render request is getting rendered.
     *
     * @return Status if the request is getting rendered.
     */
    public boolean isRendering() {
        return rendering;
    }

    /**
     * Set the render request rendering.
     */
    public void setRendering() {
        this.rendering = true;
    }
}
