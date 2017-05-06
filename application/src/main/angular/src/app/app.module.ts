import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {AppComponent} from "./app.component";
import {approutes} from "./app.routes";
import {OverviewComponent} from "./overview/overview.component";
import {AboutComponent} from "./about/about.component";
import {KeywordService} from "./shared/keyword/keyword.service";

@NgModule({
    imports: [
        RouterModule.forRoot(approutes),
        BrowserModule.withServerTransition({
            appId: "app-root"
        }),
    ],
    declarations: [
        AppComponent,
        OverviewComponent,
        AboutComponent
    ],
    providers: [
        KeywordService
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
