package ch.swaechter.angularjuniversal.renderer;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The class Render provides a manager to render requests with the render engine and the render configuration.
 *
 * @author Simon Wächter
 */
public final class Renderer {

    /**
     * Render engine factory for creating new render requests.
     */
    private final RenderEngineFactory renderenginefactory;

    /**
     * List with all active render engines.
     */
    private final List<RenderEngine> renderengines;

    /**
     * Queue with all active render requests.
     */
    private final BlockingQueue<Optional<RenderRequest>> renderrequests;

    /**
     * Render configuration with all important information.
     */
    private final RenderConfiguration renderconfiguration;

    /**
     * Date of the server bundle file, needed for live reload.
     */
    private Date startdate;

    /**
     * Create a new render engine that used the render engine factory for creating new render engines and the render
     * configuration for the configuration.
     *
     * @param renderenginefactory Render engine factory used for creating new render engines
     * @param renderconfiguration Render configuration used for the configuration
     */
    public Renderer(RenderEngineFactory renderenginefactory, RenderConfiguration renderconfiguration) {
        this.renderenginefactory = renderenginefactory;
        this.renderengines = new ArrayList<>();
        this.renderrequests = new LinkedBlockingDeque<>();
        this.renderconfiguration = renderconfiguration;
    }

    /**
     * Start the renderer. If the renderer is already running, this has no impact.
     */
    public synchronized void startRenderer() {
        if (renderengines.size() != 0) {
            return;
        }

        startdate = new Date();

        for (int i = 0; i < renderconfiguration.getEngines(); i++) {
            RenderEngine renderengine = renderenginefactory.createRenderEngine();
            renderengines.add(renderengine);

            Thread thread = new Thread(() -> {
                renderengine.startWorking(renderrequests, renderconfiguration);
            });
            thread.start();
        }

        if (renderconfiguration.getLiveReload()) {
            Thread thread = new Thread(() -> {
                while (isRendererRunning()) {
                    File file = renderconfiguration.getServerBundleFile();
                    if (startdate.before(new Date(file.lastModified()))) {
                        stopRenderer();
                        startRenderer();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * Stop the render engine while waiting for rendering all render requests. If the renderer is already stopped,
     * this has no impact.
     */
    public synchronized void stopRenderer() {
        if (renderengines.size() == 0) {
            return;
        }

        for (int i = 0; i < renderconfiguration.getEngines(); i++) {
            renderrequests.add(Optional.empty());
        }

        while (renderrequests.size() != 0) {
            // Wait for an empty request queue
        }

        renderengines.clear();
    }

    /**
     * Check if the render engine is already running.
     *
     * @return Status of the check
     */
    public synchronized boolean isRendererRunning() {
        return renderengines.size() != 0;
    }

    /**
     * Add a new render request and receive a future, that can be resolved as soon the render request has been rendered.
     *
     * @param uri URI of the render request
     * @return Future that can be accessed later on to get the rendered content
     */
    public Future<String> addRenderRequest(String uri) {
        RenderRequest renderrequest = new RenderRequest(uri);
        renderrequests.add(Optional.of(renderrequest));
        return renderrequest.getFuture();
    }
}
