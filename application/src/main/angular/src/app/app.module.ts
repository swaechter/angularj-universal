import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {AppComponent} from "./app.component";
import {approutes} from "./app.routes";
import {HomeComonent} from "./home/home.component";
import {AboutComonent} from "./about/about.component";

@NgModule({
    imports: [
        RouterModule.forRoot(approutes),
        BrowserModule.withServerTransition({
            appId: 'app-root'
        }),
    ],
    declarations: [
        AppComponent,
        HomeComonent,
        AboutComonent
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
