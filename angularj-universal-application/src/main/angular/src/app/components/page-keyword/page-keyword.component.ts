import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Keyword} from "../../services/keyword/keyword.model";

@Component({
    templateUrl: 'page-keyword.component.html',
    styleUrls: ['page-keyword.component.css']
})
export class PageKeywordComponent implements OnInit, OnDestroy {

    keyword: Keyword;

    subscriber: any;

    constructor(private route: ActivatedRoute) {
        this.keyword = new Keyword(42, "Dummy keyword");
    }

    ngOnInit(): void {
        this.subscriber = this.route.params.subscribe(parameters => {
            this.keyword.id = +parameters['id'];
        });
    }

    ngOnDestroy(): void {
        this.subscriber.unsubscribe();
    }
}
