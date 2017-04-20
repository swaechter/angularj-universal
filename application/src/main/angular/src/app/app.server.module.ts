/// <reference types="node" />

import {NgModule} from "@angular/core";
import {ServerModule} from "@angular/platform-server";
import {AppModule, AppComponent} from "./app.module";

@NgModule({
    imports: [
        ServerModule,
        AppModule
    ],
    bootstrap: [AppComponent]
})
export class AppServerModule {
}
