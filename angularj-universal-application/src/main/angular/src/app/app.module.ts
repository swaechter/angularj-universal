import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeModule} from './home/home.module';
import {AboutModule} from './about/about.module';
import {KeywordsModule} from './keywords/keywords.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule.withServerTransition({appId: 'serverApp'}),
    HttpClientModule,
    AppRoutingModule,
    HomeModule,
    AboutModule,
    KeywordsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
