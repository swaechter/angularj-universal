import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
    templateUrl: "login.component.html",
    styleUrls: ["login.component.css"]
})
export class LoginComponent implements OnInit {

    username: string;

    password: string;

    constructor(private authenticationservice: AuthenticationService, private router: Router) {
    }

    ngOnInit(): void {
    }

    onLoginSubmit() {
    }
}
