require('zone.js/dist/zone-node');

import {provideModuleMap} from '@nguniversal/module-map-ngfactory-loader';

import {renderModuleFactory} from '@angular/platform-server';

const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('./dist/angular-server/main');

export declare function registerRenderAdapter(renderadapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

export class RenderAdapter {

    constructor(private appservermodulengfactory: any, private lazymodulemap: any, private html: string) {
        registerRenderAdapter(this);
    }

    setHtml(html: string) {
        this.html = html;
    }

    renderPage(uuid: string, uri: string) {
        renderModuleFactory(this.appservermodulengfactory, {
            document: this.html,
            url: uri,
            extraProviders: [
                provideModuleMap(this.lazymodulemap)
            ]
        }).then(html => {
            receiveRenderedPage(uuid, html, null);
        });
    }
}

new RenderAdapter(AppServerModuleNgFactory, LAZY_MODULE_MAP, '<app-root></app-root>');
