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
    private final String templateContent;

    /**
     * File of the server bundle.
     */
    private final File serverBundleFile;

    /**
     * Status of live reload
     */
    private final boolean liveReload;

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
     * @param templateContent  Content of the template that is used for rendering the application
     * @param serverBundleFile File path of the server bundle that will be loaded by the render engine
     * @param liveReload       Status if live reload is enabled or not
     * @param routes           Routes of the application
     */
    private RenderConfiguration(String templateContent, File serverBundleFile, boolean liveReload, Charset charset, List<String> routes) {
        this.templateContent = templateContent;
        this.serverBundleFile = serverBundleFile;
        this.liveReload = liveReload;
        this.charset = charset;
        this.routes = routes;
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
     * Get the file path of the server bundle.
     *
     * @return File path of the server bundle
     */
    public File getServerBundleFile() {
        return serverBundleFile;
    }

    /**
     * Get the status of live reload.
     *
     * @return Status of live reload
     */
    public boolean getLiveReload() {
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
        private boolean liveReload = false;

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
        public RenderConfigurationBuilder(String templateContent, File serverBundleFile) {
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
            return new RenderConfiguration(templateContent, serverBundleFile, liveReload, charset, routes);
        }
    }
}
