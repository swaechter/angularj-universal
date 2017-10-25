package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This class is responsible for testing the view.
 *
 * @author Simon Wächter
 */
public class AngularJUniversalViewTest {

    /**
     * Test the view.
     *
     * @throws Exception Exception in case of an unexpected problem.
     */
    @Test
    public void testAngularJUniversalView() throws Exception {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Hallo Welt! Hello world! Здравствуй, мир!");

        Renderer renderer = Mockito.mock(Renderer.class);
        Mockito.when(renderer.addRenderRequest(Mockito.anyString())).thenReturn(future);

        AngularJUniversalProperties properties = Mockito.mock(AngularJUniversalProperties.class);
        Mockito.when(properties.getCharset()).thenReturn(StandardCharsets.UTF_8);

        AngularJUniversalView view = new AngularJUniversalView(renderer, properties);

        Assert.assertFalse(view.isUrlRequired());

        Map<String, Object> map = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        view.renderMergedTemplateModel(map, request, response);
        Assert.assertTrue(response.getCharacterEncoding().equals(properties.getCharset().name()));
        Assert.assertTrue(response.getContentType().equals("text/html"));
        Assert.assertTrue(response.getContentAsString().equals(future.get() + System.lineSeparator()));
    }
}
