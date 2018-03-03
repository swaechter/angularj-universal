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
     * Content of the template.
     */
    private final String templatecontent;

    /**
     * File of the server bundle.
     */
    private final File serverbundlefile;

    /**
     * Number of engines
     */
    private final int engines;

    /**
     * Status of live reload
     */
    private final boolean livereload;

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
     * @param templatecontent  Content of the template that is used for rendering the application
     * @param serverbundlefile File path of the server bundle that will be loaded by the render engine
     * @param engines          Number of engines that are used for rendering
     * @param livereload       Status if live reload is enabled or not
     * @param routes           Routes of the application
     */
    private RenderConfiguration(String templatecontent, File serverbundlefile, int engines, boolean livereload, Charset charset, List<String> routes) {
        this.templatecontent = templatecontent;
        this.serverbundlefile = serverbundlefile;
        this.engines = engines;
        this.livereload = livereload;
        this.charset = charset;
        this.routes = routes;
    }

    /**
     * Get the content of the template.
     *
     * @return Content of the template
     */
    public String getTemplateContent() {
        return templatecontent;
    }

    /**
     * Get the file path of the server bundle.
     *
     * @return File path of the server bundle
     */
    public File getServerBundleFile() {
        return serverbundlefile;
    }

    /**
     * Get the number of engines.
     *
     * @return Number of engines
     */
    public int getEngines() {
        return engines;
    }

    /**
     * Get the status of live reload.
     *
     * @return Status of live reload
     */
    public boolean getLiveReload() {
        return livereload;
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
         * Content of the template.
         */
        private final String templatecontent;

        /**
         * File of the server bundle.
         */
        private final File serverbundlefile;

        /**
         * Number of engines
         */
        private int engines = 4;

        /**
         * Status of live reload
         */
        private boolean livereload = false;

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
         * @param templatecontent  Content of the template used for rendering
         * @param serverbundlefile Server bundle file used for rendering
         */
        public RenderConfigurationBuilder(String templatecontent, File serverbundlefile) {
            this.templatecontent = templatecontent;
            this.serverbundlefile = serverbundlefile;
        }

        /**
         * Specify the number of used render engines. By default 4 engines are used.
         *
         * @param engines Number of engines
         * @return Current render configuration builder
         */
        public RenderConfigurationBuilder engines(int engines) {
            this.engines = engines;
            return this;
        }

        /**
         * Enable or disable live reload. By default live reloading is disabled.
         *
         * @param livereload Status of live reloading
         * @return Current render configuration builder
         */
        public RenderConfigurationBuilder liveReload(boolean livereload) {
            this.livereload = livereload;
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
            return new RenderConfiguration(templatecontent, serverbundlefile, engines, livereload, charset, routes);
        }
    }
}
