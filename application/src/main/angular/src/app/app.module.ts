import {NgModule} from '@angular/core'
import {BrowserModule} from '@angular/platform-browser'
import {AppComponent} from "./app.component";

@NgModule({
    imports: [
        BrowserModule.withServerTransition({
            appId: 'app-root'
        }),
    ],
    declarations: [AppComponent],
    bootstrap: [AppComponent],
    exports: [AppComponent]
})
export class AppModule {
}
