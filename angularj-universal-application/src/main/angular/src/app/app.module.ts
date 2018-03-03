import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {approutes} from './app.routes';
import {AppComponent} from './app.component';

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserModule.withServerTransition({appId: 'serverApp'}),
        RouterModule.forRoot(approutes),
        HttpClientModule,
        FormsModule
    ],
    exports: [RouterModule],
    bootstrap: [AppComponent]
})
export class AppModule {
}
