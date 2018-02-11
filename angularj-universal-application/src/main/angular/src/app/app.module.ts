import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {LogoutComponent} from './components/logout/logout.component';
import {PageComponent} from './components/page/page.component';
import {PageHomeComponent} from './components/page-home/page-home.component';
import {PageAboutComponent} from './components/page-about/page-about.component';
import {AuthenticationService} from './services/authentication/authentication.service';
import {KeywordService} from './services/keyword/keyword.service';
import {approutes} from './app.routes';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        LogoutComponent,
        PageComponent,
        PageHomeComponent,
        PageAboutComponent
    ],
    imports: [
        BrowserModule.withServerTransition({appId: 'serverApp'}),
        RouterModule.forRoot(approutes),
        HttpClientModule,
        FormsModule
    ],
    exports: [RouterModule],
    providers: [
        AuthenticationService,
        KeywordService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
