package ch.swaechter.angularjuniversal.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for providing all mapped properties.
 *
 * @author Simon Wächter
 */
@ConfigurationProperties(prefix = "angularjuniversal")
public class AngularJUniversalProperties {

    /**
     * Comma separated list with all routes of the application.
     */
    private List<String> routes = Arrays.asList("/");

    /**
     * Path of the index resource.
     */
    private String indexresourcepath = "/public/index.html";

    /**
     * Path of the server bundle resource.
     */
    private String serverbundleresourcepath = "/server.js";

    /**
     * Charset used for reading and rendering.
     */
    private Charset charset = StandardCharsets.UTF_8;

    /**
     * Number of render engines.
     */
    private int engines = 4;

    /**
     * Get all routes as list.
     *
     * @return All routes as list
     */
    public List<String> getRoutes() {
        return routes;
    }

    /**
     * Set all routes as list.
     *
     * @param routes All routes as list
     */
    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    /**
     * Get the path of the index resource.
     *
     * @return Path of the index resource
     */
    public String getIndexResourcePath() {
        return indexresourcepath;
    }

    /**
     * Set the path of the new index resource.
     *
     * @param indexresourcepath Path of the new index resource
     */
    public void setIndexResourcePath(String indexresourcepath) {
        this.indexresourcepath = indexresourcepath;
    }

    /**
     * Get the path of the server bundle resource.
     *
     * @return Path of the server bundle
     */
    public String getServerBundleResourcePath() {
        return serverbundleresourcepath;
    }

    /**
     * Set the path of the new server bundle resource.
     *
     * @param serverbundleresourcepath Path of the new server bundle resource
     */
    public void setServerBundleResourcePath(String serverbundleresourcepath) {
        this.serverbundleresourcepath = serverbundleresourcepath;
    }

    /**
     * Get the charset used for reading and rendering.
     *
     * @return Charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Set the new charset.
     *
     * @param charset New charset
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Get the number of render engines.
     *
     * @return Number of render engines
     */
    public int getEngines() {
        return engines;
    }

    /**
     * Set the new number of render engines.
     *
     * @param engines New number of render engines
     */
    public void setEngines(int engines) {
        this.engines = engines;
    }
}
