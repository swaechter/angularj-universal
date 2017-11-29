package ch.swaechter.angularjuniversal.springboot.starter;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * This class is responsible for testing the properties.
 *
 * @author Simon Wächter
 */
public class AngularJUniversalPropertiesTest {

    /**
     * Test all property values.
     */
    @Test
    public void testAngularJUniversalProperties() {
        AngularJUniversalProperties properties = new AngularJUniversalProperties();
        Assert.assertEquals(1, properties.getRoutes().size());
        Assert.assertTrue(properties.getRoutes().get(0).equals("/"));
        Assert.assertTrue(properties.getIndexResourcePath().equals("/public/index.html"));
        Assert.assertTrue(properties.getServerBundleResourcePath().equals("/server.js"));
        Assert.assertTrue(properties.getCharset().equals(StandardCharsets.UTF_8));
        Assert.assertEquals(5, properties.getEngines());

        properties.setRoutes(Arrays.asList("/", "/home"));
        properties.setIndexResourcePath("/other/public/index.html");
        properties.setServerBundleResourcePath("/other.server.js");
        properties.setCharset(StandardCharsets.ISO_8859_1);
        properties.setEngines(10);

        Assert.assertEquals(2, properties.getRoutes().size());
        Assert.assertTrue(properties.getRoutes().get(0).equals("/"));
        Assert.assertTrue(properties.getRoutes().get(1).equals("/home"));
        Assert.assertTrue(properties.getIndexResourcePath().equals("/other/public/index.html"));
        Assert.assertTrue(properties.getServerBundleResourcePath().equals("/other.server.js"));
        Assert.assertTrue(properties.getCharset().equals(StandardCharsets.ISO_8859_1));
        Assert.assertEquals(10, properties.getEngines());
    }
}
