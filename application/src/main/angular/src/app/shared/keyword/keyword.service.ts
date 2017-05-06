import {Http} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import "rxjs/Rx";
import {Settings} from "../../settings";
import {Keyword} from "./keyword.model";

@Injectable()
export class KeywordService {

    constructor(private http: Http) {
    }

    getKeywords(): Observable<Keyword[]> {
        return this.http.get(Settings.BACKEND_URL + "/api/keyword").map(data => <Keyword[]> data.json());
    }
}
