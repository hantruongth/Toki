export class Flight {
    constructor(public id?: string,
                public departure?: string,
                public arrival?: string,
                public departureTime?: string,
                public arrivalTime?: string,
                public businessFlight?: boolean) {
    };
}
