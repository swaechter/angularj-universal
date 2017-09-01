import {RenderAdapter, RenderCallback} from "./serveradapter";

const AppServerModuleNgFactory = require('./../dist-server/main.bundle').AppServerModuleNgFactory;

export declare function registerRenderAdapter(renderadapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

const rendercallback: RenderCallback = (uuid: string, html: string, error: any) => {
    receiveRenderedPage(uuid, html, error);
};

registerRenderAdapter(new RenderAdapter(AppServerModuleNgFactory, rendercallback));
