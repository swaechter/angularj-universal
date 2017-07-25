package ch.swaechter.angularjssr.example;

import ch.swaechter.angularjssr.renderer.Renderer;
import ch.swaechter.angularjssr.renderer.assets.FilesystemProvider;
import ch.swaechter.angularjssr.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjssr.v8renderer.V8RenderEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
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
        File indexfile = createTemporaryFileFromInputStream(getResourceAsInputStream("/index.html"));
        File serverbundlefile = createTemporaryFileFromInputStream(getResourceAsInputStream("/server.bundle.js"));
        RenderAssetProvider provider = new FilesystemProvider(indexfile, serverbundlefile, StandardCharsets.UTF_8);
        //RenderAssetProvider provider = new ResourceProvider(getResourceAsInputStream("index.html"), getResourceAsInputStream("server.bundle.js"), StandardCharsets.UTF_8);
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

    /**
     * Create a temporary file from an input stream.
     *
     * @param inputstream Input stream with the file content
     * @return Temporary file
     * @throws IOException Exception in case of an IO problem
     */
    private File createTemporaryFileFromInputStream(InputStream inputstream) throws IOException {
        File file = File.createTempFile("renderer", ".tmp");
        OutputStream outputstream = new FileOutputStream(file);
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return file;
    }
}
