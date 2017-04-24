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
     * Status if the render request is rendering.
     */
    private boolean rendering;

    /**
     * Create a new render requests.
     *
     * @param uri URI of the request that is used to represent the routing in Angular
     */
    public RenderEntity(String uri) {
        this.future = new CompletableFuture<>();
        this.uuid = UUID.randomUUID();
        this.uri = uri;
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
        return !isRendering() && !isRendered();
    }

    /**
     * Check if the render request is rendering.
     *
     * @return Status of the check
     */
    public boolean isRendering() {
        return rendering;
    }

    /**
     * Set the render request rendering.
     */
    public void setRendering() {
        rendering = true;
    }

    /**
     * Check if the request is rendered (Added, finished rendering, rendered).
     *
     * @return Status of the check
     */
    public boolean isRendered() {
        return future.isDone();
    }

    /**
     * Set the request rendered and resolve the future.
     *
     * @param content Rendered page content
     */
    public void setRendered(String content) {
        future.complete(content);
    }
}
