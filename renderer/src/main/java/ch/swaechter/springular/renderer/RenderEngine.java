package ch.swaechter.springular.renderer;

import java.util.concurrent.Future;

/**
 * The interface RenderEngine represents the method a render engine provides. Someone can implement this interface
 * or use the existing abstract reference implementation.
 *
 * @author Simon Wächter
 */
public interface RenderEngine {

    /**
     * Start the render engine. If render requests were already added, the will be rendered.
     */
    void startEngine();

    /**
     * Stop the render engine. If render requests are still there, the render engine will render them before stopping.
     */
    void stopEngine();

    /**
     * Check if the render engine is running.
     *
     * @return Result of the check
     */
    boolean isEngineRunning();

    /**
     * Add a new render request and receive a future, that can be resolved as soon the render request has been rendered.
     *
     * @param uri URI of the render request
     * @return Future that can be accessed later on to get the rendered content
     */
    Future<String> renderRequest(String uri);
}
