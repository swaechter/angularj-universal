package ch.swaechter.springular.renderer;

import java.io.*;

/**
 * This class is responsible for providing render util functions like reading a resource or creating a temporary file.
 *
 * @author Simon Wächter
 */
public class RenderUtils {

    /**
     * Read a file and return the file content.
     *
     * @param object Object that is used to access the resources
     * @param resourcepath Resource path to read
     * @return File content
     * @throws IOException Exception in case of an IO problem
     */
    public static String readFile(Object object, String resourcepath) throws IOException {
        BufferedInputStream inputstream = new BufferedInputStream(object.getClass().getResourceAsStream(resourcepath));
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return outputstream.toString("UTF-8");
    }

    /**
     * Create a temporary file and write the content to the file.
     *
     * @param content Content of the file
     * @return Temporary file
     * @throws IOException Exception in case of an IO problem
     */
    public static File createTemporaryFile(String content) throws IOException {
        File file = File.createTempFile("render", ".js");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
        return file;
    }
}
