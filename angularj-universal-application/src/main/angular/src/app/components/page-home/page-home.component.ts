import {Component, OnInit} from "@angular/core";
import {KeywordService} from "../../services/keyword/keyword.service";
import {Keyword} from "../../services/keyword/keyword.model";

@Component({
    templateUrl: "page-home.component.html",
    styleUrls: ["page-home.component.css"]
})
export class PageHomeComponent implements OnInit {

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
