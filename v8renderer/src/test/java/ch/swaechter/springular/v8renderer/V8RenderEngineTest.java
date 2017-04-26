package ch.swaechter.springular.v8renderer;

import ch.swaechter.springular.renderer.RenderConfiguration;
import ch.swaechter.springular.renderer.AbstractRenderEngine;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Future;

/**
 * This class provides a test to guarantee the functionality of the V8 render engine.
 *
 * @author Simon Wächter
 */
public class V8RenderEngineTest {

    /**
     * Test the render engine.
     */
    @Test
    public void testRenderEngine() {
        try {
            File serverfile = new File("/home/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/angular/dist/server.js");
            File indexfile = new File("/home/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/resources/public/index.html");
            //File serverfile = new File("C:/Users/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/angular/dist/server.js");
            //File indexfile = new File("C:/Users/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/resources/public/index.html");

            String content = readFile(indexfile);

            Assert.assertNotNull(serverfile);
            Assert.assertTrue(content.contains("app-root"));

            RenderConfiguration configuration = new RenderConfiguration(serverfile, readFile(indexfile));
            AbstractRenderEngine engine = new V8RenderEngine(configuration);
            engine.startEngine();

            Future<String> future1 = engine.renderPage("/");
            Assert.assertNotNull(future1);
            Assert.assertTrue(future1.get().contains("Home"));

            Future<String> future2 = engine.renderPage("/about");
            Assert.assertNotNull(future2);
            Assert.assertTrue(future2.get().contains("About"));

            engine.stopEngine();
        } catch (Exception exception) {
            Assert.fail("The test failed due an exception: " + exception.getMessage());
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
