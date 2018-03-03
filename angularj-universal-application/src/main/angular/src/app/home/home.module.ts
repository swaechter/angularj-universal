import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";

import {homeroutes} from "./home.routes";
import {HomeComponent} from './home.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(homeroutes)
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {
}
