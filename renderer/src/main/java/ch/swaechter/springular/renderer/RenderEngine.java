package ch.swaechter.springular.renderer;

/**
 * The class RenderEngine provides an interface for rendering an Angular page with a JavaScript engine. A class that
 * implements this interface has ti start/stop the engine and take care of the incoming requests and provide a parsed
 * page.
 *
 * @author Simon Wächter
 */
public interface RenderEngine {

    /**
     * Start the render engine.
     */
    void startEngine();

    /**
     * Stop the render engine.
     */
    void stopEngine();

    /**
     * Render a page based on the given URI. This method should behave blocking and only provide a result if the engine
     * was able to parse the page.
     *
     * @param uri URI of the request
     * @return Fully parsed page that can be served to the client
     */
    String renderPage(String uri);
}
