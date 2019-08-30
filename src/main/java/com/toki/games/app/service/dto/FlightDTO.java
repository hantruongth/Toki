package com.toki.games.app.service.dto;

import java.util.Comparator;
import java.util.Date;

public class FlightDTO {
    private String id;
    private String departure;
    private String arrival;
    private Date departureTime;
    private Date arrivalTime;
    private boolean isBusinessFlight = false;

    boolean asc = true;
    String orderBy;

    public FlightDTO() {
    }

    public FlightDTO(String orderBy, boolean asc) {
        this.asc = asc;
        this.orderBy = orderBy;
    }

    public FlightDTO(String id, String departure, String arrival, Date departureTime, Date arrivalTime, boolean isBusinessFlight) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.isBusinessFlight = isBusinessFlight;
    }

    public FlightDTO(FlightResponseDTO flightResponseDTO) {
        this.arrival = flightResponseDTO.getArrival();
        this.departure = flightResponseDTO.getDeparture();
        this.arrivalTime = flightResponseDTO.getArrivalTime();
        this.departureTime = flightResponseDTO.getDepartureTime();
        this.id = flightResponseDTO.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isBusinessFlight() {
        return isBusinessFlight;
    }

    public void setBusinessFlight(boolean businessFlight) {
        isBusinessFlight = businessFlight;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Comparator<FlightDTO> getComparator() {
        return new Comparator<FlightDTO>() {
            @Override
            public int compare(FlightDTO o1, FlightDTO o2) {
                int sign = (asc) ? 1 : -1;
                switch (orderBy) {
                    case "arrivalTime":
                        return o1.getArrivalTime().compareTo(o2.getArrivalTime()) * sign;
                    case "departureTime":
                        return o1.getDepartureTime().compareTo(o2.getDepartureTime()) * sign;
                    case "arrival":
                        return o1.getArrival().compareTo(o2.getArrival()) * sign;
                    case "departure":
                        return o1.getDeparture().compareTo(o2.getDeparture()) * sign;
                    default:
                        return o1.getDepartureTime().compareTo(o2.getDepartureTime()) * sign;
                }
            }
        };
    }
}
