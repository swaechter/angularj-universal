package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.data.DataLoader;
import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

/**
 * This class provides a test to guarantee the functionality of the V8 renderer.
 *
 * @author Simon Wächter
 */
public class V8RenderEngineTest {

    /**
     * Test the renderer.
     */
    @Test
    public void testRenderEngine() throws Exception {
        DataLoader dataloader = new DataLoader();
        InputStream indexinputstream = dataloader.getIndexAsInputStream();
        InputStream serverbundleinputstream = dataloader.getServerBundleAsInputStream();
        Assert.assertNotNull(indexinputstream);
        Assert.assertNotNull(serverbundleinputstream);

        String templatecontent = RenderUtils.getStringFromInputStream(indexinputstream, StandardCharsets.UTF_8);
        File serverbundlefile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", ".js", serverbundleinputstream);
        Assert.assertTrue(templatecontent.contains("app-root"));
        Assert.assertTrue(serverbundlefile.exists());

        RenderEngineFactory renderenginefactory = new V8RenderEngineFactory();
        RenderConfiguration renderconfiguration = new RenderConfiguration(templatecontent, serverbundlefile, 5, false);
        Renderer renderer = new Renderer(renderenginefactory, renderconfiguration);

        Assert.assertFalse(renderer.isRendererRunning());
        renderer.startRenderer();
        Assert.assertTrue(renderer.isRendererRunning());
        renderer.stopRenderer();
        Assert.assertFalse(renderer.isRendererRunning());
        renderer.startRenderer();
        Assert.assertTrue(renderer.isRendererRunning());

        Future<String> future1 = renderer.addRenderRequest("/");
        Assert.assertNotNull(future1);
        Assert.assertTrue(future1.get().contains("Home"));

        Future<String> future2 = renderer.addRenderRequest("/home");
        Assert.assertNotNull(future2);
        Assert.assertTrue(future2.get().contains("Home"));

        Future<String> future3 = renderer.addRenderRequest("/keywords");
        Assert.assertNotNull(future3);
        Assert.assertTrue(future3.get().contains("Keywords"));

        Future<String> future4 = renderer.addRenderRequest("/keywords/1");
        Assert.assertNotNull(future4);
        Assert.assertTrue(future4.get().contains("Dummy keyword"));

        Future<String> future5 = renderer.addRenderRequest("/about");
        Assert.assertNotNull(future5);
        Assert.assertTrue(future5.get().contains("About"));

        Future<String> future6 = renderer.addRenderRequest("/invalid");
        Assert.assertNotNull(future6);
        Assert.assertTrue(future6.get().contains("Home"));

        renderer.stopRenderer();
    }
}
