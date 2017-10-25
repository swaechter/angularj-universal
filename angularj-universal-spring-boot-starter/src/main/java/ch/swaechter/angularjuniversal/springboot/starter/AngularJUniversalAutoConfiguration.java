package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class serves as an entry point for the AngularJ Universal Spring Boot starter.
 *
 * @author Simon Wächter
 */
@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(AngularJUniversalProperties.class)
public class AngularJUniversalAutoConfiguration {

    /**
     * Properties loaded by Spring Boot and used by this starter.
     */
    private final AngularJUniversalProperties properties;

    /**
     * Constructor with the starter properties.
     *
     * @param properties Starter properties
     */
    @Autowired
    public AngularJUniversalAutoConfiguration(AngularJUniversalProperties properties) {
        this.properties = properties;
    }

    /**
     * Get the render engine factory. At the moment the V8 implementation is used.
     *
     * @return Render engine factory
     */
    @Bean
    @ConditionalOnMissingBean
    public RenderEngineFactory getRenderEngineFactory() {
        return new V8RenderEngineFactory();
    }

    /**
     * Get the render configuration.
     *
     * @param resourceloader Resource loader for accessing the assets
     * @return Render configuration
     */
    @Bean
    @ConditionalOnMissingBean
    public RenderConfiguration getRenderConfiguration(ResourceLoader resourceloader) {
        // Get the content of the index template
        InputStream indexinputstream = AngularJUniversalUtils.getInputStreamFromResource(resourceloader, properties.getIndexResourcePath());
        if (indexinputstream == null) {
            throw new RuntimeException("AngularJ Universal starter is unable get an input stream for " + properties.getIndexResourcePath());
        }

        // Get the input stream for the server bundle
        InputStream serverbundleinputstream = AngularJUniversalUtils.getInputStreamFromResource(resourceloader, properties.getServerBundleResourcePath());
        if (serverbundleinputstream == null) {
            throw new RuntimeException("AngularJ Universal starter is unable get an input stream for " + properties.getServerBundleResourcePath());
        }

        // Check the engine number
        int engines = properties.getEngines();
        if (engines < 0) {
            throw new RuntimeException("AngularJ Universal requires at least one engine not " + engines);
        }

        // Check the charset
        if (properties.getCharset() == null) {
            throw new RuntimeException("AngularJ Universal starter is unable to parse the charset");
        }

        // Read the content from the index template
        String templatecontent;
        try {
            templatecontent = RenderUtils.getStringFromInputStream(indexinputstream, properties.getCharset());
        } catch (IOException exception) {
            throw new RuntimeException("AngularJ Universal is unable to read the template content for " + properties.getIndexResourcePath());
        }

        // Create a temporary server bundle from the input stream
        File serverbundlefile;
        try {
            serverbundlefile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", ".js", serverbundleinputstream);
        } catch (IOException exception) {
            throw new RuntimeException("AngularJ Universal is unable to cache the server bundle file for " + properties.getServerBundleResourcePath());
        }

        // Create the configuration
        return new RenderConfiguration(templatecontent, serverbundlefile, engines, false);
    }

    /**
     * Get the renderer.
     *
     * @param renderenginefactory Injected render engine factory
     * @param renderconfiguration Injected render configuration
     * @return Started renderer
     */
    @Bean
    @ConditionalOnMissingBean
    public Renderer getRenderer(RenderEngineFactory renderenginefactory, RenderConfiguration renderconfiguration) {
        // Create the renderer
        Renderer renderer = new Renderer(renderenginefactory, renderconfiguration);
        renderer.startRenderer();
        return renderer;
    }

    /**
     * Get the view resolver.
     *
     * @param renderer Renderer used to render page requests
     * @return View resolver
     */
    @Bean
    public ViewResolver getViewResolver(Renderer renderer) {
        // Check the routes
        if (properties.getRoutes().isEmpty()) {
            throw new RuntimeException("AngularJ Universal starter is unable to parse and find any routes for " + properties.getRoutes());
        }

        // Check the charset
        if (properties.getCharset() == null) {
            throw new RuntimeException("AngularJ Universal starter is unable to parse the charset");
        }

        // Create the view resolver
        AngularJUniversalViewResolver viewresolver = new AngularJUniversalViewResolver(renderer, properties);
        viewresolver.setOrder(0);
        return viewresolver;
    }
}
