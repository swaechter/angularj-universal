package ch.swaechter.springular.renderer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The class RenderEntity provides all information that are required to represent a render requests.
 *
 * @author Simon Wächter
 */
public class RenderEntity {

    /**
     * Different states in which the render request can be.
     */
    private enum Status {
        UNTOUCHED,
        RENDERING,
        RENDERED,
    }

    /**
     * Completable future that will be provided to the caller. As soon the render request is rendered, the future will
     * be resolved an the value is accessible.
     */
    private final CompletableFuture<String> future;

    /**
     * Unique ID of the render requests that is used to recognize the request in the asynchronous process flow.
     */
    private final UUID uuid;

    /**
     * URI that is used to represent the client routing.
     */
    private final String uri;

    /**
     * Status of the render requests.
     */
    private Status status;

    /**
     * Create a new render requests.
     *
     * @param uri URI of the request that is used to represent the routing in Angular
     */
    public RenderEntity(String uri) {
        this.future = new CompletableFuture<>();
        this.uuid = UUID.randomUUID();
        this.uri = uri;
        this.status = Status.UNTOUCHED;
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
     * Get the unique UUID which represents each request.
     *
     * @return Unique UUID of the request
     */
    public String getUuid() {
        return uuid.toString();
    }

    /**
     * Get the URI of the request.
     *
     * @return URI of the request
     */
    public String getUri() {
        return uri;
    }

    /**
     * Check if the request is untouched (Added, but not rendering or rendered).
     *
     * @return Status of the check
     */
    public boolean isUntouched() {
        return status == Status.UNTOUCHED;
    }

    /**
     * Check if the request is rendered (Added, rendering but not rendered).
     *
     * @return Status of the check
     */
    public boolean isInRendering() {
        return status == Status.RENDERING;
    }

    /**
     * Set the request rendering.
     */
    public void setInRendering() {
        status = Status.RENDERED;
    }

    /**
     * Check if the request is rendered (Added, finished rendering, rendered).
     *
     * @return Status of the check
     */
    public boolean isRendered() {
        return status == Status.RENDERED;
    }

    /**
     * Set the request rendered and resolve the future.
     *
     * @param content Rendered page content
     */
    public void setRendered(String content) {
        future.complete(content);
        status = Status.RENDERED;
    }
}
