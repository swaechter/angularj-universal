package ch.swaechter.angularjuniversal.renderer.request;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a render request with a completable future that will contain the rendered content, a unique
 * UUID and the URI of the request.
 *
 * @author Simon Wächter
 */
public class RenderRequest {

    private static long lastId = 0L;

    /**
     * Completable future that will contain the rendered content.
     */
    @NotNull
    private CompletableFuture<String> future = new CompletableFuture<>();

    /**
     * Unique UUID of the request.
     */
    private long id = ++RenderRequest.lastId;

    /**
     * URI of the request.
     */
    @NotNull
    private String uri;

    /**
     * Create a new render request based on the given URI.
     *
     * @param uri URI of the page request
     */
    public RenderRequest(@NotNull String uri) {
        this.uri = uri;
    }

    /**
     * Get the completable future of the render request that will contain the render content.
     *
     * @return Completable future of the render request
     */
    @NotNull
    public CompletableFuture<String> getFuture() {
        return future;
    }

    /**
     * Get the unique UUID of the render request.
     *
     * @return Unique UUID of the render request
     */
    public long getId() {
        return id;
    }

    /**
     * Get the URI of the render request.
     *
     * @return URI of the render request
     */
    @NotNull
    public String getUri() {
        return uri;
    }
}
