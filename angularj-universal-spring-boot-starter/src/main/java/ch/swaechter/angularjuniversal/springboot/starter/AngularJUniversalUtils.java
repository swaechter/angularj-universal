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
     * @param resourceLoader   Resource loader that serves as resource context
     * @param resourceFilePath Path to the resource
     * @return Valid input stream or null
     * @throws IOException Exception in case of an IO problem
     */

    public static InputStream getInputStreamFromResource(ResourceLoader resourceLoader, String resourceFilePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + resourceFilePath);
        return resource.getInputStream();
    }
}
