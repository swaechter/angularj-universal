package ch.swaechter.angularjssr.renderer.assets;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * The class FilesystemProvider represents an asset provider that is using assets from the file system. If the files
 * change, the asset provider can detect this change and tell the renderer that the assets have changed. The renderer
 * then can reload the assets.
 *
 * @author Simon Wächter
 */
public class FilesystemProvider implements RenderAssetProvider {

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
     * Create a new file system provider.
     *
     * @param indexfile        File that will be used to get the content of the index template
     * @param serverbundlefile File that will be used to get the server bundle
     * @param charset          Charset that will be used to read the files
     */
    public FilesystemProvider(File indexfile, File serverbundlefile, Charset charset) throws IOException {
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

    /**
     * Check if the provider does support live reload.
     *
     * @return Status if the provider supports live reload
     */
    @Override
    public boolean isLiveReloadSupported() {
        return true;
    }

    /**
     * Check if the provider detected an asset chance and wishes a live reload.
     *
     * @param date Date of the last reload
     * @return Status of the provider wishes a live reload
     */
    @Override
    public boolean isLiveReloadRequired(Date date) throws IOException {
        return isFileOutdated(indexfile, date) || isFileOutdated(serverbundlefile, date);
    }

    /**
     * Check if the file modified date is after the reload, so whe have to reload the scripts.
     *
     * @param file File that will be checked
     * @param date Date of the engine reload
     * @return Status if the file is outdated
     * @throws IOException Exception in case of an IO problem
     */
    private boolean isFileOutdated(File file, Date date) throws IOException {
        return date.before(new Date(file.lastModified()));
    }
}
