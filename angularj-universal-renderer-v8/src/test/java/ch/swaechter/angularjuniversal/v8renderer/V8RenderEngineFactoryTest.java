package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class provides a test to guarantee the factory functionality.
 *
 * @author Simon Wächter
 */
public class V8RenderEngineFactoryTest {

    /**
     * Test the render engine factory functionality.
     */
    @Test
    public void testRenderEngineFactory() {
        RenderEngineFactory renderenginefactory = new V8RenderEngineFactory();
        RenderEngine renderengine = renderenginefactory.createRenderEngine();
        Assert.assertEquals(V8RenderEngine.class.getName(), renderengine.getClass().getName());
    }
}
