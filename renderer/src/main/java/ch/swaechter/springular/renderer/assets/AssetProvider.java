package ch.swaechter.springular.renderer.assets;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

/**
 * The class AssetProvider is responsible for providing the assets like the content of the index page and the server
 * bundle as a file. It's possible to develop specific asset providers (Resource, file system etc.).
 *
 * @author Simon Wächter
 */
public abstract class AssetProvider extends Observable {

    /**
     * Request the render engine to reload itself with the new assets.
     */
    protected void requestReload() {
        setChanged();
        notifyObservers();
    }

    /**
     * Get the content of the index template.
     *
     * @return Content of the index template.
     * @throws IOException Exception in case of an IO problem
     */
    public abstract String getIndexContent() throws IOException;

    /**
     * Get the file of the server bundle.
     *
     * @return File of the server bundle
     * @throws IOException Exception in case of an IO problem
     */
    public abstract File getServerBundle() throws IOException;
}
