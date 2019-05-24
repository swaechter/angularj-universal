package ch.swaechter.angularjuniversal.renderer.utils;

import org.jetbrains.annotations.NotNull;

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
     * @param inputStream Given input stream with the content
     * @param charset     Charset of the input stream
     * @return Input stream content as string
     * @throws IOException Exception in case of an IO problem
     */
    public static String getStringFromInputStream(@NotNull InputStream inputStream, @NotNull Charset charset) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int result = inputStream.read();
        while (result != -1) {
            outputStream.write((byte) result);
            result = inputStream.read();
        }
        return outputStream.toString(charset.name());

    }

    /**
     * Create a temporary file from an input string.
     *
     * @param suffix      Suffix of the temporary file
     * @param prefix      Prefix of the temporary file
     * @param inputStream Given input stream with the content
     * @return Newly created temporary file
     * @throws IOException Exception in case of an IO problem
     */
    public static File createTemporaryFileFromInputStream(@NotNull String suffix, @NotNull String prefix, @NotNull InputStream inputStream) throws IOException {
        File file = File.createTempFile(suffix, prefix);
        file.deleteOnExit();

        OutputStream outputStream = new FileOutputStream(file);
        int result = inputStream.read();
        while (result != -1) {
            outputStream.write((byte) result);
            result = inputStream.read();
        }
        return file;
    }
}
