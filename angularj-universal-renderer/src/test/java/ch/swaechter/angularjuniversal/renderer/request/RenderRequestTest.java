package ch.swaechter.angularjuniversal.renderer.request;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is responsible for testing the render request.
 *
 * @author Simon Wächter
 */
public class RenderRequestTest {

    /**
     * Test the render request.
     */
    @Test
    public void testRenderRequest() {
        RenderRequest renderrequest = new RenderRequest("/");
        Assert.assertNotNull(renderrequest.getFuture());
        Assert.assertEquals("/", renderrequest.getUri());
        Assert.assertTrue(renderrequest.getUuid().length() > 0);
    }
}
