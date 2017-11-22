package ch.swaechter.angularjuniversal.renderer.utils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * This class provides several useful util functions for reading and caching the assets.
 *
 * @author Simon Wächter
 */
public class RenderUtils {

    /**
     * Get the content of an input stream as string.
     *
     * @param inputstream Given input stream with the content
     * @param charset     Charset of the input stream
     * @return Input stream content as string
     * @throws IOException Exception in case of an IO problem
     */
    public static String getStringFromInputStream(InputStream inputstream, Charset charset) throws IOException {
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return outputstream.toString(charset.name());

    }

    /**
     * Create a temporary file from an input string.
     *
     * @param suffix      Suffix of the temporary file
     * @param prefix      Prefix of the temporary file
     * @param inputstream Given input stream with the content
     * @return Newly created temporary file
     * @throws IOException Exception in case of an IO problem
     */
    public static File createTemporaryFileFromInputStream(String suffix, String prefix, InputStream inputstream) throws IOException {
        File file = File.createTempFile(suffix, prefix);
        file.deleteOnExit();

        OutputStream outputstream = new FileOutputStream(file);
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return file;
    }
}
