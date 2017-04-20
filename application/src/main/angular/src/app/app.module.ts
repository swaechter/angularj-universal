import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";

export {AppComponent};

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule.withServerTransition({appId: "springular"})
    ],
    exports: [AppComponent],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
