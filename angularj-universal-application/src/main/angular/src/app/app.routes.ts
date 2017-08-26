import {Routes} from "@angular/router";
//import {AuthenticationGuard} from "./guards/authentication.guard";
import {LoginComponent} from "./components/login/login.component";
import {LogoutComponent} from "./components/logout/logout.component";
import {PageComponent} from "./components/page/page.component";
import {PageHomeComponent} from "./components/page-home/page-home.component";
import {PageAboutComponent} from "./components/page-about/page-about.component";

export const approutes: Routes = [
    {
        path: "",
        redirectTo: "/login",
        pathMatch: "full"
    },
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: "logout",
        component: LogoutComponent
    },
    {
        path: "page",
        component: PageComponent,
        //canActivate: [AuthenticationGuard],
        children: [
            {
                path: "",
                redirectTo: "home",
                pathMatch: "full"
            },
            {
                path: "home",
                component: PageHomeComponent
            },
            {
                path: "about",
                component: PageAboutComponent
            }
        ]
    },
    {
        path: "**",
        redirectTo: "/login",
        pathMatch: "full"
    }
];
