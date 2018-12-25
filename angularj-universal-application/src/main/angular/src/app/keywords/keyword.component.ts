import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Keyword} from './services/keyword.model';

@Component({
  templateUrl: './keyword.component.html',
  styleUrls: ['./keyword.component.css']
})
export class KeywordComponent implements OnInit, OnDestroy {

  subscriber: any;

  keyword: Keyword;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.subscriber = this.route.params.subscribe(params => {
      const id = +params['id'];
      this.keyword = new Keyword(id, 'Dummy keyword');
    });
  }

  ngOnDestroy(): void {
    this.subscriber.unsubscribe();
  }
}
