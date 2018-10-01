package ch.swaechter.angularjuniversal.data;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class is responsible for testing the data loader that provides the Angular files.
 *
 * @author Simon Wächter
 */
public class DataLoaderTest {

    /**
     * Test all input streams.
     */
    @Test
    public void testInputStreams() {
        DataLoader dataLoader = new DataLoader();
        Assert.assertNotNull(dataLoader.getIndexAsInputStream());
        Assert.assertNotNull(dataLoader.getServerBundleAsInputStream());
    }
}
