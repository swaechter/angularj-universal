package ch.swaechter.angularjuniversal.renderer.configuration;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for providing the whole render configuration.
 *
 * @author Simon Wächter
 */
public class RenderConfiguration {

    /**
     * Path or executable name of the Node.js executable. This path is used to start a Node.js process for rendering.
     */
    private final String nodePath;

    /**
     * Port of the Node.js process is opening a TCP server.
     */
    private final Integer nodePort;

    /**
     * File of the server bundle.
     */
    private final File serverBundleFile;

    /**
     * Content of the template.
     */
    private final String templateContent;

    /**
     * Status of live reload
     */
    private final Boolean liveReload;

    /**
     * Charset of the application.
     */
    private final Charset charset;

    /**
     * Routes of the application.
     */
    private final List<String> routes;

    /**
     * Create a new render configuration with the given parameters.
     *
     * @param nodePath         Node.js path or executable name that is used to start a Node.js path used for rendering
     * @param nodePort         Port of the Node.js TCP socket used for the rendering communication
     * @param serverBundleFile File path of the server bundle that will be loaded by the render engine
     * @param templateContent  Content of the template that is used for rendering the application
     * @param liveReload       Status if live reload is enabled or not
     * @param routes           Routes of the application
     */
    private RenderConfiguration(String nodePath, Integer nodePort, File serverBundleFile, String templateContent, Boolean liveReload, Charset charset, List<String> routes) {
        this.nodePath = nodePath;
        this.nodePort = nodePort;
        this.serverBundleFile = serverBundleFile;
        this.templateContent = templateContent;
        this.liveReload = liveReload;
        this.charset = charset;
        this.routes = routes;
    }

    /**
     * Get the Node.js path or executable name that is used to start a Node.js path used for rendering.
     *
     * @return Path or executable Node.js name
     */
    public String getNodePath() {
        return nodePath;
    }

    /**
     * Get the port of the Node.js TCP socket used for the rendering communication.
     *
     * @return TCP port used for the communication
     */
    public Integer getNodePort() {
        return nodePort;
    }

    /**
     * Get the file path of the server bundle.
     *
     * @return File path of the server bundle
     */
    public File getServerBundleFile() {
        return serverBundleFile;
    }

    /**
     * Get the content of the template.
     *
     * @return Content of the template
     */
    public String getTemplateContent() {
        return templateContent;
    }

    /**
     * Get the status of live reload.
     *
     * @return Status of live reload
     */
    public Boolean getLiveReload() {
        return liveReload;
    }

    /**
     * Get the charset of the application.
     *
     * @return Charset of the application
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Get the routes of the application.
     *
     * @return Routes of the application
     */
    public List<String> getRoutes() {
        return routes;
    }

    /**
     * This class is responsible for building a render configuration.
     *
     * @author Simon Wächter
     */
    public static class RenderConfigurationBuilder {

        /**
         * Path or executable name of the Node.js executable. This path is used to start a Node.js process for rendering.
         */
        private final String nodePath;

        /**
         * Port of the Node.js process is opening a TCP server.
         */
        private final Integer nodePort;

        /**
         * Content of the template.
         */
        private final String templateContent;

        /**
         * File of the server bundle.
         */
        private final File serverBundleFile;

        /**
         * Status of live reload
         */
        private Boolean liveReload = false;

        /**
         * Charset of the application.
         */
        private Charset charset = StandardCharsets.UTF_8;

        /**
         * Routes of the application.
         */
        private List<String> routes = Arrays.asList("/");

        /**
         * Create a new render configuration builder that can be used to build the render configuration.
         *
         * @param templateContent  Content of the template used for rendering
         * @param serverBundleFile Server bundle file used for rendering
         */
        /**
         * Create a new render configuration builder that can be used to build the render configuration.
         *
         * @param nodePath         Path or executable name of the Node.js executable. This path is used to start a Node.js process for rendering.
         * @param nodePort         Port of the Node.js process is opening a TCP server.
         * @param serverBundleFile Server bundle file used for rendering
         * @param templateContent  Content of the template used for rendering
         */
        public RenderConfigurationBuilder(String nodePath, Integer nodePort, File serverBundleFile, String templateContent) {
            this.nodePath = nodePath;
            this.nodePort = nodePort;
            this.templateContent = templateContent;
            this.serverBundleFile = serverBundleFile;
        }

        /**
         * Enable or disable live reload. By default live reloading is disabled.
         *
         * @param liveReload Status of live reloading
         * @return Current render configuration builder
         */
        public RenderConfigurationBuilder liveReload(boolean liveReload) {
            this.liveReload = liveReload;
            return this;
        }

        /**
         * Specify the charset of the application. By default UTF-8 is used.
         *
         * @param charset Charset of the application
         * @return Current render configuration builder
         */
        public RenderConfigurationBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * Specify the routes of the application. By default the / route is set.
         *
         * @param routes Routes of the application
         * @return Current render configuration builder
         */
        public RenderConfigurationBuilder routes(List<String> routes) {
            this.routes = routes;
            return this;
        }

        /**
         * Build a new render configuration.
         *
         * @return New render configuration
         */
        public RenderConfiguration build() {
            return new RenderConfiguration(nodePath, nodePort, serverBundleFile, templateContent, liveReload, charset, routes);
        }
    }
}
