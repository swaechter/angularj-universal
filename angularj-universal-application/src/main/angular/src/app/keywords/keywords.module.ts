import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";

import {keywordsroutes} from "./keywords.routes";
import {KeywordsComponent} from './keywords.component';
import {KeywordComponent} from "./keyword.component";
import {KeywordService} from "./services/keyword.service";

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(keywordsroutes)
    ],
    declarations: [
        KeywordsComponent,
        KeywordComponent
    ],
    providers: [
        KeywordService
    ]
})
export class KeywordsModule {
}
