package ch.swaechter.angularjuniversal.renderer.engine;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * The interface RenderEngine represents a JavaScript engine that has to handle all incoming requests, render them and
 * push back the result. Each JavaScript engine has to implement this functionality and can use the render queue and
 * asset provider to work with.
 *
 * @author Simon Wächter
 */
public interface RenderEngine {

    /**
     * Start working and handle all incoming requests and resolve them. The engine will work as long it receives a valid
     * and non optional request and will shutdown itself as soon it received an ooptional request from the queue.
     *
     * @param renderRequests      Blocking queue with requests to read from
     * @param renderConfiguration Render configuration with the all required information
     */
    void startWorking(BlockingQueue<Optional<RenderRequest>> renderRequests, RenderConfiguration renderConfiguration);
}
