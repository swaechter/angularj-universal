import {Routes} from "@angular/router";
import {OverviewComponent} from "./overview/overview.component";
import {AboutComponent} from "./about/about.component";

export const approutes: Routes = [
    {
        path: "",
        redirectTo: "/overview",
        pathMatch: "full"
    },
    {
        path: "overview",
        component: OverviewComponent
    },
    {
        path: "about",
        component: AboutComponent
    },
    {
        path: "**",
        component: OverviewComponent
    }
];
