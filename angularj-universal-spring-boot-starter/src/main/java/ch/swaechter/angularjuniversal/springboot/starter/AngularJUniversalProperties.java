package ch.swaechter.angularjuniversal.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
    private String routes = "/";

    /**
     * Path of the index resource.
     */
    private String indexresourcepath = "/public/index.html";

    /**
     * Path of the server bundle resource.
     */
    private String serverbundleresourcepath = "/server.bundle.js";

    /**
     * Charset used for reading and rendering.
     */
    private String charset = "UTF-8";

    /**
     * Number of render engines.
     */
    private int engines = 5;

    /**
     * Get all routes of the application as comma separated list.
     *
     * @return All routes as comma separated list
     */
    public String getRoutes() {
        return routes;
    }

    /**
     * Set all routes of the application as comma separated list.
     *
     * @param routes All routes as comma separated list
     */
    public void setRoutes(String routes) {
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
    public String getCharset() {
        return charset;
    }

    /**
     * Set the new charset.
     *
     * @param charset New charset
     */
    public void setCharsetend(String charset) {
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
