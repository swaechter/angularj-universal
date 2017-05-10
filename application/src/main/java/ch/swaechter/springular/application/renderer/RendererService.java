package ch.swaechter.springular.application.renderer;

import ch.swaechter.springular.renderer.Renderer;
import ch.swaechter.springular.renderer.assets.RenderAssetProvider;
import ch.swaechter.springular.renderer.assets.ResourceProvider;
import ch.swaechter.springular.v8renderer.V8RenderEngine;
import org.springframework.core.io.ClassPathResource;
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
        RenderAssetProvider provider = new ResourceProvider(getResourceAsInputStream("index.html"), getResourceAsInputStream("server.bundle.js"), StandardCharsets.UTF_8);
        this.renderer = new Renderer(new V8RenderEngine(), provider);
        this.renderer.startEngine();
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

    /**
     * Get a resource as input stream.
     *
     * @param resourcepath Path to the resource
     * @return Input stream based on the resource
     * @throws IOException Exception in case of an IO problem
     */
    private InputStream getResourceAsInputStream(String resourcepath) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcepath);
        return resource.getInputStream();
    }
}
