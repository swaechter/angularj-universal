package ch.swaechter.springular.renderer.queue;

/**
 * The class RenderResponse represents a rendered request that contains the rendered page and is identified by a unique
 * identifier.
 *
 * @author Simon Wächter
 */
public class RenderResponse {

    /**
     * Unique identifier of the render response.
     */
    private final String uuid;

    /**
     * Content of the rendered page.
     */
    private final String content;

    /**
     * Create a new render response with the unique identifier and the page content.
     *
     * @param uuid    Unique identifier
     * @param content Content of the page
     */
    public RenderResponse(String uuid, String content) {
        this.uuid = uuid;
        this.content = content;
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
     * Get the content of the page.
     *
     * @return Content of the page
     */
    public String getContent() {
        return content;
    }
}
