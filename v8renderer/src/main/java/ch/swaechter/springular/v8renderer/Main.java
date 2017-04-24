package ch.swaechter.springular.v8renderer;

import ch.swaechter.springular.renderer.RenderConfiguration;
import ch.swaechter.springular.renderer.RenderEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Future;

/**
 * This class provides a test example for the V8 render engine.
 *
 * @author Simon Wächter
 */
public class Main {

    /**
     * Start the application.
     *
     * @param args Unused application parameters
     */
    public static void main(String[] args) {
        new Main();
    }

    /**
     * Create a V8 render engine and render a few page requests.
     */
    Main() {
        try {
            File serverfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/dist/server.js");
            File indexfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/output/index.html");

            RenderConfiguration configuration = new RenderConfiguration(serverfile, readFile(indexfile));
            RenderEngine engine = new V8RenderEngine(configuration);
            engine.start();
            System.out.println("Engine started");

            Future<String> future1 = engine.renderPage("/");
            System.out.println("Value of /: " + future1.get());

            Future<String> future2 = engine.renderPage("/about");
            System.out.println("Value of /about: " + future2.get());

            engine.shutdown();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read the index file", exception);
        }
    }

    /**
     * Read a file and return the file content.
     *
     * @param file File to read
     * @return File content
     * @throws IOException Exception in case of an IO problem
     */
    private String readFile(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded);
    }
}
