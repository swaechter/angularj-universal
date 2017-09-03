import {RenderAdapter, RenderCallback} from "./renderadapter";

const AppServerModuleNgFactory = require('./../dist-server/main.bundle').AppServerModuleNgFactory;

export declare function registerRenderAdapter(renderadapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

const rendercallback: RenderCallback = (uuid: string, html: string, error: any) => {
    receiveRenderedPage(uuid, html, error);
};

const renderadapter = new RenderAdapter(AppServerModuleNgFactory, rendercallback);
registerRenderAdapter(renderadapter);
