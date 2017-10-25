package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
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

        AngularJUniversalProperties properties = Mockito.mock(AngularJUniversalProperties.class);
        Mockito.when(properties.getRoutes()).thenReturn(Arrays.asList("/", "/home"));

        AngularJUniversalViewResolver resolver = new AngularJUniversalViewResolver(renderer, properties);

        Assert.assertTrue(resolver.requiredViewClass().getName().equals(AngularJUniversalView.class.getName()));

        Locale locale = Locale.ENGLISH;
        Assert.assertTrue(resolver.canHandle("/", locale));
        Assert.assertTrue(resolver.canHandle("/home", locale));
        Assert.assertFalse(resolver.canHandle("/login", locale));

        Assert.assertTrue(resolver.buildView(Mockito.anyString()).getClass().getName().equals(AngularJUniversalView.class.getName()));
    }
}
