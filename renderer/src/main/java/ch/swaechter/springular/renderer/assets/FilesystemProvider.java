package ch.swaechter.springular.renderer.assets;

import java.io.*;
import java.nio.charset.Charset;

/**
 * The class FilesystemProvider represents an asset provider that is using the assets from a file system. If the files
 * change, the asset provider will reload them and request the render engine to reload itself with the new assets. As a
 * reason of that, this provider enables live reload during development.
 *
 * @author Simon Wächter
 */
public class FilesystemProvider extends AssetProvider {

    /**
     * File that is used to get the content of the index template.
     */
    private final File indexfile;

    /**
     * File that is used to get the server bundle.
     */
    private final File serverbundlefile;

    /**
     * Charset that is used to read the files.
     */
    private final Charset charset;

    /**
     * Create a new file system provider and listen to file changes.
     *
     * @param indexfile        File that will be used to get the content of the index template
     * @param serverbundlefile File that will be used to get the server bundle
     * @param charset          Charset that will be used to read the files
     */
    public FilesystemProvider(File indexfile, File serverbundlefile, Charset charset) {
        this.indexfile = indexfile;
        this.serverbundlefile = serverbundlefile;
        this.charset = charset;
    }

    /**
     * Get the content of the index template.
     *
     * @return Content of the index template.
     * @throws IOException Exception in case of an IO problem
     */
    @Override
    public String getIndexContent() throws IOException {
        InputStream inputstream = new FileInputStream(indexfile);
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return outputstream.toString(charset.name());
    }

    /**
     * Get the file of the server bundle.
     *
     * @return File of the server bundle
     * @throws IOException Exception in case of an IO problem
     */
    @Override
    public File getServerBundle() throws IOException {
        return serverbundlefile;
    }
}
