package ch.swaechter.springular.renderer.assets;

import java.io.*;
import java.nio.charset.Charset;

/**
 * The class ResourceProvider represents an asset provider that is using the resource system of Java. If the streams of
 * the resources are valid, the asset provider will load them during creation time and provide a cached version. The
 * provider itself does not provide any reload functionality. As a reason of that, the provider is used in production.
 *
 * @author Simon Wächter
 */
public class ResourceProvider implements RenderAssetProvider {

    /**
     * Content that is used to represents the index template.
     */
    private final String indexcontent;

    /**
     * File that is used to get the server bundle.
     */
    private final File serverbundle;

    /**
     * Create a new resource provider that provides cached assets.
     *
     * @param indexinputstream        Input stream that is used to read the index template from
     * @param serverbundleinputstream Input stream that is used to read the server bundle from
     * @param charset                 Charset that will be used to read and write streams and files
     * @throws IOException
     */
    public ResourceProvider(InputStream indexinputstream, InputStream serverbundleinputstream, Charset charset) throws IOException {
        this.indexcontent = getIndexContent(indexinputstream, charset);
        this.serverbundle = getServerBundle(serverbundleinputstream);
    }

    /**
     * Get the cached content of the index template.
     *
     * @return Content of the index template.
     * @throws IOException Exception in case of an IO problem
     */
    @Override
    public String getIndexContent() throws IOException {
        return indexcontent;
    }

    /**
     * Get the cached file of the server bundle.
     *
     * @return File of the server bundle
     * @throws IOException Exception in case of an IO problem
     */
    @Override
    public File getServerBundle() throws IOException {
        return serverbundle;
    }

    /**
     * Check if the provider does support live reload.
     *
     * @return Status if the provider supports live reload
     */
    @Override
    public boolean isLiveReloadSupported() {
        return false;
    }

    /**
     * Check if the provider detected an asset chance and wishes a live reload.
     *
     * @return Status of the provider wishes a live reload
     */
    @Override
    public boolean isLiveReloadRequired() {
        return false;
    }

    /**
     * Get the content of the index input stream. Because the input stream can only read once, we have to cache the
     * result.
     *
     * @param inputstream Input stream that provides the index template
     * @param charset     Charset that will be used to read the index template
     * @return Index template as string
     * @throws IOException Exception in case of an IO problem
     */
    private String getIndexContent(InputStream inputstream, Charset charset) throws IOException {
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return outputstream.toString(charset.name());
    }

    /**
     * Get the content of the server bundle input stream. Because we need the server bundle as a file, we have to create
     * a copy on the local file system. Because the input stream can only read once, we have to cache the result.
     *
     * @param inputstream Input stream that provides the server bundle
     * @return Temporary file that contains the server bundle
     * @throws IOException Exception in case of an IO problem
     */
    private File getServerBundle(InputStream inputstream) throws IOException {
        File file = File.createTempFile("serverbundle", ".js");
        OutputStream outputstream = new FileOutputStream(file);
        int result = inputstream.read();
        while (result != -1) {
            outputstream.write((byte) result);
            result = inputstream.read();
        }
        return file;
    }
}
