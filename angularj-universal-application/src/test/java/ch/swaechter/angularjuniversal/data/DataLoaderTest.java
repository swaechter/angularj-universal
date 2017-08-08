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
        DataLoader dataloader = new DataLoader();
        Assert.assertNotNull(dataloader.getIndexAsInputStream());
        Assert.assertNotNull(dataloader.getServerBundleAsInputStream());
    }
}
