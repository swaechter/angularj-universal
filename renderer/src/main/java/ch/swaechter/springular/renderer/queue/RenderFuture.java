package ch.swaechter.springular.renderer.queue;

import java.util.concurrent.CompletableFuture;

/**
 * The class RenderFuture provides a resolvable render request that can be accesses as soon as the render request has
 * been rendered.
 *
 * @author Simon Wächter
 */
public class RenderFuture {

    /**
     * Completable future that will be provided to the caller. As soon the render request is rendered, the future will
     * be resolved an the value is accessible.
     */
    private final CompletableFuture<String> future;

    private final RenderRequest request;

    private boolean rendering;

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
     * Check if the render request of the future is getting rendered.
     *
     * @return Status if the request is rendering
     */
    public boolean isRendering() {
        return rendering;
    }

    /**
     * Set the render request rendering .
     */
    public void setRendering() {
        this.rendering = true;
    }
}
