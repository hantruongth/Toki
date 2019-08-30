import {Component, OnDestroy, OnInit} from '@angular/core';
import {ITEMS_PER_PAGE} from 'app/shared';
import {JhiAlertService, JhiParseLinks} from 'ng-jhipster';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Flight} from 'app/flight/flight.model';
import {FlightsService} from 'app/flight/flights.service';

@Component({
  selector: 'jhi-flight',
  templateUrl: './flight.component.html',
  styles: []
})
export class FlightComponent implements OnInit, OnDestroy {

    flights: Flight[];
    departure: string;
    arrival: string;
    itemsPerPage: any;
    links: any;
    queryCount: number;
    page: number;
    routeData: any;
    predicate: any;
    previousPage: any;
    reverse: boolean;

    totalItems: number;

    constructor(
        private flightService: FlightsService,
        private alertService: JhiAlertService,
        private parseLinks: JhiParseLinks,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = 1;
            this.previousPage = 0;
            this.reverse = true;
            this.predicate = 'departureTime';
        });
    }

    ngOnInit() {
        this.loadAll(false);
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    onSearch() {
        this.loadAll(false);
    }
    onSearchForNewFlights() {
        this.loadAll(true);
    }

    loadAll(forceGetNewFlights: boolean) {
        this.flightService
            .query({
                departure: this.departure !== undefined ? this.departure : '',
                arrival: this.arrival !== undefined ? this.arrival : '',
                forceGetDataFromProvider: forceGetNewFlights,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),

            })
            .subscribe(
                (res: HttpResponse<Flight[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        return result;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/flight'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll(false);
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(data.pagination.link);
        this.totalItems = data.pagination.total;
        this.queryCount = this.totalItems;
        // this.page = data.pagination.
        this.flights = data['data'];
    }

    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }
}
