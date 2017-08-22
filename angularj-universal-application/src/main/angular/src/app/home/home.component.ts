import {Component, OnInit} from "@angular/core";
import {KeywordService} from "../shared/keyword/keyword.service";
import {Keyword} from "../shared/keyword/keyword.model";

@Component({
    templateUrl: "home.component.html"
})
export class HomeComponent implements OnInit {

    keywords: Keyword[];

    constructor(private keywordservice: KeywordService) {
    }

    ngOnInit(): void {
        this.keywordservice.getKeywords().subscribe(keywords => {
            this.keywords = keywords;
        }, error => {
            console.log("Unable to get the keywords: " + error);
        });
    }
}
