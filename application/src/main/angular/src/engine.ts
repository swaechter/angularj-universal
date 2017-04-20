//import "reflect-metadata";
//import "zone.js/dist/zone-node";
import "./polyfills";
import {renderModuleFactory} from "@angular/platform-server";
import {AppServerModuleNgFactory} from "../dist/ngfactory/src/app/app.server.module.ngfactory";

/**
 * Method that is used to register a valid render object. This method has to be provided by the Java JVM.
 *
 * @param renderengine Instance of a render engine that is used by the Java JVM to render a page
 */
declare function registerRenderEngine(renderengine: RenderEngine);

/**
 * This interface provides a callback functionality to exchange data with the Java method.
 *
 * @author Simon Wächter
 */
export interface IRenderCallback {
    (result: string, error: Error): void;
}

/**
 * This class provides a JavaScript based render engine that is capable of rendering an HTML template. To make use of the
 * render functionality, the caller (In this case this class is called by a Java JVM) requires a valid object of the
 * class. As a reason of that the NodeJs server script has to call RenderEngine.registerEngine method. This static
 * method class will pass a valid object via registerRenderEngine to the Java JVM.
 *
 * If the Java JVM has a valid object of RenderEngine, the Java JVM can access the renderPage method with the index
 * template, the URI (for example / or /about) and a callback. Because the method is asynchronous, the callback will
 * return the result directly to the Java JVM.
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
     * Render a page based on the given template an URI. The result will be passed to the callback that has to provided by
     * the Java JVM (like the registerRenderEngine method).
     *
     * @param template String of the template, more or less always from the index.html file
     * @param uri Current URI of the client like / or about. The value is required to get the right Angular component routing.
     * @param callback Callback that will be used to pass the result
     */
    public renderPage(template: string, uri: string, callback: IRenderCallback) {
        try {
            renderModuleFactory(AppServerModuleNgFactory, {document: template, url: uri}).then(html => {
                callback(html, null);
            })
        } catch (error) {
            callback(null, error);
        }
    }
}
