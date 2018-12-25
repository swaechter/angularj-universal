import {NgModule} from '@angular/core';
import {ServerModule} from '@angular/platform-server';

import {AppModule} from './app.module';
import {AppComponent} from './app.component';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
    // ModuleMapLoaderModule TODO: Check how to support lazy loaded modules
  ],
  bootstrap: [AppComponent],
})
export class AppServerModule {
}
