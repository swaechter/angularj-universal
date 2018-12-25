import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {KeywordsRoutingModule} from './keywords-routing.module';
import {KeywordsComponent} from './keywords.component';
import {KeywordComponent} from './keyword.component';
import {KeywordService} from './services/keyword.service';

@NgModule({
  declarations: [
    KeywordsComponent,
    KeywordComponent
  ],
  imports: [
    CommonModule,
    KeywordsRoutingModule
  ],
  providers: [
    KeywordService
  ]
})
export class KeywordsModule {
}
