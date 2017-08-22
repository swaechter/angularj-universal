package ch.swaechter.angularjuniversal.example.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The class RenderController provides the entry points to the routes.
 *
 * @author Simon Wächter
 */
@Controller
@RequestMapping("/")
public class ContentController {

    /**
     * Provide the home page.
     *
     * @return Model and view to the home page
     */
    @GetMapping({"/", "/home"})
    public ModelAndView showHome() {
        return new ModelAndView("/home");
    }

    /**
     * Provide the about page.
     *
     * @return Model and view to the about page
     */
    @GetMapping({"/about"})
    public ModelAndView showAbout() {
        return new ModelAndView("/about");
    }
}
