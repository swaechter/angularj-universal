import {Routes} from '@angular/router';

export const approutes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        loadChildren: './home/home.module#HomeModule'
    },
    {
        path: 'keywords',
        loadChildren: './keywords/keywords.module#KeywordsModule'
    },
    {
        path: 'about',
        loadChildren: './about/about.module#AboutModule'
    },
    {
        path: '**',
        redirectTo: '/home',
        pathMatch: 'full'
    },
];
