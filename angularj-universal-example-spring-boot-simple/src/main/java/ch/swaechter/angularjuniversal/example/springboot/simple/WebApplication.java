package ch.swaechter.angularjuniversal.example.springboot.simple;

import ch.swaechter.angularjuniversal.keywords.Keyword;
import ch.swaechter.angularjuniversal.keywords.KeywordService;
import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.tcprenderer.TcpRenderEngineFactory;
import org.jetbrains.annotations.NotNull;
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

        @NotNull
        private final RenderService renderService;

        @Autowired
        public ContentController(@NotNull RenderService renderService) {
            this.renderService = renderService;
        }

        @ResponseBody
        @GetMapping({"/", "/home"})
        @NotNull
        public String showHome() throws Exception {
            return renderService.renderPage("/home").get();
        }

        @ResponseBody
        @GetMapping("/keywords")
        @NotNull
        public String showLogin() throws Exception {
            return renderService.renderPage("/keywords").get();
        }

        @ResponseBody
        @GetMapping("/keywords/{id}")
        @NotNull
        public String showLogout(@PathVariable("id") int id) throws Exception {
            return renderService.renderPage("/keywords/" + id).get();
        }

        @ResponseBody
        @GetMapping("/about")
        @NotNull
        public String showPage() throws Exception {
            return renderService.renderPage("/about").get();
        }
    }

    @RestController
    @RequestMapping("/api")
    public class KeywordController {

        @NotNull
        private final KeywordService keywordService;

        public KeywordController() {
            this.keywordService = new KeywordService();
        }

        @GetMapping("/keyword")
        @NotNull
        public ResponseEntity<List<Keyword>> getKeywords() {
            return new ResponseEntity<>(keywordService.getKeywords(), HttpStatus.OK);
        }
    }

    @Service
    public class RenderService {

        @NotNull
        private final Renderer renderer;

        @NotNull
        public RenderService() throws IOException {
            // Load the template and create a temporary server bundle file from the resource (This file will of course never change until manually edited)
            InputStream templateInputStream = getClass().getResourceAsStream("/public/index.html");
            InputStream serverBundleInputStream = getClass().getResourceAsStream("/server.js");
            String templateContent = RenderUtils.getStringFromInputStream(templateInputStream, StandardCharsets.UTF_8);
            File serverBundleFile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", "tmp", serverBundleInputStream);
            // File serverBundleFile = new File("<Local server bundle on the file system>"); --> Also enable auto reload in the configuration

            // Create the configuration. For real live reloading, don't use a temporary file but the real generated on from the file system
            // The string "node" is the path or executable name of the Node.js process and has to match/be found
            RenderConfiguration renderConfiguration = new RenderConfiguration.RenderConfigurationBuilder("node", 9090, serverBundleFile, templateContent).liveReload(false).build();

            // Create the TCP render engine factory for spawning render engines
            RenderEngineFactory renderEngineFactory = new TcpRenderEngineFactory();

            // Create and start the renderer
            this.renderer = new Renderer(renderConfiguration, renderEngineFactory);
            this.renderer.startRenderer();
        }

        @NotNull
        Future<String> renderPage(@NotNull String uri) {
            // Render a request and return a resolvable future
            return renderer.addRenderRequest(uri);
        }
    }
}
