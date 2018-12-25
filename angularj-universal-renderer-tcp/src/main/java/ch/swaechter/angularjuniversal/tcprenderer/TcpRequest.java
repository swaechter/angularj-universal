package ch.swaechter.angularjuniversal.tcprenderer;

/**
 * TCP request representing the JSON object sent to the render server.
 *
 * @author Simon Wächter
 */
public class TcpRequest {

    /**
     * ID of the render request.
     */
    private String id;

    /**
     * URL of the render request.
     */
    private String url;

    /**
     * Fallback document template.
     */
    private String document;

    /**
     * Default constructor for Jackson
     */
    public TcpRequest() {
    }

    /**
     * Create a new TCP request that will be serialized to JSON.
     *
     * @param id       ID of the render request
     * @param url      URL of the render request
     * @param document Fallback document template
     */
    public TcpRequest(String id, String url, String document) {
        this.id = id;
        this.url = url;
        this.document = document;
    }

    /**
     * Get the ID of the request.
     *
     * @return ID of the request
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID of the request.
     *
     * @param id New ID of the request
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the ID of the request.
     *
     * @return ID of the request
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the request.
     *
     * @param url New URL of the request
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the fallback document template of the request.
     *
     * @return Fallback document template of the request
     */
    public String getDocument() {
        return document;
    }

    /**
     * Set the fallback document template  of the request.
     *
     * @param document New fallback document template of the request
     */
    public void setDocument(String document) {
        this.document = document;
    }
}
