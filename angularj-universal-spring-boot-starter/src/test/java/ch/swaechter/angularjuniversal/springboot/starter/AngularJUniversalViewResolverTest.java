package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Locale;

/**
 * This class is responsible for testing the view resolver.
 *
 * @author Simon Wächter
 */
public class AngularJUniversalViewResolverTest {

    /**
     * Test the view resolver.
     */
    @Test
    public void testAngularJUniversalViewResolver() {
        Renderer renderer = Mockito.mock(Renderer.class);

        RenderConfiguration renderconfiguration = Mockito.mock(RenderConfiguration.class);
        Mockito.when(renderconfiguration.getRoutes()).thenReturn(Arrays.asList("/", "/home"));

        AngularJUniversalViewResolver resolver = new AngularJUniversalViewResolver(renderer, renderconfiguration);

        Assert.assertEquals(AngularJUniversalView.class.getName(), resolver.requiredViewClass().getName());

        Locale locale = Locale.ENGLISH;
        Assert.assertTrue(resolver.canHandle("/", locale));
        Assert.assertTrue(resolver.canHandle("/home", locale));
        Assert.assertFalse(resolver.canHandle("/login", locale));

        Assert.assertEquals(AngularJUniversalView.class.getName(), resolver.buildView("/").getClass().getName());
    }
}
