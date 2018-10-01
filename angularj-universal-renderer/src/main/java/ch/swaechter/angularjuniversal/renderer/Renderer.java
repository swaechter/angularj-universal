package ch.swaechter.angularjuniversal.renderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;

import java.io.File;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The class Render provides a manager to render requests with the render engine and the render configuration.
 *
 * @author Simon Wächter
 */
public class Renderer {

    /**
     * Queue with all active render requests.
     */
    private final BlockingQueue<Optional<RenderRequest>> renderRequests;

    /**
     * Render configuration with all important information.
     */
    private final RenderConfiguration renderConfiguration;

    /**
     * Render engine factory for creating new render requests.
     */

    private final RenderEngineFactory renderEngineFactory;

    /**
     * Current render engine.
     */
    private RenderEngine renderEngine;

    /**
     * Date of the server bundle file, used for live reloading.
     */
    private Date startDate;

    /**
     * Create a new render that uses the render engine factory to for creating a render engine based on the given
     * configuration.
     *
     * @param renderConfiguration Render configuration used as main configuration
     * @param renderEngineFactory Render engine factory used for creating new render engines
     */
    public Renderer(RenderConfiguration renderConfiguration, RenderEngineFactory renderEngineFactory) {
        this.renderRequests = new LinkedBlockingDeque<>();
        this.renderConfiguration = renderConfiguration;
        this.renderEngineFactory = renderEngineFactory;
    }

    /**
     * Start the renderer. If the renderer is already running, this has no impact.
     */
    public synchronized void startRenderer() {
        if (renderEngine != null) {
            return;
        }

        startDate = new Date();
        renderEngine = renderEngineFactory.createRenderEngine();
        Thread engineThread = new Thread(() -> renderEngine.startWorking(renderRequests, renderConfiguration));
        engineThread.start();

        if (renderConfiguration.getLiveReload()) {
            Thread reloadThread = new Thread(() -> {
                while (isRendererRunning()) {
                    File file = renderConfiguration.getServerBundleFile();
                    if (startDate.before(new Date(file.lastModified()))) {
                        stopRenderer();
                        startRenderer();
                    }
                }
            });
            reloadThread.start();
        }
    }

    /**
     * Stop the render engine while waiting for rendering all render requests. If the renderer is already stopped,
     * this has no impact.
     */
    public synchronized void stopRenderer() {
        if (renderEngine == null) {
            return;
        }

        renderRequests.add(Optional.empty());

        while (renderRequests.size() != 0) {
            // Wait for an empty request queue
        }

        renderEngine = null;
    }

    /**
     * Check if the render engine is already running.
     *
     * @return Status of the check
     */
    public synchronized boolean isRendererRunning() {
        return renderEngine != null;
    }

    /**
     * Add a new render request and receive a future, that can be resolved as soon the render request has been rendered.
     *
     * @param uri URI of the render request
     * @return Future that can be accessed later on to get the rendered content
     */
    public Future<String> addRenderRequest(String uri) {
        RenderRequest renderRequest = new RenderRequest(uri);
        renderRequests.add(Optional.of(renderRequest));
        return renderRequest.getFuture();
    }
}
