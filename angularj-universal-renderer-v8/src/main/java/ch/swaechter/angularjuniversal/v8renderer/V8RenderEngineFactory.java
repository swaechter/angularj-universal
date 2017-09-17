package ch.swaechter.angularjuniversal.v8renderer;

import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;

/**
 * Provide a V8 render engine factory which is able to create new V8 render engines.
 *
 * @author Simon Wächter
 */
public class V8RenderEngineFactory implements RenderEngineFactory {

    /**
     * Create a new V8 render engine.
     *
     * @return New V8 render engine
     */
    @Override
    public RenderEngine createRenderEngine() {
        return new V8RenderEngine();
    }
}
