package ch.swaechter.springular.renderer;

import java.io.File;

/**
 * The class RenderConfiguration provides the configuration for the render engine like the file path of the server
 * JavaScript bundle and the content of the index page as string.
 *
 * @author Simon Wächter
 */
public class RenderConfiguration {

    /**
     * File of the JavaScript server bundle.
     */
    private final File bundlefile;

    /**
     * File content of the index page.
     */
    private final String indexpagecontent;

    /**
     * Create a new configuration that provides the JavaScript server bundle and the content of the index page as
     * string.
     *
     * @param bundlefile       File path to the JavaScript server bundle
     * @param indexpagecontent Content of the index page
     */
    public RenderConfiguration(File bundlefile, String indexpagecontent) {
        this.bundlefile = bundlefile;
        this.indexpagecontent = indexpagecontent;
    }

    /**
     * Get the file to the JavaScript server bundle.
     *
     * @return File to the JavaScript server bundle
     */
    public File getBundleFile() {
        return bundlefile;
    }

    /**
     * Get the content of the index page.
     *
     * @return Content of the index page
     */
    public String getIndexPageContent() {
        return indexpagecontent;
    }
}
