package ch.swaechter.springular.v8renderer;

import ch.swaechter.springular.renderer.RenderEngine;
import ch.swaechter.springular.renderer.assets.AssetProvider;
import ch.swaechter.springular.renderer.assets.ResourceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
            InputStream indexinputstream = getClass().getResourceAsStream("/index.html");
            Assert.assertNotNull(indexinputstream);

            InputStream serverbundleinputstream = getClass().getResourceAsStream("/server.bundle.js");
            Assert.assertNotNull(serverbundleinputstream);

            AssetProvider provider = new ResourceProvider(indexinputstream, serverbundleinputstream, StandardCharsets.UTF_8);
            Assert.assertTrue(provider.getIndexContent().contains("app-root"));
            Assert.assertNotNull(provider.getServerBundle());

            RenderEngine engine = new V8RenderEngine(provider);
            engine.startEngine();

            Future<String> future1 = engine.renderRequest("/");
            Assert.assertNotNull(future1);
            Assert.assertTrue(future1.get().contains("Overview"));

            Future<String> future2 = engine.renderRequest("/overview");
            Assert.assertNotNull(future2);
            Assert.assertTrue(future2.get().contains("Overview"));

            Future<String> future3 = engine.renderRequest("/about");
            Assert.assertNotNull(future3);
            Assert.assertTrue(future3.get().contains("About"));

            engine.stopEngine();
        } catch (Exception exception) {
            Assert.fail("The test failed due an exception: " + exception.getMessage());
        }
    }
}
