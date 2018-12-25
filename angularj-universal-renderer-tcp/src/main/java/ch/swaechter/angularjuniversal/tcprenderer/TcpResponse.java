package ch.swaechter.angularjuniversal.tcprenderer;

/**
 * TCP response represeing the JSON object with the rendered page from the server.
 *
 * @author Simon Wächter
 */
public class TcpResponse {

    /**
     * Rendered HTML in the response.
     */
    private String html;

    /**
     * ID of the render response.
     */
    private String id;

    /**
     * Default constructor for Jackson
     */
    public TcpResponse() {
    }

    /**
     * Get the rendered HTML of the response.
     *
     * @return Rendered HTML of the response
     */
    public String getHtml() {
        return html;
    }

    /**
     * Set the rendered HTML of the response.
     *
     * @param html New rendered HTML of the response
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * Get the ID of the response.
     *
     * @return ID of the response
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID of the response.
     *
     * @param id New ID of the response
     */
    public void setId(String id) {
        this.id = id;
    }
}
