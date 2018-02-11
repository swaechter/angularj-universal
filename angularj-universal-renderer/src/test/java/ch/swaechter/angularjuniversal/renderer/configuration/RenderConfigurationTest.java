package ch.swaechter.angularjuniversal.renderer.configuration;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * This class is responsible for testing the render configuration.
 *
 * @author Simon Wächter
 */
public class RenderConfigurationTest {

    /**
     * Test the render configuration.
     */
    @Test
    public void testRenderConfiguration() {
        File serverbundle = new File("server.bundle.js");
        RenderConfiguration renderconfiguration = new RenderConfiguration("<app-root></app-root>", serverbundle, 10, true);
        Assert.assertEquals(serverbundle, renderconfiguration.getServerBundleFile());
        Assert.assertEquals("<app-root></app-root>", renderconfiguration.getTemplateContent());
        Assert.assertEquals(10, renderconfiguration.getEngines());
        Assert.assertTrue(renderconfiguration.getLiveReload());
    }
}
