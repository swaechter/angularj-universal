package ch.swaechter.springular.application;

import ch.swaechter.springular.renderer.RenderConfiguration;
import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.v8renderer.V8RenderEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * This class provides a service for rendering page requests.
 *
 * @author Simon Wächter
 */
@Service
public class RendererService {

    /**
     * File path to the server bundle.
     *
     * @TODO: Don't hardcode this path, but integrate all dependencies into the webpack build.
     */
    private static final String SERVER_FILE = "/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/dist/server.js";

    /**
     * File path to the index page.
     */
    private static final String INDEX_FILE = "public/index.html";

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
        RenderConfiguration configuration = new RenderConfiguration(new File(SERVER_FILE), readFile(readFile(INDEX_FILE)));
        RenderEngine engine = new V8RenderEngine(configuration);
        this.renderer = engine;
        this.renderer.start();
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
     * Read a file from the resources and return the file content.
     *
     * @param resourcefile Path of the resource file
     * @return File content
     * @throws IOException Exception in case of an IO problem
     */
    private String readFile(String resourcefile) throws IOException {
        Resource resource = new ClassPathResource(resourcefile);
        InputStream inputstream = resource.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int length;
        byte[] buffer = new byte[1024];
        while ((length = inputstream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
