package ch.swaechter.springular.application;

import ch.swaechter.springular.renderer.RenderConfiguration;
import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.RenderUtils;
import ch.swaechter.springular.v8renderer.V8RenderEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
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
     * Render engine that will render the page.
     */
    private final RenderEngine renderer;

    /**
     * Create a new render service.
     *
     * @throws Exception Exception in case we are unable to read the files
     */
    public RendererService() throws Exception {
        String indexcontent = getResourceAsString("index.html");
        File serverbundle = RenderUtils.createTemporaryFile(getResourceAsString("server.bundle.js"));
        RenderConfiguration configuration = new RenderConfiguration(indexcontent, serverbundle);
        this.renderer = new V8RenderEngine(configuration);
        this.renderer.startEngine();
    }

    /**
     * Render a page.
     *
     * @param uri URI of the page request
     * @return Rendered page
     * @throws Exception Exception in case it's impossible to resolve the future
     */
    public String renderPage(String uri) throws Exception {
        Future<String> future = renderer.renderPage(uri);
        return future.get();
    }

    /**
     * Get the file content from an internal resource
     *
     * @param resourcepath Path to the resource
     * @return Content of the resource
     * @throws IOException
     */
    public String getResourceAsString(String resourcepath) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcepath);
        byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(data, StandardCharsets.UTF_8);
    }
}
