package ch.swaechter.angularjuniversal.renderer.engine;

import org.jetbrains.annotations.NotNull;

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
    @NotNull
    RenderEngine createRenderEngine();
}
