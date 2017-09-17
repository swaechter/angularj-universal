package ch.swaechter.angularjuniversal.renderer.engine;

/**
 * This interface is used to create new render engines.
 *
 * @author Simon Wächter
 */
public interface RenderEngineFactory {

    /**
     * Create a new render engine.
     *
     * @return New render engine
     */
    RenderEngine createRenderEngine();
}
