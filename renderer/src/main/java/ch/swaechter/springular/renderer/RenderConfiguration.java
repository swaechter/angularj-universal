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
     * File content of the index page.
     */
    private final String indexcontent;

    /**
     * File of the server bundle.
     */
    private final File serverbundle;

    /**
     * Create a new configuration based on the content of the index page and the server bundle.
     *
     * @param indexcontent Content of the index page
     * @param serverbundle File path to the server bundle
     */
    public RenderConfiguration(String indexcontent, File serverbundle) {
        this.indexcontent = indexcontent;
        this.serverbundle = serverbundle;
    }

    /**
     * Get the content of the index page.
     *
     * @return Content of the index page
     */
    public String getIndexContent() {
        return indexcontent;
    }

    /**
     * Get the server bundle.
     *
     * @return Server bundle
     */
    public File getServerBundle() {
        return serverbundle;
    }
}
