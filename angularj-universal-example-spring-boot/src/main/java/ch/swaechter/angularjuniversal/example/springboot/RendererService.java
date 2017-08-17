package ch.swaechter.angularjuniversal.example.springboot;

import ch.swaechter.angularjuniversal.data.DataLoader;
import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjuniversal.renderer.assets.ResourceProvider;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

/**
 * This class provides a service for rendering page requests.
 *
 * @author Simon Wächter
 */
@Service
public class RendererService {

    /**
     * Renderer that will render the page.
     */
    private final Renderer renderer;

    /**
     * Create a new render service.
     *
     * @throws IOException Exception in case we are unable to read the assets
     */
    public RendererService() throws IOException {
        DataLoader dataloader = new DataLoader();
        InputStream indexinputstream = dataloader.getIndexAsInputStream();
        InputStream serverbundleinputstream = dataloader.getServerBundleAsInputStream();
        RenderAssetProvider provider = new ResourceProvider(indexinputstream, serverbundleinputstream, StandardCharsets.UTF_8);
        renderer = new Renderer(new V8RenderEngine(), provider);
        renderer.startEngine();
    }

    /**
     * Render a page.
     *
     * @param uri URI of the page request
     * @return Rendered page
     * @throws Exception Exception in case it's impossible to resolve the future or due a render engine error
     */
    public String renderPage(String uri) throws Exception {
        Future<String> future = renderer.renderRequest(uri);
        return future.get();
    }
}
