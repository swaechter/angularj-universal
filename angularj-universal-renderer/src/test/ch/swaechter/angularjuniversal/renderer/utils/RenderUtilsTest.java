package ch.swaechter.angularjuniversal.renderer.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is responsible for testing the render utils.
 *
 * @author Simon Wächter
 */
public class RenderUtilsTest {

    /**
     * Test reading a string from an input stream.
     *
     * @throws IOException Exception in case of a problem
     */
    @Test
    public void testStringFromInputStream() throws IOException {
        String content = "Example";
        InputStream inputstream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        String result = RenderUtils.getStringFromInputStream(inputstream, StandardCharsets.UTF_8);
        Assert.assertTrue(result.equals(content));
    }

    /**
     * Test creating a temporary file from an input stream.
     *
     * @throws IOException Exception in case of a problem
     */
    @Test
    public void testTemporaryFileFromInputStream() throws IOException {
        String content = "Example 1\nExample2";
        InputStream inputstream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        File result = RenderUtils.createTemporaryFileFromInputStream("serverbundle", ".js", inputstream);
        Assert.assertTrue(result.exists());

        byte[] data = Files.readAllBytes(Paths.get(result.getAbsolutePath()));
        Assert.assertTrue(content.equals(new String(data, StandardCharsets.UTF_8)));
    }
}
