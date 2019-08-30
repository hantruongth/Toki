package com.toki.games.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.toki.games.app.service.FlightService;
import com.toki.games.app.service.dto.FlightDTO;
import com.toki.games.app.service.dto.FlightResponseDTO;
import com.toki.games.app.web.rest.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping(value = "/search", params = {"departure", "arrival"})
    public Response getAllFlights(Pageable pageable,
                                  @RequestParam(value = "departure") String departure,
                                  @RequestParam(value = "arrival") String arrival,
                                  @RequestParam(value = "forceGetDataFromProvider", defaultValue = "false") boolean forceGetDataFromProvider) {
        return new Response(flightService.searchAllFlights(departure, arrival, forceGetDataFromProvider, pageable), "/api/flight/search");
    }
}

