import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {PageHomeComponent} from './components/page-home/page-home.component';
import {PageKeywordsComponent} from './components/page-keywords/page-keywords.component';
import {PageKeywordComponent} from './components/page-keyword/page-keyword.component';
import {PageAboutComponent} from './components/page-about/page-about.component';
import {KeywordService} from './services/keyword/keyword.service';
import {approutes} from './app.routes';

@NgModule({
    declarations: [
        AppComponent,
        PageHomeComponent,
        PageKeywordsComponent,
        PageKeywordComponent,
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
        KeywordService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
