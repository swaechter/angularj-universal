package ch.swaechter.angularjuniversal.tcprenderer;

import ch.swaechter.angularjuniversal.renderer.request.RenderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class provides a test to guarantee the functionality of the TCP renderer.
 *
 * @author Simon Wächter
 */
public class TcpRenderEngineTest {

    /**
     * Test the renderer.
     */
    @Test
    public void testRenderEngine() throws Exception {
        /*DataLoader dataLoader = new DataLoader();
        InputStream indexInputStream = dataLoader.getIndexAsInputStream();
        InputStream serverBundleInputStream = dataLoader.getServerBundleAsInputStream();
        Assert.assertNotNull(indexInputStream);
        Assert.assertNotNull(serverBundleInputStream);

        String templateContent = RenderUtils.getStringFromInputStream(indexInputStream, StandardCharsets.UTF_8);
        File serverBundleFile = RenderUtils.createTemporaryFileFromInputStream("serverbundle", ".js", serverBundleInputStream);
        Assert.assertTrue(templateContent.contains("app-root"));
        Assert.assertTrue(serverBundleFile.exists());

        RenderConfiguration renderConfiguration = new RenderConfiguration.RenderConfigurationBuilder(templateContent, serverBundleFile).build();
        RenderEngineFactory renderEngineFactory = new TcpRenderEngineFactory();
        Renderer renderer = new Renderer(renderConfiguration, renderEngineFactory);

        Assert.assertFalse(renderer.isRendererRunning());
        renderer.startRenderer();
        Assert.assertTrue(renderer.isRendererRunning());
        renderer.stopRenderer();
        Assert.assertFalse(renderer.isRendererRunning());
        renderer.startRenderer();
        Assert.assertTrue(renderer.isRendererRunning());

        Future<String> future1 = renderer.addRenderRequest("/");
        Assert.assertNotNull(future1);
        Assert.assertTrue(future1.get().contains("Home"));

        Future<String> future2 = renderer.addRenderRequest("/home");
        Assert.assertNotNull(future2);
        Assert.assertTrue(future2.get().contains("Home"));

        Future<String> future3 = renderer.addRenderRequest("/keywords");
        Assert.assertNotNull(future3);
        Assert.assertTrue(future3.get().contains("Keywords"));

        Future<String> future4 = renderer.addRenderRequest("/keywords/1");
        Assert.assertNotNull(future4);
        Assert.assertTrue(future4.get().contains("Dummy keyword"));

        Future<String> future5 = renderer.addRenderRequest("/about");
        Assert.assertNotNull(future5);
        Assert.assertTrue(future5.get().contains("About"));

        Future<String> future6 = renderer.addRenderRequest("/invalid");
        Assert.assertNotNull(future6);
        Assert.assertTrue(future6.get().contains("Home"));

        renderer.stopRenderer();*/
    }

    @Test
    public void testConnection() throws Exception {
        RenderRequest renderRequest = new RenderRequest("/");
        sendRequestAndReceiveBody(renderRequest);
        System.out.println("The response was: " + renderRequest.getFuture().get());
    }

    private void sendRequestAndReceiveBody(RenderRequest renderRequest) throws Exception {
        // Create the object mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Create the socket and initialize the writer reader
        Socket socket = new Socket("localhost", 9090);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Write the request
        TcpRequest tcpRequest = new TcpRequest(renderRequest.getUuid(), renderRequest.getUri(), "<app-root></app-root>");
        writer.println(objectMapper.writeValueAsString(tcpRequest));
        writer.flush();

        // Read the response
        TcpResponse tcpResponse = objectMapper.readValue(reader, TcpResponse.class);
        renderRequest.getFuture().complete(tcpResponse.getHtml());
    }
}
