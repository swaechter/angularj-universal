package ch.swaechter.angularjuniversal.data;

import java.io.InputStream;

/**
 * This class is responsible for providing the index template and server bundle as input stream.
 *
 * @author Simon Wächter
 */
public class DataLoader {

    /**
     * Get the index template as input stream.
     *
     * @return Input stream of the index template
     */
    public InputStream getIndexAsInputStream() {
        return this.getClass().getResourceAsStream("/public/index.html");
    }

    /**
     * Get the server bundle as input stream.
     *
     * @return Input stream of the server bundle
     */
    public InputStream getServerBundleAsInputStream() {
        return this.getClass().getResourceAsStream("/server.bundle.js");
    }
}
