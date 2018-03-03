package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is responsible for registering all routes of the application, so no further mapping in the application is
 * required.
 *
 * @author Simon Wächter
 */
public class AngularJUniversalConfigurer implements WebMvcConfigurer {

    /**
     * Render configuration that will be used to check the routes.
     */
    private final RenderConfiguration renderconfiguration;

    /**
     * Constructor with the render configuration.
     *
     * @param renderconfiguration Render configuration
     */
    public AngularJUniversalConfigurer(RenderConfiguration renderconfiguration) {
        this.renderconfiguration = renderconfiguration;
    }

    /**
     * Add all view controllers than are defined as routing, so no further request mapping in the application is
     * required.
     *
     * @param registry Registry to add new view controllers
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (String url : renderconfiguration.getRoutes()) {
            ViewControllerRegistration viewControllerRegistry = registry.addViewController(url);
            viewControllerRegistry.setViewName(url);
        }
    }
}
