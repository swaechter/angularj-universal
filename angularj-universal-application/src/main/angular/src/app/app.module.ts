import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {KeywordService} from "./shared/keyword/keyword.service";
import {approutes} from "./app.routes";

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        AboutComponent
    ],
    imports: [
        BrowserModule.withServerTransition({appId: "my-app"}),
        RouterModule.forRoot(approutes),
        HttpClientModule
    ],
    exports: [RouterModule],
    providers: [KeywordService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
