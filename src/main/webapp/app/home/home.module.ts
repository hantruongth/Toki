import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {TokiGamesSharedModule} from 'app/shared';
import {HOME_ROUTE, HomeComponent} from './';

@NgModule({
    imports: [TokiGamesSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TokiGamesHomeModule {}
