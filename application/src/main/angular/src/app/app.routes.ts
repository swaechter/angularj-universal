import {Routes} from "@angular/router";
import {HomeComonent} from "./home/home.component";
import {AboutComonent} from "./about/about.component";

export const approutes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComonent
    },
    {
        path: 'about',
        component: AboutComonent
    },
    {
        path: '**',
        component: HomeComonent
    }
];
