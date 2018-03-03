package ch.swaechter.angularjuniversal.renderer.configuration;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

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
        String templatecontent = "<app-root></app-root>";
        File serverbundle = new File("server.bundle.js");
        Charset charset = StandardCharsets.UTF_8;
        List<String> routes = Arrays.asList("/", "/home", "/about");
        List<String> emptyroutes = Arrays.asList("/");

        RenderConfiguration renderconfiguration1 = new RenderConfiguration.RenderConfigurationBuilder(templatecontent, serverbundle).engines(10).liveReload(true).charset(charset).routes(routes).build();
        Assert.assertEquals(serverbundle, renderconfiguration1.getServerBundleFile());
        Assert.assertEquals(templatecontent, renderconfiguration1.getTemplateContent());
        Assert.assertEquals(10, renderconfiguration1.getEngines());
        Assert.assertTrue(renderconfiguration1.getLiveReload());
        Assert.assertEquals(charset, renderconfiguration1.getCharset());
        Assert.assertEquals(routes, renderconfiguration1.getRoutes());

        RenderConfiguration renderconfiguration2 = new RenderConfiguration.RenderConfigurationBuilder(templatecontent, serverbundle).build();
        Assert.assertEquals(serverbundle, renderconfiguration2.getServerBundleFile());
        Assert.assertEquals(templatecontent, renderconfiguration2.getTemplateContent());
        Assert.assertEquals(4, renderconfiguration2.getEngines());
        Assert.assertFalse(renderconfiguration2.getLiveReload());
        Assert.assertEquals(charset, renderconfiguration2.getCharset());
        Assert.assertEquals(emptyroutes, renderconfiguration2.getRoutes());
    }
}
