package ch.swaechter.angularjuniversal.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class RenderController provides the entry points to the routes. In the future we have to find a more generic
 * approach.
 *
 * @author Simon Wächter
 */
@RestController
@RequestMapping("/")
public class RenderController {

    /**
     * Render service that is used to render requests.
     */
    private final RendererService renderservice;

    /**
     * Create a new render controller that uses the render service to render requests.
     *
     * @param renderservice Render service that is used to render requests.
     */
    @Autowired
    public RenderController(RendererService renderservice) {
        this.renderservice = renderservice;
    }

    /**
     * Provide the overview page.
     *
     * @return Rendered overview page
     * @throws Exception Exception in case the render engine is unable to render the request
     */
    @GetMapping("/")
    public String showIndex() throws Exception {
        return renderservice.renderPage("/");
    }

    /**
     * Provide the overview page.
     *
     * @return Rendered overview page
     * @throws Exception Exception in case the render engine is unable to render the request
     */
    @GetMapping("/overview")
    public String showOverview() throws Exception {
        return renderservice.renderPage("/overview");
    }

    /**
     * Provide the about page.
     *
     * @return Rendered about page
     * @throws Exception Exception in case the render engine is unable to render the request
     */
    @GetMapping("/about")
    public String showAbout() throws Exception {
        return renderservice.renderPage("/about");
    }
}
