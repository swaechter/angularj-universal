package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import ch.swaechter.angularjuniversal.renderer.engine.RenderEngineFactory;
import ch.swaechter.angularjuniversal.renderer.utils.RenderUtils;
import ch.swaechter.angularjuniversal.tcprenderer.TcpRenderEngineFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
     * Get the render engine factory. At the moment the V8 implementation is used.
     *
     * @return Render engine factory
     */
    @Bean
    @ConditionalOnMissingBean
    @NotNull
    public RenderEngineFactory getRenderEngineFactory() {
        return new TcpRenderEngineFactory();
    }

    /**
     * Get the render configuration.
     *
     * @param properties     Properties loaded by Spring Boot and used by this starter.
     * @param resourceLoader Resource loader for accessing the assets
     * @return Render configuration
     */
    @Bean
    @ConditionalOnMissingBean
    @NotNull
    public RenderConfiguration getRenderConfiguration(@NotNull AngularJUniversalProperties properties, @NotNull ResourceLoader resourceLoader) {
        // Check the charset
        if (properties.getCharset() == null) {
            throw new RuntimeException("AngularJ Universal starter is unable to parse the charset");
        }

        // Get the Node.js path
        @NotNull
        String nodePath = properties.getNodePath();

        // Get the Node.js port
        @NotNull
        Integer nodePort = properties.getNodePort();

        // Create a temporary server bundle from the input stream
        @Nullable
        File serverBundleFile;
        try {
            @NotNull
            InputStream serverBundleInputStream = AngularJUniversalUtils.getInputStreamFromResource(resourceLoader, properties.getServerBundleResourcePath());
            serverBundleFile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", ".js", serverBundleInputStream);
        } catch (IOException exception) {
            throw new RuntimeException("AngularJ Universal is unable to cache the server bundle file for " + properties.getServerBundleResourcePath());
        }

        // Get the content of the index template
        @Nullable
        String templateContent;
        try {
            @NotNull
            InputStream indexInputStream = AngularJUniversalUtils.getInputStreamFromResource(resourceLoader, properties.getIndexResourcePath());
            templateContent = RenderUtils.getStringFromInputStream(indexInputStream, properties.getCharset());
        } catch (IOException exception) {
            throw new RuntimeException("AngularJ Universal is unable to read the template content for " + properties.getIndexResourcePath());
        }

        // Build the render configuration builder
        RenderConfiguration.RenderConfigurationBuilder builder = new RenderConfiguration.RenderConfigurationBuilder(nodePath, nodePort, serverBundleFile, templateContent);
        builder.charset(properties.getCharset());

        // Check the routes
        @NotNull
        List<String> routes = properties.getRoutes();
        if (properties.getRoutes().isEmpty()) {
            throw new RuntimeException("AngularJ Universal starter is unable to parse and find any routes for " + properties.getRoutes());
        }

        builder.routes(routes);

        // Build the render configuration
        return builder.build();
    }

    /**
     * Get the renderer.
     *
     * @param renderEngineFactory Injected render engine factory
     * @param renderConfiguration Injected render configuration
     * @return Started renderer
     */
    @Bean
    @ConditionalOnMissingBean
    @NotNull
    public Renderer getRenderer(@NotNull RenderEngineFactory renderEngineFactory, @NotNull RenderConfiguration renderConfiguration) {
        // Create the renderer
        @NotNull
        Renderer renderer = new Renderer(renderConfiguration, renderEngineFactory);
        renderer.startRenderer();
        return renderer;
    }

    /**
     * Get the view resolver.
     *
     * @param renderer            Injected renderer
     * @param renderConfiguration Injected render configuration
     * @return View resolver
     */
    @Bean
    @NotNull
    public ViewResolver getViewResolver(@NotNull Renderer renderer, @NotNull RenderConfiguration renderConfiguration) {
        // Create the view resolver
        @NotNull
        AngularJUniversalViewResolver viewResolver = new AngularJUniversalViewResolver(renderer, renderConfiguration);
        viewResolver.setOrder(0);
        return viewResolver;
    }

    /**
     * Get a web MVC configurer that is going to register all routes of the application.
     *
     * @param renderConfiguration Injected render configuration
     * @return Web MVC configurer with the application routes
     */
    @Bean
    @NotNull
    public WebMvcConfigurer getWebMvcConfigurer(@NotNull RenderConfiguration renderConfiguration) {
        return new AngularJUniversalConfigurer(renderConfiguration);
    }
}
