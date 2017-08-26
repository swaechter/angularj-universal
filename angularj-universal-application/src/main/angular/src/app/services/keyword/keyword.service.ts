import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Keyword} from "./keyword.model";
import {Settings} from "../../settings";

@Injectable()
export class KeywordService {

    constructor(private http: HttpClient) {
    }

    getKeywords() {
        return this.http.get<Keyword[]>(Settings.BACKEND_URL + "/api/keyword");
    }
}
