package ch.swaechter.angularjuniversal.renderer.request;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a render request with a completable future that will contain the rendered content, a unique
 * UUID and the URI of the request.
 *
 * @author Simon Wächter
 */
public class RenderRequest {

    /**
     * Completable future that will contain the rendered content.
     */
    private CompletableFuture<String> future;

    /**
     * Unique UUID of the request.
     */
    private String uuid;

    /**
     * URI of the request.
     */
    private String uri;

    /**
     * Create a new render request based on the given URI.
     *
     * @param uri URI of the page request
     */
    public RenderRequest(String uri) {
        this.future = new CompletableFuture<>();
        this.uuid = UUID.randomUUID().toString();
        this.uri = uri;
    }

    /**
     * Get the completable future of the render request that will contain the render content.
     *
     * @return Completable future of the render request
     */
    public CompletableFuture<String> getFuture() {
        return future;
    }

    /**
     * Get the unique UUID of the render request.
     *
     * @return Unique UUID of the render request
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Get the URI of the render request.
     *
     * @return URI of the render request
     */
    public String getUri() {
        return uri;
    }
}
