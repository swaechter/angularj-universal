import "reflect-metadata";
import "zone.js/dist/zone-node";
import {renderModuleFactory} from "@angular/platform-server";
import {AppServerModuleNgFactory} from "./ngfactory/src/app/app.server.module.ngfactory";

/**
 * Method that is used to register a valid render object. This method has to be provided by the Java JVM.
 *
 * @param renderengine Instance of a render engine that is used by the Java JVM to render a page
 */
declare function registerRenderEngine(renderengine: RenderEngine);

/**
 * Method that is used to receive the rendered page content. This method has to be provided by the Java JVM.
 *
 * @param uuid Unique ID that identifies the request
 * @param content Rendered HTML page
 * @param error Error if something went wrong
 */
declare function receiveRenderedPage(uuid: string, content: string, error: Error);

/**
 * This class provides a JavaScript based render engine that is capable of rendering an HTML template. To make use of the
 * render functionality, the caller (In this case this class is called by a Java JVM) requires a valid object of the
 * class. As a reason of that the NodeJs server script has to call RenderEngine.registerEngine method. This static
 * method class will pass a valid object via registerRenderEngine to the Java JVM.
 *
 * If the Java JVM has a valid object of RenderEngine, the Java JVM can access the renderPage method with the an
 * unique identifier for the request, the index template and the URI (for example / or /about). As soon the page is
 * rednered, the method receiveRenderedpage will be called (This method also has to be provided by the Java JVM).
 *
 * @author Simon Wächter
 */
export class RenderEngine {

    /**
     * Pass an instance of the render engine to the calling Java JVM, so the JVM is able to use the renderPage method
     * based on the passed object. The registerRenderEngine has not to be implemented because the Java JVM is responsible
     * for providing this method.
     */
    public static registerEngine() {
        registerRenderEngine(new RenderEngine());
    }

    /**
     * Render a page based on the given unique ID, the template and URI. The result will be passed to the method that
     * has to provided by the Java JVM (like the registerRenderEngine method).
     *
     * @param uuid Unique ID that identifies the request
     * @param template String of the template, more or less always from the index.html file
     * @param uri Current URI of the client like / or about. The value is required to get the right Angular component routing.
     */
    public renderPage(uuid: string, template: string, uri: string) {
        try {
            renderModuleFactory(AppServerModuleNgFactory, {document: template, url: uri}).then(html => {
                receiveRenderedPage(uuid, html, null);
            });
        } catch (error) {
            receiveRenderedPage(uuid, null, error);
        }
    }
}

// Register the render engine in the Java JVM
RenderEngine.registerEngine();
