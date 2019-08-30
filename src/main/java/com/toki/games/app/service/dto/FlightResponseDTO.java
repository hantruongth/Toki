package com.toki.games.app.service.dto;

import java.util.UUID;

public class FlightResponseDTO extends FlightDTO {
    private UUID uuid;
    private String flight;
    boolean asc = true;
    String orderBy;
    public FlightResponseDTO() {
        super();
    }

    public FlightResponseDTO(UUID uuid, String flight) {
        this.uuid = uuid;
        this.flight = flight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }
//
//    public Comparator<FlightResponseDTO> getComparator(){
//        return new Comparator<FlightResponseDTO>() {
//            @Override
//            public int compare(FlightResponseDTO o1, FlightResponseDTO o2) {
//
//                int sign = (asc)?1:-1;
//                switch (orderBy) {
//                    case "sell_count":
//                        return   Integer.compare (o1.getSellCount(),o2.getSellCount()) * sign;
//                    case "saledate":
//                        return o1.getSaleDate().compareTo(o2.getSaleDate())*sign;
//                    case "startdate":
//                        return o1.getStartDate().compareTo(o2.getStartDate())*sign;
//                    case "co_id":
//                        return o1.getCoId().compareTo(o2.getCoId())*sign;
//                    case "deal_id":
//                        return o1.getDealId().compareTo(o2.getDealId())*sign;
//                    case "category_no":
//                        return o1.getCategoryNo().compareTo(o2.getCategoryNo())*sign;
//                    case "parent_no":
//                        return o1.getParentNo().compareTo(o2.getParentNo())*sign;
//                    default:
//                        return Long.compare(o1.getSales(),o2.getSales())*sign;
//                }
//            }
//        };
//    }
}
