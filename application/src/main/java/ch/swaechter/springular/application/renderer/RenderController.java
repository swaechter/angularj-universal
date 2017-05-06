package ch.swaechter.springular.application.renderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RenderController {

    private final RendererService renderservice;

    @Autowired
    public RenderController(RendererService renderservice) {
        this.renderservice = renderservice;
    }

    @GetMapping("/")
    public String showIndex() throws Exception {
        return renderservice.renderPage("/");
    }

    @GetMapping("/overview")
    public String showOverview() throws Exception {
        return renderservice.renderPage("/overview");
    }

    @GetMapping("/about")
    public String showAbout() throws Exception {
        return renderservice.renderPage("/about");
    }
}
