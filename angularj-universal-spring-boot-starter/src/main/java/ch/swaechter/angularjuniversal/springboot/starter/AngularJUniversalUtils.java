package ch.swaechter.angularjuniversal.springboot.starter;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides several util functions.
 *
 * @author Simon Wächter
 */
public class AngularJUniversalUtils {

    /**
     * Get the input stream from a resource.
     *
     * @param resourceloader   Resource loader that serves as resource context
     * @param resourcefilepath Path to the resource
     * @return Valid input stream or null
     * @throws IOException Exception in case of an IO problem
     */

    public static InputStream getInputStreamFromResource(ResourceLoader resourceloader, String resourcefilepath) throws IOException {
        Resource resource = resourceloader.getResource("classpath:" + resourcefilepath);
        return resource.getInputStream();

    }
}
