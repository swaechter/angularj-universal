import {Component, OnInit} from '@angular/core';

import {KeywordService} from './services/keyword.service';
import {Keyword} from './services/keyword.model';

@Component({
  templateUrl: './keywords.component.html',
  styleUrls: ['./keywords.component.css']
})
export class KeywordsComponent implements OnInit {

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
