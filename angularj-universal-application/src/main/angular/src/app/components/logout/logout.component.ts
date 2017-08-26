import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
    templateUrl: "logout.component.html"
})
export class LogoutComponent implements OnInit {

    constructor(private authenticationservice: AuthenticationService, private router: Router) {
    }

    ngOnInit(): void {
        this.authenticationservice.logoutUser();
        this.router.navigate(["login"]);
    }
}
