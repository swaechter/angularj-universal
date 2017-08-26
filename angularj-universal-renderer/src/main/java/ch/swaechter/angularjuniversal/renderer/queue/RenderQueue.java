package ch.swaechter.angularjuniversal.renderer.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * The class RenderQueue contains all render requests including queued render requests and requests that are being
 * processed. There are methods to create a new render request, get the next unresolved one and to resolve a processed
 * render request.
 *
 * @author Simon Wächter
 */
public class RenderQueue {

    /**
     * Queue with all unresolved render requests.
     */
    private final BlockingQueue<RenderRequest> renderqueue;

    /**
     * Queue with all render requests.
     */
    private final List<RenderRequest> renderlist;

    /**
     * Create a new render queue that will handle all requests and futures.
     */
    public RenderQueue() {
        this.renderqueue = new LinkedBlockingDeque<>();
        this.renderlist = new ArrayList<>();
    }

    /**
     * Check if the queue is empty and all render requests have been rendered.
     *
     * @return Status if the queue is empty
     */
    public boolean isQueueEmpty() {
        return renderqueue.size() == 0 && renderlist.size() == 0;
    }

    /**
     * Create a new render request based on the given URI.
     *
     * @param uri URI for the render request
     * @return Render request
     */
    public RenderRequest createRenderFuture(String uri) {
        RenderRequest renderrequest = new RenderRequest(uri);
        renderqueue.add(renderrequest);
        renderlist.add(renderrequest);
        return renderrequest;
    }

    /**
     * Get the next blocking render request. After receiving the render request, the request will be removed from the
     * pending request queue.
     *
     * @return Blocking render request
     * @throws InterruptedException Exception in case of an interrupt
     */
    public RenderRequest getNextRenderRequest() throws InterruptedException {
        return renderqueue.take();
    }

    /**
     * Resolve a render request with completing the future of the render request.
     *
     * @param uuid    UUID of the render requests
     * @param content Received rendered content
     */
    public void resolveRenderReuest(String uuid, String content) {
        Optional<RenderRequest> renderrequestitem = renderlist.stream().filter(i -> i.getUuid().equals(uuid)).findFirst();
        if (renderrequestitem.isPresent()) {
            RenderRequest renderrequest = renderrequestitem.get();
            renderlist.remove(renderrequest);
            renderrequest.getFuture().complete(content);
        }
    }
}
