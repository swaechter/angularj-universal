require('zone.js/dist/zone-node');
require('reflect-metadata');

const AppServerModuleNgFactory = require('./../dist-server/main.bundle').AppServerModuleNgFactory;

import {renderModuleFactory} from "@angular/platform-server";

export declare function registerRenderAdapter(renderadapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

export class RenderAdapter {

    private appservermodulengfactory: any;

    private html: string;

    constructor(appservermodulengfactory: any) {
        this.appservermodulengfactory = appservermodulengfactory;
        this.html = "<app-root></app-root>";
    }

    setHtml(html: string) {
        this.html = html;
    }

    renderPage(uuid: string, uri: string) {
        renderModuleFactory(this.appservermodulengfactory, {document: this.html, url: uri}).then(html => {
            receiveRenderedPage(uuid, html, null);
        });
    }
}

registerRenderAdapter(new RenderAdapter(AppServerModuleNgFactory));
