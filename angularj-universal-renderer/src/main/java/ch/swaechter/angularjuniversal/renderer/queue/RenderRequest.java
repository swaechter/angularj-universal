package ch.swaechter.angularjuniversal.renderer.queue;

import java.util.UUID;

/**
 * The class RenderRequest represents a render requests that contains the given URI for the request. The render request
 * will generate a unique identifier that is used later on to access the rendered response.
 *
 * @author Simon Wächter
 */
public class RenderRequest {

    /**
     * Unique identifier of the render request.
     */
    private final String uuid;

    /**
     * URI of the page request.
     */
    private final String uri;

    /**
     * Create a new render request based on the URI. The render request is accessible by a generated unique identifier.
     *
     * @param uri URI of the page request
     */
    public RenderRequest(String uri) {
        this.uuid = UUID.randomUUID().toString();
        this.uri = uri;
    }

    /**
     * Get the unique identifier of the page.
     *
     * @return Unique identifier of the page
     */

    public String getUuid() {
        return uuid;
    }

    /**
     * Get the URI of the page request.
     *
     * @return URI of the page request
     */
    public String getUri() {
        return uri;
    }
}
