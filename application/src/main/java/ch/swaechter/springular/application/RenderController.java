package ch.swaechter.springular.application;

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

    @GetMapping("/home")
    public String showHome() throws Exception {
        return renderservice.renderPage("/home");
    }

    @GetMapping("/about")
    public String showAbout() throws Exception {
        return renderservice.renderPage("/about");
    }
}
