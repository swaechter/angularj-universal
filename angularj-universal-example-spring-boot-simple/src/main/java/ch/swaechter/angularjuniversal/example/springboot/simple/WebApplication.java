package ch.swaechter.angularjuniversal.example.springboot.simple;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RestController
    public class ContentController {

        private final RenderService renderservice;

        @Autowired
        public ContentController(RenderService renderservice) {
            this.renderservice = renderservice;
        }

        @ResponseBody
        @GetMapping("/")
        public String showIndex() throws Exception {
            return renderservice.renderPage("/").get();
        }

        @ResponseBody
        @GetMapping("/login")
        public String showLogin() throws Exception {
            return renderservice.renderPage("/login").get();
        }

        @ResponseBody
        @GetMapping("/logout")
        public String showLogout() throws Exception {
            return renderservice.renderPage("/logout").get();
        }

        @ResponseBody
        @GetMapping("/page")
        public String showPage() throws Exception {
            return renderservice.renderPage("/page").get();
        }

        @ResponseBody
        @GetMapping("/page/home")
        public String showPageHome() throws Exception {
            return renderservice.renderPage("/page/home").get();
        }

        @ResponseBody
        @GetMapping("/page/about")
        public String showPageAbout() throws Exception {
            return renderservice.renderPage("/page/about").get();
        }
    }

    @Service
    public class RenderService {

        private final Renderer renderer;

        public RenderService() throws IOException {
            // Load the template and create a temporary server bundle file from the resource (This file will of course never change until manually edited)
            InputStream templateinputstream = getClass().getResourceAsStream("/public/index.html");
            InputStream serverbundleinputstream = getClass().getResourceAsStream("/server.js");
            String templatecontent = RenderUtils.getStringFromInputStream(templateinputstream, StandardCharsets.UTF_8);
            File serverbundlefile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", "tmp", serverbundleinputstream);
            // File localserverbundlefile = new File("<Local server bundle on the file system>"); --> Also enable auto reload in the configuration

            // Create the configuration. For real live reloading, don't use a temporary file but the real generated on from the file system
            RenderConfiguration configuration = new RenderConfiguration(templatecontent, serverbundlefile, 4, false);

            // Create the V8 render engine factory for spawning render engines
            RenderEngineFactory factory = new V8RenderEngineFactory();

            // Create and start the renderer
            this.renderer = new Renderer(factory, configuration);
            this.renderer.startRenderer();
        }

        Future<String> renderPage(String uri) {
            // Render a request and return a resolvable future
            return renderer.addRenderRequest(uri);
        }
    }
}
