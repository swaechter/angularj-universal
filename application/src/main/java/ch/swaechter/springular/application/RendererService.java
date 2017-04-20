package ch.swaechter.springular.application;

import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.V8RenderEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class RendererService {

    /**
     * @TODO: Don't hardcode this path, but integrate all dependencies into the webpack build.
     */
    private static final String SERVER_FILE = "/home/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/angular/dist/server.js";

    private final RenderEngine renderer;

    public RendererService() throws Exception {
        File serverfile = new File(SERVER_FILE);
        String indexfile = readFile("public/index.html");
        this.renderer = new V8RenderEngine(serverfile, indexfile);
        this.renderer.startEngine();
    }

    public String renderPage(String uri) {
        return renderer.renderPage(uri);
    }

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
