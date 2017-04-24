package ch.swaechter.springular.renderer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The class RenderQueue provides a util collection to simplify the common render engine tasks.
 *
 * @author Simon Wächter
 */
public class RenderQueue {

    /**
     * List with all touched and rendering requests.
     */
    private final List<RenderEntity> entities;

    /**
     * Create a new render queue.
     */
    public RenderQueue() {
        this.entities = new LinkedList<>();
    }

    /**
     * Check if the queue is empty (Has no untouched or rendering requests.
     *
     * @return Status of the check
     */
    public boolean isEmpty() {
        return entities.size() == 0;
    }

    /**
     * Create a new render entity.
     *
     * @param uri URI of the requests
     * @return New render entity
     */
    public RenderEntity createRenderEntity(String uri) {
        RenderEntity entity = new RenderEntity(uri);
        entities.add(entity);
        return entity;
    }

    /**
     * Remove a rendered render request.
     *
     * @param entity Render request to remove
     */
    public void removeRenderEntity(RenderEntity entity) {
        entities.remove(entity);
    }

    /**
     * Get a specific render request.
     *
     * @param uuid Unique UUID of the render request
     * @return Optional render request
     */
    public Optional<RenderEntity> getRenderEntity(String uuid) {
        return entities.stream().filter(item -> uuid.equals(item.getUuid())).findFirst();
    }

    /**
     * Get the next untouched render request.
     *
     * @return Optional next untouched render request
     */
    public Optional<RenderEntity> getNextUntouchedRenderEntity() {
        return entities.stream().filter(item -> item.isUntouched()).findFirst();
    }
}
