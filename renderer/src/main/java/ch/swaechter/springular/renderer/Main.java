package ch.swaechter.springular.renderer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    Main() {
        try {
            File serverfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/dist/server.js");
            File indexfile = new File("/home/swaechter/Owncloud/Workspace_Node/cli-universal-demo/foo/index.html");
            V8RenderEngine engine = new V8RenderEngine(serverfile, readFile(indexfile));
            engine.startEngine();

            String html1 = engine.renderPage("/");
            System.out.println("Value of /: " + html1);

            String html2 = engine.renderPage("/about");
            System.out.println("Value of /about: " + html2);

            System.out.flush();

            //Thread.sleep(3000);
            //engine.stopEngine();
            System.out.println("End");
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read the index file", exception);
        }
    }

    private String readFile(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded);
    }
}
