package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.assets.RenderAssetProvider;
import ch.swaechter.angularjuniversal.renderer.assets.ResourceProvider;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngine;
import ch.swaechter.angularjuniversal.v8renderer.V8RenderEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

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
     * Delimiter that separates the routes in the configuration.
     */
    private static final String ROUTE_DELIMITER = ",";

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
     * Get the renderer.
     *
     * @param engine         Render engine used to render page requests
     * @param resourceloader Resource loader used for loading the index and server bundle resources
     * @return Renderer used to render page requests
     * @throws IOException Exception in case of a problem
     */
    @Bean
    @ConditionalOnMissingBean
    public Renderer getRenderer(RenderEngine engine, ResourceLoader resourceloader) throws IOException {
        // Check the routes
        if (properties.getRoutes().split(ROUTE_DELIMITER).length == 0) {
            throw new RuntimeException("AngularJ Universal starter is unable to parse and find any routes for " + properties.getRoutes());
        }

        // Get the input stream for the index
        InputStream indexinputstream = AngularJUniversalUtils.getInputStreamFromResource(resourceloader, properties.getIndexResourcePath());
        if (indexinputstream == null) {
            throw new RuntimeException("AngularJ Universal starter is unable get an input stream for " + properties.getIndexResourcePath());
        }

        // Get the input stream for the server bundle
        InputStream serverbundleinputstream = AngularJUniversalUtils.getInputStreamFromResource(resourceloader, properties.getServerBundleResourcePath());
        if (serverbundleinputstream == null) {
            throw new RuntimeException("AngularJ Universal starter is unable get an input stream for " + properties.getServerBundleResourcePath());
        }

        // Get the encoding
        Charset charset = AngularJUniversalUtils.getCharsetFromName(properties.getCharset());
        if (charset == null) {
            throw new RuntimeException("AngularJ Universal starter is unable to interpret the charset " + properties.getCharset());
        }

        // Create the asset provider
        RenderAssetProvider assetprovider;
        try {
            assetprovider = new ResourceProvider(indexinputstream, serverbundleinputstream, charset);
        } catch (Exception exception) {
            throw new RuntimeException("AngularJ Universal is unable to create the asset provider");
        }

        // Create the renderer
        Renderer renderer = new Renderer(engine, assetprovider);
        renderer.startEngine();
        return renderer;
    }

    /**
     * Get the render engine. At the moment a V8 engine is used by default.
     *
     * @return Render engine
     */
    @Bean
    @ConditionalOnMissingBean
    public RenderEngine getRenderEngine() {
        return new V8RenderEngine();
    }

    /**
     * Get the view resolver.
     *
     * @param renderer Renderer used to render page requests
     * @return View resolver
     */
    @Bean
    public ViewResolver getViewResolver(Renderer renderer) {
        List<String> routes = Arrays.asList(properties.getRoutes().split(ROUTE_DELIMITER));
        AngularJUniversalViewResolver viewresolver = new AngularJUniversalViewResolver(renderer, routes);
        viewresolver.setOrder(0);
        return viewresolver;
    }
}
