package ch.swaechter.springular.application;

import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.V8RenderEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class RendererService {

    private final RenderEngine renderer;

    public RendererService() throws Exception {
        File serverfile = new File("/home/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/angular/dist/server.js");//createLocalFile("server.js");
        String indexfile = readFile("public/index.html");
        //File serverfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/dist/server.js");
        //File indexfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/foo/index.html");
        this.renderer = new V8RenderEngine(serverfile, indexfile);
        this.renderer.startEngine();
    }

    public String renderPage(String uri) {
        return renderer.renderPage(uri);
    }

    private File createLocalFile(String resourcefile) throws IOException {
        File file = new File(resourcefile);
        file.createNewFile();
        Resource resource = new ClassPathResource(resourcefile);
        InputStream inputstream = resource.getInputStream();
        FileOutputStream outputstream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputstream.read(buffer)) != -1) {
            outputstream.write(buffer, 0, len);
        }
        return file;
    }

    private String readFile(String resourcefile) throws IOException {
        Resource resource = new ClassPathResource(resourcefile);
        InputStream inputstream = resource.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputstream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
