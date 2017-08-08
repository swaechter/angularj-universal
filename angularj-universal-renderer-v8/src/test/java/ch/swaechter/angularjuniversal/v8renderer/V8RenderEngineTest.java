package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.data.DataLoader;
import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjuniversal.renderer.assets.ResourceProvider;
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
    public void testRenderEngine() throws Exception {
        DataLoader dataloader = new DataLoader();
        InputStream indexinputstream = dataloader.getIndexAsInputStream();
        InputStream serverbundleinputstream = dataloader.getServerBundleAsInputStream();

        RenderAssetProvider provider = new ResourceProvider(indexinputstream, serverbundleinputstream, StandardCharsets.UTF_8);
        Assert.assertTrue(provider.getIndexContent().contains("app-root"));
        Assert.assertNotNull(provider.getServerBundle());

        Renderer renderer = new Renderer(new V8RenderEngine(), provider);
        renderer.startEngine();

        Future<String> future1 = renderer.renderRequest("/");
        Assert.assertNotNull(future1);
        Assert.assertTrue(future1.get().contains("Overview"));

        Future<String> future2 = renderer.renderRequest("/overview");
        Assert.assertNotNull(future2);
        Assert.assertTrue(future2.get().contains("Overview"));

        Future<String> future3 = renderer.renderRequest("/about");
        Assert.assertNotNull(future3);
        Assert.assertTrue(future3.get().contains("About"));

        renderer.stopEngine();
    }
}
