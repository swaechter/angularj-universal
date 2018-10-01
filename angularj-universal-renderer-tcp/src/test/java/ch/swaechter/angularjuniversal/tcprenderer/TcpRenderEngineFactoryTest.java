package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class provides a test to guarantee the factory functionality.
 *
 * @author Simon Wächter
 */
public class TcpRenderEngineFactoryTest {

    /**
     * Test the render engine factory functionality.
     */
    @Test
    public void testRenderEngineFactory() {
        RenderEngineFactory renderEngineFactory = new TcpRenderEngineFactory();
        RenderEngine renderEngine = renderEngineFactory.createRenderEngine();
        Assert.assertEquals(TcpRenderEngine.class.getName(), renderEngine.getClass().getName());
    }
}
