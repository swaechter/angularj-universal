package ch.swaechter.angularjuniversal.springboot.starter;

import ch.swaechter.angularjuniversal.renderer.Renderer;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class is responsible for resolving our view requests if the routing matches.
 *
 * @author Simon Wächter
 */
public class AngularJUniversalViewResolver extends AbstractTemplateViewResolver {

    /**
     * Renderer that will be passed to the view for rendering the page request.
     */
    private final Renderer renderer;

    /**
     * Constructor with the renderer that will be passed to the view.
     *
     * @param renderer Renderer
     */
    public AngularJUniversalViewResolver(Renderer renderer) {
        setViewClass(requiredViewClass());
        this.renderer = renderer;
    }

    /**
     * Define the required view class.
     *
     * @return AngularJ Universal view class
     */
    @Override
    public Class<?> requiredViewClass() {
        return AngularJUniversalView.class;
    }

    /**
     * Check if the given model name can be handled as valid URI. If yes, we will render the request later on, otherwise
     * we will not.
     *
     * @param modelname Model name that we threat as URI
     * @param locale    Locale of the page request
     * @return Status of the check
     */
    @Override
    public boolean canHandle(String modelname, Locale locale) {
        List<String> endpoints = Arrays.asList("/", "/login", "/logout", "/page", "/page/home", "/page/about"); // TODO: Remove hardcoded values
        if (endpoints.contains(modelname)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Build a valid view based on the given URI.
     *
     * @param uri URI of the page request
     * @return Valid view
     */
    @Override
    public AbstractUrlBasedView buildView(String uri) {
        return new AngularJUniversalView(renderer);
    }
}
