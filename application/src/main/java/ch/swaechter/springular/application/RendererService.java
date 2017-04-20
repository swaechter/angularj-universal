package ch.swaechter.springular.application;

import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.V8RenderEngine;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class RendererService {

    private final RenderEngine renderer;

    public RendererService() throws Exception {
        File serverfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/dist/server.js");
        File indexfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/foo/index.html");
        this.renderer = new V8RenderEngine(serverfile, readFile(indexfile));
        this.renderer.startEngine();
    }

    public String renderPage(String uri) {
        return renderer.renderPage(uri);
    }

    private String readFile(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded);
    }
}
