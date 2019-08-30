package com.toki.games.app.service;

import com.toki.games.app.service.dto.FlightDTO;
import com.toki.games.app.service.dto.FlightResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService {
    List<FlightResponseDTO> searchForLights(String url);

    Page<FlightDTO> searchAllFlights(String departure, String arrival, boolean forceGetDataFromProvider, Pageable pageable);
}
