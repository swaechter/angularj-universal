import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {AuthenticationService} from "../services/authentication/authentication.service";

@Injectable()
export class AuthenticationGuard implements CanActivate {

    constructor(private authenticationservice: AuthenticationService, private router: Router) {
    }

    canActivate() {
        return true;
    }
}
