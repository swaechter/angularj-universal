package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class AngularJUniversalView extends AbstractTemplateView {

    /**
     * Renderer for rendering page requests.
     */
    private final Renderer renderer;

    /**
     * Constructor with the new renderer for rendering page requests.
     *
     * @param renderer Renderer
     */
    public AngularJUniversalView(Renderer renderer) {
        this.renderer = renderer;
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
    protected void renderMergedTemplateModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        writer.println(renderer.renderRequest(getBeanName()).get());
        writer.flush();
        writer.close();
    }
}
