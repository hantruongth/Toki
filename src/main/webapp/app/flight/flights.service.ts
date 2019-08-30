import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { createRequestOption } from 'app/shared';
import { SERVER_API_URL } from 'app/app.constants';
import {Flight} from 'app/flight/flight.model';

@Injectable({ providedIn: 'root' })
export class FlightsService {
    constructor(private http: HttpClient) {}

    query(req: any): Observable<HttpResponse<Flight[]>> {
        const params: HttpParams = createRequestOption(req);
        params.set('fromDate', req.fromDate);
        params.set('toDate', req.toDate);

        const requestURL = SERVER_API_URL + 'api/flight/search';

        return this.http.get<Flight[]>(requestURL, {
            params,
            observe: 'response'
        });
    }
}
