package ch.swaechter.angularjuniversal.tcprenderer;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * TCP request representing the JSON object sent to the render server.
 *
 * @author Simon Wächter
 */
public class TcpRequest {

    /**
     * ID of the render request.
     */
    @NotNull
    private String id;

    /**
     * URL of the render request.
     */
    @NotNull
    private String url;

    /**
     * Fallback document template.
     */
    @NotNull
    private String document;

    /**
     * Default constructor for Jackson
     */
    public TcpRequest() {
        this.id = UUID.randomUUID().toString();
        this.url = "";
        this.document = "";
    }

    /**
     * Create a new TCP request that will be serialized to JSON.
     *
     * @param id       ID of the render request
     * @param url      URL of the render request
     * @param document Fallback document template
     */
    public TcpRequest(@NotNull String id, @NotNull String url, @NotNull String document) {
        this.id = id;
        this.url = url;
        this.document = document;
    }

    /**
     * Get the ID of the request.
     *
     * @return ID of the request
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Set the ID of the request.
     *
     * @param id New ID of the request
     */
    public void setId(@NotNull String id) {
        this.id = id;
    }

    /**
     * Get the ID of the request.
     *
     * @return ID of the request
     */
    @NotNull
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the request.
     *
     * @param url New URL of the request
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Get the fallback document template of the request.
     *
     * @return Fallback document template of the request
     */
    @NotNull
    public String getDocument() {
        return document;
    }

    /**
     * Set the fallback document template  of the request.
     *
     * @param document New fallback document template of the request
     */
    public void setDocument(@NotNull String document) {
        this.document = document;
    }
}
