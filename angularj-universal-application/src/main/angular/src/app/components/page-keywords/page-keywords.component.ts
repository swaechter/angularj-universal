import {Component, OnInit} from '@angular/core';
import {KeywordService} from '../../services/keyword/keyword.service';
import {Keyword} from '../../services/keyword/keyword.model';

@Component({
    templateUrl: 'page-keywords.component.html',
    styleUrls: ['page-keywords.component.css']
})
export class PageKeywordsComponent implements OnInit {

    keywords: Keyword[];

    constructor(private keywordservice: KeywordService) {
    }

    ngOnInit(): void {
        this.keywordservice.getKeywords().subscribe(keywords => {
            this.keywords = keywords;
        }, error => {
            console.log('Unable to get the keywords: ' + error);
        });
    }
}
