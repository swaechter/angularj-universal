import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";

export {AppComponent};

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        AboutComponent
    ],
    imports: [
        BrowserModule.withServerTransition({appId: "springular"}),
        RouterModule.forRoot([
            {
                path: "",
                redirectTo: "home",
                pathMatch: "full"
            },
            {
                path: "home",
                component: HomeComponent,
            },
            {
                path: "about",
                component: AboutComponent,
            },
            {
                path: "**",
                redirectTo: "home",
                pathMatch: "full"
            },
        ])
    ],
    exports: [AppComponent],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
