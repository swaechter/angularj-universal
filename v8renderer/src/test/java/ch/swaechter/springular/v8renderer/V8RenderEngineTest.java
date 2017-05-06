package ch.swaechter.springular.v8renderer;

import ch.swaechter.springular.renderer.RenderConfiguration;
import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.RenderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
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
            String indexcontent = RenderUtils.readFile(this, "/index.html");
            Assert.assertTrue(indexcontent.contains("app-root"));

            String serverbundlecontent = RenderUtils.readFile(this, "/server.bundle.js");
            File serverbundle = RenderUtils.createTemporaryFile(serverbundlecontent);
            Assert.assertNotNull(serverbundle);

            RenderConfiguration configuration = new RenderConfiguration(indexcontent, serverbundle);
            RenderEngine engine = new V8RenderEngine(configuration);
            engine.startEngine();

            Future<String> future1 = engine.renderPage("/");
            Assert.assertNotNull(future1);
            Assert.assertTrue(future1.get().contains("Overview"));

            Future<String> future2 = engine.renderPage("/overview");
            Assert.assertNotNull(future2);
            Assert.assertTrue(future2.get().contains("Overview"));

            Future<String> future3 = engine.renderPage("/about");
            Assert.assertNotNull(future3);
            Assert.assertTrue(future3.get().contains("About"));

            engine.stopEngine();
        } catch (Exception exception) {
            Assert.fail("The test failed due an exception: " + exception.getMessage());
        }
    }
}
