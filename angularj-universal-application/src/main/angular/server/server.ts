require('zone.js/dist/zone-node');
require('reflect-metadata');

const AppServerModuleNgFactory = require('./../dist-server/main.bundle').AppServerModuleNgFactory;

import {renderModuleFactory} from "@angular/platform-server";

export declare function registerRenderElements(rendermodulefactory, appservermodulengfactory): void;

registerRenderElements(renderModuleFactory, AppServerModuleNgFactory);
