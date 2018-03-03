import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";

import {aboutroutes} from "./about.routes";
import {AboutComponent} from './about.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(aboutroutes)
    ],
    declarations: [
        AboutComponent
    ]
})
export class AboutModule {
}
