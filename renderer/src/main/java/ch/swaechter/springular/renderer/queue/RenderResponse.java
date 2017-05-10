package ch.swaechter.springular.renderer.queue;

public class RenderResponse {

    private final String uuid;

    private final String content;

    public RenderResponse(String uuid, String content) {
        this.uuid = uuid;
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public String getContent() {
        return content;
    }
}
