import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {KeywordsComponent} from './keywords.component';
import {KeywordComponent} from './keyword.component';

const routes: Routes = [
  {
    path: '',
    component: KeywordsComponent
  },
  {
    path: ':id',
    component: KeywordComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KeywordsRoutingModule {
}
