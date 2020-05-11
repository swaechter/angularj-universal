package ch.swaechter.angularjuniversal.tcprenderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TCP response represeing the JSON object with the rendered page from the server.
 *
 * @author Simon Wächter
 */
public class TcpResponse {

    /**
     * Rendered HTML in the response.
     */
    @Nullable
    private String html;

    /**
     * ID of the render response.
     */
    @NotNull
    private Long id = 0L;

    /**
     * The error if one occurred
     */
    @Nullable
    private String error;

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
    @Nullable
    public String getHtml() {
        return html;
    }

    /**
     * Set the rendered HTML of the response.
     *
     * @param html New rendered HTML of the response
     */
    public void setHtml(@Nullable String html) {
        this.html = html;
    }

    /**
     * Get the ID of the response.
     *
     * @return ID of the response
     */
    @NotNull
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the response.
     *
     * @param id New ID of the response
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Get the occurred error if one occurred
     *
     * @return the error description
     */
    @Nullable
    public String getError() {
        return error;
    }

    /**
     * Set the error if one occurred
     *
     * @param error the error description
     */
    public void setError(@Nullable String error) {
        this.error = error;
    }
}
