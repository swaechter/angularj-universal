package ch.swaechter.springular.renderer;

import ch.swaechter.springular.renderer.assets.RenderAssetProvider;
import ch.swaechter.springular.renderer.engine.RenderEngine;
import ch.swaechter.springular.renderer.queue.RenderFuture;
import ch.swaechter.springular.renderer.queue.RenderQueue;

import java.util.concurrent.Future;

/**
 * The class Render provides a manager to render requests with the render engine based on the data from the asset
 * manager.
 *
 * @author Simon Wächter
 */
public class Renderer {

    /**
     * Queue that is used to store the render requests.
     */
    private final RenderQueue queue;

    /**
     * Engine that is used to render requests.
     */
    private final RenderEngine engine;

    /**
     * Provider that is used to access the assets.
     */
    private final RenderAssetProvider provider;

    /**
     * Create a new render engine and use the render engine and render provider to access the assets.
     *
     * @param provider Render provider to access the assets
     */
    public Renderer(RenderEngine engine, RenderAssetProvider provider) {
        this.queue = new RenderQueue();
        this.engine = engine;
        this.provider = provider;
    }

    /**
     * Start the render engine. If render requests were already added, the will be rendered.
     */
    public void startEngine() {
        if (engine.isWorking()) {
            return;
        }

        new Thread(() -> engine.doWork(queue, provider)).start();
        new Thread(() -> checkAssets()).start();
    }

    /**
     * Stop the render engine. If render requests are still there, the render engine will render them before stopping.
     */
    public void stopEngine() {
        if (!engine.isWorking()) {
            return;
        }

        while (!queue.isQueuePending()) {
            sleep(50);
        }

        engine.stopWork();
    }

    /**
     * Check if the render engine is running.
     *
     * @return Result of the check
     */
    public boolean isEngineRunning() {
        return engine.isWorking();
    }

    /**
     * Add a new render request and receive a future, that can be resolved as soon the render request has been rendered.
     *
     * @param uri URI of the render request
     * @return Future that can be accessed later on to get the rendered content
     */
    public Future<String> renderRequest(String uri) {
        RenderFuture future = queue.createRenderFuture(uri);
        return future.getCompletableFuture();
    }

    /**
     * Check if the assets require a reload.
     */
    private void checkAssets() {
        if (provider.isLiveReloadSupported()) {
            while (engine.isWorking()) {
                if (provider.isLiveReloadRequired()) {
                    engine.stopWork();
                    engine.doWork(queue, provider);
                }
            }

        }
    }

    /**
     * Sleep for the given amount of milliseconds.
     *
     * @param miliseconds Sleep time in milliseconds
     */
    private void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (Exception exception) {
            throw new IllegalStateException("An error occurred while working with the engine: " + exception.getMessage(), exception);
        }
    }
}
