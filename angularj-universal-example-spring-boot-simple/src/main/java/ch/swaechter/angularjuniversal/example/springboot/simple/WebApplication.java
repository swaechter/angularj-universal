package ch.swaechter.angularjuniversal.example.springboot.simple;

import ch.swaechter.angularjuniversal.keywords.Keyword;
import ch.swaechter.angularjuniversal.keywords.KeywordService;
import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RestController
    public class ContentController {

        private final RenderService renderService;

        @Autowired
        public ContentController(RenderService renderService) {
            this.renderService = renderService;
        }

        @ResponseBody
        @GetMapping({"/", "/home"})
        public String showHome() throws Exception {
            return renderService.renderPage("/home").get();
        }

        @ResponseBody
        @GetMapping("/keywords")
        public String showLogin() throws Exception {
            return renderService.renderPage("/keywords").get();
        }

        @ResponseBody
        @GetMapping("/keywords/{id}")
        public String showLogout(@PathVariable("id") int id) throws Exception {
            return renderService.renderPage("/keywords/" + id).get();
        }

        @ResponseBody
        @GetMapping("/about")
        public String showPage() throws Exception {
            return renderService.renderPage("/about").get();
        }
    }

    @RestController
    @RequestMapping("/api")
    public class KeywordController {

        private final KeywordService keywordService;

        public KeywordController() {
            this.keywordService = new KeywordService();
        }

        @GetMapping("/keyword")
        public ResponseEntity<List<Keyword>> getKeywords() {
            return new ResponseEntity<>(keywordService.getKeywords(), HttpStatus.OK);
        }
    }

    @Service
    public class RenderService {

        private final Renderer renderer;

        public RenderService() throws IOException {
            // Load the template and create a temporary server bundle file from the resource (This file will of course never change until manually edited)
            InputStream templateInputStream = getClass().getResourceAsStream("/public/index.html");
            InputStream serverBundleInputStream = getClass().getResourceAsStream("/server.js");
            String templateContent = RenderUtils.getStringFromInputStream(templateInputStream, StandardCharsets.UTF_8);
            File serverBundleFile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", "tmp", serverBundleInputStream);
            // File serverBundleFile = new File("<Local server bundle on the file system>"); --> Also enable auto reload in the configuration

            // Create the configuration. For real live reloading, don't use a temporary file but the real generated on from the file system
            RenderConfiguration renderConfiguration = new RenderConfiguration.RenderConfigurationBuilder(templateContent, serverBundleFile).liveReload(false).build();

            // Create the V8 render engine factory for spawning render engines
            RenderEngineFactory renderEngineFactory = new V8RenderEngineFactory();

            // Create and start the renderer
            this.renderer = new Renderer(renderConfiguration, renderEngineFactory);
            this.renderer.startRenderer();
        }

        Future<String> renderPage(String uri) {
            // Render a request and return a resolvable future
            return renderer.addRenderRequest(uri);
        }
    }
}
