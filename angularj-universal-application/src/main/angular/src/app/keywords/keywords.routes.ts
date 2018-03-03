import {Routes} from '@angular/router';

import {KeywordsComponent} from "./keywords.component";
import {KeywordComponent} from "./keyword.component";

export const keywordsroutes: Routes = [
    {
        path: '',
        component: KeywordsComponent
    },
    {
        path: ':id',
        component: KeywordComponent
    },
];
