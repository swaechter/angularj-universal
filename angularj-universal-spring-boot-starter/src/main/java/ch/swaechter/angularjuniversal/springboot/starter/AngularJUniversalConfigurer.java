package ch.swaechter.angularjuniversal.springboot.starter;

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
     * Properties that will be used to check the routes.
     */
    private final AngularJUniversalProperties properties;

    /**
     * Constructor with the starter properties.
     *
     * @param properties Starter properties
     */
    public AngularJUniversalConfigurer(AngularJUniversalProperties properties) {
        this.properties = properties;
    }

    /**
     * Add all view controllers than are defined as routing, so no further request mapping in the application is
     * required.
     *
     * @param registry Registry to add new view controllers
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (String url : properties.getRoutes()) {
            ViewControllerRegistration viewControllerRegistry = registry.addViewController(url);
            viewControllerRegistry.setViewName(url);
        }
    }
}
