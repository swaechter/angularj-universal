import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {AppComponent} from "./app.component";
import {approutes} from "./app.routes";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";

@NgModule({
    imports: [
        RouterModule.forRoot(approutes),
        BrowserModule.withServerTransition({
            appId: "app-root"
        }),
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        AboutComponent
    ],
    bootstrap: [
        AppComponent
    ],
    exports: [
        AppComponent
    ]
})
export class AppModule {
}
