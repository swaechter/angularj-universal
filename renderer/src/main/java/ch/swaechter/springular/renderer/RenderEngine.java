package ch.swaechter.springular.renderer;

import java.util.concurrent.Future;

/**
 * The interface RenderEngine provides a way to access the render engine. Each JavaScript engine like V8 has to
 * implement this interface.
 *
 * @author Simon Wächter
 */
public interface RenderEngine {

    /**
     * Start the engine and render page requests. If requests were already added, the render engine will render them.
     */
    void startEngine();

    /**
     * Stop the engine, as soon all render requests have been finished (Method call is blocking).
     */
    void stopEngine();

    /**
     * Render a page request based on the URI and return a Future that can be accessed later on.
     *
     * @param uri URI of the render request
     * @return Future with the page result that can be accessed later on
     */
    Future<String> renderPage(String uri);
}
