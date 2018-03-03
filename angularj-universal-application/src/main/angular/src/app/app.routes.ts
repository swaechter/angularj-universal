import {Routes} from '@angular/router';

import {PageHomeComponent} from './components/page-home/page-home.component';
import {PageKeywordsComponent} from "./components/page-keywords/page-keywords.component";
import {PageKeywordComponent} from "./components/page-keyword/page-keyword.component";
import {PageAboutComponent} from './components/page-about/page-about.component';

export const approutes: Routes = [
    {
        path: '',
        component: PageHomeComponent
    },
    {
        path: 'home',
        component: PageHomeComponent
    },
    {
        path: 'keywords',
        component: PageKeywordsComponent
    },
    {
        path: 'keywords/:id',
        component: PageKeywordComponent
    },
    {
        path: 'about',
        component: PageAboutComponent
    },
    {
        path: '**',
        component: PageHomeComponent
    }
];
