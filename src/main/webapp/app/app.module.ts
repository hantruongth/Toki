import './vendor.ts';

import {CUSTOM_ELEMENTS_SCHEMA, NgModule, ViewContainerRef} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {NgbDatepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import {Ng2Webstorage} from 'ngx-webstorage';
import {NgJhipsterModule} from 'ng-jhipster';

import {AuthInterceptor} from './blocks/interceptor/auth.interceptor';
import {AuthExpiredInterceptor} from './blocks/interceptor/auth-expired.interceptor';
import {ErrorHandlerInterceptor} from './blocks/interceptor/errorhandler.interceptor';
import {NotificationInterceptor} from './blocks/interceptor/notification.interceptor';
import {TokiGamesSharedModule} from 'app/shared';
import {TokiGamesCoreModule} from 'app/core';
import {TokiGamesAppRoutingModule} from './app-routing.module';
import {TokiGamesHomeModule} from './home/home.module';
import {TokiGamesAccountModule} from './account/account.module';
import * as moment from 'moment';

import {
    ActiveMenuDirective,
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    NavbarComponent,
    PageRibbonComponent
} from './layouts';
import {FlightComponent} from './flight/flight.component';

@NgModule({
    imports: [
        BrowserModule,
        TokiGamesAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgJhipsterModule.forRoot({
            alertAsToast: false,
            alertTimeout: 5000,
            i18nEnabled: true,
            defaultI18nLang: 'en'
        }),
        TokiGamesSharedModule.forRoot(),
        TokiGamesCoreModule,
        TokiGamesHomeModule,
        TokiGamesAccountModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent, FlightComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class TokiGamesAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
