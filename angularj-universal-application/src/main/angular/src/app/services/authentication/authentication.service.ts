import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Headers} from "@angular/http";

@Injectable()
export class AuthenticationService {

    constructor(private http: HttpClient) {
    }

    public loginUser(username: string, password: string) {
    }

    public logoutUser() {
    }

    public isUserLoggedIn() {
        return true;
    }

    public getUnauthenticatedHeaders() {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        return headers;
    }

    public getAuthenticatedHeaders() {
        let headers = this.getUnauthenticatedHeaders();
        return headers;
    }
}
