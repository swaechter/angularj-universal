import {RenderAdapter} from "./renderadapter";

const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require("./../dist/main.bundle");

new RenderAdapter(AppServerModuleNgFactory, LAZY_MODULE_MAP, "<app-root></app-root>");
