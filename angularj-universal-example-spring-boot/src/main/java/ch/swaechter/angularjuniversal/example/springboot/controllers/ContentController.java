package ch.swaechter.angularjuniversal.example.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is responsible for serving all Angular endpoints.
 *
 * @author Simon Wächter
 */
@Controller
@RequestMapping("/")
public class ContentController {

    /**
     * Serve the login page.
     *
     * @return Model and view of the login page
     */
    @GetMapping({"/", "/login"})
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }

    /**
     * Serve the logout page.
     *
     * @return Model and view of the logout page
     */
    @GetMapping({"/logout"})
    public ModelAndView showLogout() {
        return new ModelAndView("/logout");
    }

    /**
     * Serve the home page.
     *
     * @return Model and view of the home page
     */
    @GetMapping({"/page", "/page/home"})
    public ModelAndView showPageHome() {
        return new ModelAndView("/page/home");
    }

    /**
     * Serve the about page.
     *
     * @return Model and view of the about page
     */
    @GetMapping({"/page/about"})
    public ModelAndView showPageAbout() {
        return new ModelAndView("/page/about");
    }

}
