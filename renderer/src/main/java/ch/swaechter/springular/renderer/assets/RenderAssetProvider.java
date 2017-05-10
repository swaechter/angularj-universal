package ch.swaechter.springular.renderer.assets;

import java.io.File;
import java.io.IOException;

/**
 * The interface RenderAssetProvider is responsible for providing the assets like the content of the index page and the
 * server bundle as a file. It's possible to develop specific asset providers (Resource, file system etc.) that can
 * support live reloading.
 *
 * @author Simon Wächter
 */
public interface RenderAssetProvider {

    /**
     * Get the content of the index template.
     *
     * @return Content of the index template.
     * @throws IOException Exception in case of an IO problem
     */
    String getIndexContent() throws IOException;

    /**
     * Get the file of the server bundle.
     *
     * @return File of the server bundle
     * @throws IOException Exception in case of an IO problem
     */
    File getServerBundle() throws IOException;

    /**
     * Check if the provider does support live reload.
     *
     * @return Status if the provider supports live reload
     */
    boolean isLiveReloadSupported();

    /**
     * Check if the provider detected an asset chance and wishes a live reload.
     *
     * @return Status of the provider wishes a live reload
     */
    boolean isLiveReloadRequired();
}
