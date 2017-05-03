import {platformBrowser} from '@angular/platform-browser'
import {AppModuleNgFactory} from './ngfactory/src/app/app.module.ngfactory'

platformBrowser().bootstrapModuleFactory(AppModuleNgFactory);
