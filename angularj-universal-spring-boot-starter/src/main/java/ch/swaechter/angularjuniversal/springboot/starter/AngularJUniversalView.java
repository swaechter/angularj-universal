package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import ch.swaechter.angularjuniversal.renderer.configuration.RenderConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class AngularJUniversalView extends AbstractTemplateView {

    /**
     * Renderer for rendering page requests.
     */
    @NotNull
    private final Renderer renderer;

    /**
     * Render configuration that will be used to check the routes.
     */
    @NotNull
    private final RenderConfiguration renderConfiguration;

    /**
     * Constructor with the new renderer and the properties for rendering page requests.
     *
     * @param renderer            Renderer
     * @param renderConfiguration Render configuration
     */
    public AngularJUniversalView(@NotNull Renderer renderer, @NotNull RenderConfiguration renderConfiguration) {
        this.renderer = renderer;
        this.renderConfiguration = renderConfiguration;
    }

    /**
     * Disable any URL requirement.
     *
     * @return False because we don't rely on resources
     */
    @Override
    protected boolean isUrlRequired() {
        return false;
    }

    /**
     * Render the page request with the given renderer.
     *
     * @param map      Map with all values
     * @param request  HTTP request
     * @param response HTTP response
     * @throws Exception Exception in case of a problem
     */
    @Override
    protected void renderMergedTemplateModel(@NotNull Map<String, Object> map, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        response.setCharacterEncoding(renderConfiguration.getCharset().name());
        response.setContentType("text/html");
        @NotNull
        PrintWriter writer = response.getWriter();
        writer.println(renderer.addRenderRequest(request.getRequestURI()).get());
        writer.flush();
        writer.close();
    }
}
