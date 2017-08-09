package ch.swaechter.angularjuniversal.v8renderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class V8Utils {

    public static String getContentFromInputStream(InputStream inputstream, Charset charset) throws IOException {
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return outputstream.toString(charset.name());
    }
}
