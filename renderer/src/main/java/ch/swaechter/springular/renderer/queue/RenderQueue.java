package ch.swaechter.springular.renderer.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The class RenderQueue contains all render requests and manages their state. The render queue is passed to the render
 * engine and accessed by this entity. There are methods to create a new render request, set the render request
 * rendering and resolve it.
 *
 * @author Simon Wächter
 */
public class RenderQueue {

    /**
     * List with all render futures that can be resolved.
     */
    private final List<RenderFuture> futures;

    /**
     * Create a new render queue that will handle all requests and futures.
     */
    public RenderQueue() {
        this.futures = new LinkedList<>();
    }

    /**
     * Check if objects in the queues are pending or if all objects have been rendered.
     *
     * @return Status of the queue is pending or not
     */
    public boolean isQueuePending() {
        return futures.size() == 0;
    }

    /**
     * Create a new render future that contains a render request.
     *
     * @param uri URI of the requests
     * @return New render future that contains the render request
     */
    public RenderFuture createRenderFuture(String uri) {
        RenderFuture future = new RenderFuture(new RenderRequest(uri));
        futures.add(future);
        return future;
    }

    /**
     * Get the next render requests. After receiving the render request, the request will be removed from the pending
     * request queue.
     *
     * @return Optional render request
     */
    public Optional<RenderRequest> getNextRenderRequest() {
        Optional<RenderFuture> futureitem = futures.stream().filter(future -> !future.isRendering()).findFirst();
        if (futureitem.isPresent()) {
            RenderFuture future = futureitem.get();
            RenderRequest request = future.getRequest();
            future.setRendering();
            return Optional.of(request);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Resolve an existing render future and the request based on the response. All objects will be removed from the
     * queue.
     *
     * @param response Render response that contains the
     */
    public void resolveRenderFuture(RenderResponse response) {
        Optional<RenderFuture> futureitem = futures.stream().filter(i -> i.getRequest().getUuid().equals(response.getUuid())).findFirst();
        if (futureitem.isPresent()) {
            RenderFuture future = futureitem.get();
            futures.remove(future);
            future.getCompletableFuture().complete(response.getContent());
        }
    }
}
