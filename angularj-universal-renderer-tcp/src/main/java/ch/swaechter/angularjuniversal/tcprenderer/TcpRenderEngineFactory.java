package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Provide a TCP render engine factory which is able to create new TCP render engines.
 *
 * @author Simon Wächter
 */
public class TcpRenderEngineFactory implements RenderEngineFactory {

    /**
     * Create a new TCP render engine.
     *
     * @return New TCP render engine
     */
    @Override
    @NotNull
    public RenderEngine createRenderEngine() {
        return new TcpRenderEngine();
    }
}
