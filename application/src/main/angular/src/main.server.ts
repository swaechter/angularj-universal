import * as fs from 'fs';
import 'zone.js/dist/zone-node';
import {renderModuleFactory} from '@angular/platform-server'
import {AppServerModuleNgFactory} from './ngfactory/src/app/app.server.module.ngfactory'

var text = fs.readFileSync("C:/Users/swaechter/Owncloud/Workspace_Java/spring-boot-angular-renderer/application/src/main/angular/src/index.html", {encoding: 'utf8'});

function doWork(text, url) {
    console.log("Start rendering");
    renderModuleFactory(AppServerModuleNgFactory, {document: text, url: url}).then(string => {
        console.log("Generated HTML " + string);
    });
}

doWork(text, "/");
