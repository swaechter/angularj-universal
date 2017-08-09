require("reflect-metadata");
require("zone.js/dist/zone-node");

var renderModuleFactory = require("@angular/platform-server").renderModuleFactory;
var AppServerModuleFactory = require("./dist-server/main.bundle.js").AppServerModuleNgFactory;

registerRenderElements(renderModuleFactory, AppServerModuleFactory);
