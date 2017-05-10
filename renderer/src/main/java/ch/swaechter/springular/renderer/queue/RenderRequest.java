package ch.swaechter.springular.renderer.queue;

import java.util.UUID;

public class RenderRequest {

    private final String uuid;

    private final String uri;

    public RenderRequest(String uri) {
        this.uuid = UUID.randomUUID().toString();
        this.uri = uri;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUri() {
        return uri;
    }
}
