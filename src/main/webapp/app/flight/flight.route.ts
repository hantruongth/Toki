import {Route} from '@angular/router';

import {FlightComponent} from 'app/flight/flight.component';

export const flightRoute: Route = {
    path: 'flight',
    component: FlightComponent,
    data: {
        authorities: [],
        pageTitle: 'flight.title'
    }
};
