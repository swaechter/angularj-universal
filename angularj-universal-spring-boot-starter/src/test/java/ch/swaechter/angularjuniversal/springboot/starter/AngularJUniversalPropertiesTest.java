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
        Assert.assertEquals("/", properties.getRoutes().get(0));
        Assert.assertEquals("/public/index.html", properties.getIndexResourcePath());
        Assert.assertEquals("/server.js", properties.getServerBundleResourcePath());
        Assert.assertEquals(StandardCharsets.UTF_8, properties.getCharset());

        properties.setRoutes(Arrays.asList("/", "/home"));
        properties.setIndexResourcePath("/other/public/index.html");
        properties.setServerBundleResourcePath("/other.server.js");
        properties.setCharset(StandardCharsets.ISO_8859_1);

        Assert.assertEquals(2, properties.getRoutes().size());
        Assert.assertEquals("/", properties.getRoutes().get(0));
        Assert.assertEquals("/home", properties.getRoutes().get(1));
        Assert.assertEquals("/other/public/index.html", properties.getIndexResourcePath());
        Assert.assertEquals("/other.server.js", properties.getServerBundleResourcePath());
        Assert.assertEquals(StandardCharsets.ISO_8859_1, properties.getCharset());
    }
}
