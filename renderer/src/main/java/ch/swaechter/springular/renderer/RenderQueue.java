package ch.swaechter.springular.renderer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The class RenderQueue provides a util collection to store the render requests and to simplify the common render
 * engine tasks.
 *
 * @author Simon Wächter
 */
public class RenderQueue {

    /**
     * List with all touched and rendering requests.
     */
    private final List<RenderRequest> requests;

    /**
     * Create a new render queue.
     */
    public RenderQueue() {
        this.requests = new LinkedList<>();
    }

    /**
     * Check if the queue is empty (Has no untouched or rendering requests.
     *
     * @return Status of the check
     */
    public boolean isEmpty() {
        return requests.size() == 0;
    }

    /**
     * Create a new render request.
     *
     * @param uri URI of the requests
     * @return New render request
     */
    public RenderRequest createRenderRequest(String uri) {
        RenderRequest request = new RenderRequest(uri);
        requests.add(request);
        return request;
    }

    /**
     * Remove a rendered render request.
     *
     * @param request Render request to remove
     */
    public void removeRenderRequest(RenderRequest request) {
        requests.remove(request);
    }

    /**
     * Get a specific render request.
     *
     * @param uuid Unique UUID of the render request
     * @return Optional render request
     */
    public Optional<RenderRequest> getRenderRequest(String uuid) {
        return requests.stream().filter(item -> uuid.equals(item.getUuid())).findFirst();
    }

    /**
     * Get the next untouched render request.
     *
     * @return Optional next untouched render request
     */
    public Optional<RenderRequest> getNextUntouchedRenderRequest() {
        return requests.stream().filter(item -> item.isUntouched()).findFirst();
    }
}
