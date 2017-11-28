require('zone.js/dist/zone-node');

import {renderModuleFactory} from "@angular/platform-server";

export type RenderCallback = (uuid: string, html: string, error: any) => void;

export class RenderAdapter {

    private appservermodulengfactory: any;

    private callback: RenderCallback;

    private html: string;

    constructor(appservermodulengfactory: any, callback: RenderCallback) {
        this.appservermodulengfactory = appservermodulengfactory;
        this.callback = callback;
        this.html = "<app-root></app-root>";
    }

    setHtml(html: string) {
        this.html = html;
    }

    renderPage(uuid: string, uri: string) {
        renderModuleFactory(this.appservermodulengfactory, {document: this.html, url: uri}).then(html => {
            this.callback(uuid, html, null);
        });
    }
}
