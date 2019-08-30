package com.toki.games.app.service.impl;

import com.toki.games.app.config.Constants;
import com.toki.games.app.config.DefinedProperties;
import com.toki.games.app.service.FlightService;
import com.toki.games.app.service.dto.FlightDTO;
import com.toki.games.app.service.dto.FlightResponseDTO;
import com.toki.games.app.service.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightServiceImpl implements FlightService{

    private final Logger log = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private ForkJoinPool forkJoinPool = new ForkJoinPool(Constants.NUMBER_OF_THREAD_HANDLE_API_SEARCH);

    private List<FlightResponseDTO> flightResponseDTO = new ArrayList<>();

    @Autowired
    private DefinedProperties definedProperties;

    @Override
    public List<FlightResponseDTO> searchForLights(String url) {
        ResponseEntity<List<FlightResponseDTO>> flightResponse = null;

        try {
            flightResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightResponseDTO>>() {});
        }catch (RestClientException e){
            throw new RuntimeException("Exception occur with calling api to search flight");
        }
        if(flightResponse != null && flightResponse.getBody() != null && !flightResponse.getBody().isEmpty()){
            List<FlightResponseDTO> flights = flightResponse.getBody();
            if(flights.size() > Constants.FLIGHT_SEARCH_LIMIT)
                return flights.subList(0, Constants.FLIGHT_SEARCH_LIMIT);
            return flights;
        }
        return null;
    }

    @Override
    public Page<FlightDTO> searchAllFlights(String departure, String arrival, boolean forceGetDataFromProvider, Pageable pageable) {

        // only call to flight providers to get new flight when requested or at the first time.
        if(this.flightResponseDTO.isEmpty() || forceGetDataFromProvider) {
            List<String> urls = Arrays.asList(definedProperties.getUrl().getCheapUrl(), definedProperties.getUrl().getBusinessUrl());
            try {
                // call rest using multiple threads in parallel
                this.flightResponseDTO = forkJoinPool.submit(() -> {
                    List<FlightResponseDTO> result = urls.parallelStream().flatMap(url -> {
                        List<FlightResponseDTO> flights = searchForLights(url);
                        if (flights != null && !flights.isEmpty()) {
                            return flights.stream();
                        }
                        return Stream.empty();
                    }).collect(Collectors.toList());
                    return result;
                }).get();

            } catch (ExecutionException e1) {
                e1.printStackTrace();
                log.error("Exception in executing threads");

            } catch (InterruptedException e2) {
                e2.printStackTrace();
                log.error("Exception in searching api");
            }
        }

        return processFlightData(this.flightResponseDTO, departure, arrival, pageable);

    }

    private Page<FlightDTO> processFlightData(List<FlightResponseDTO> flightResponseDTOS, String departure, String arrival, Pageable pageable){

        List<FlightDTO> result = new ArrayList<>();
        List<FlightDTO> flightDTOS = new ArrayList<>();

        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = startIndex+pageable.getPageSize();

        String sort = pageable.getSort().toString();
        String sortField = Constants.DEFAULT_SORT_FIELD;
        String sortDirection = Constants.DEFAULT_SORT_DIRECTION;
        if(sort != null){
            String[] tmp = sort.split(":");
            if(tmp.length > 1){
                sortField = tmp[0].trim();
                sortDirection = tmp[1].trim();
            }
        }
        // get comparator for sorting
        Comparator<FlightDTO> comparator = new FlightDTO(sortField, Constants.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(sortDirection) ? true : false).getComparator();

        flightResponseDTOS.forEach(f -> {

            // for filter departure. When filtered, skip the flights that mismatch from the lists
            if(departure != null && f.getDeparture() != null && !f.getDeparture().toLowerCase().contains(departure.toLowerCase())){
                return;
            }
            // for filter arrival. When filtered, skip the flights that mismatch from the lists
            if(arrival != null && f.getArrival() != null && !f.getArrival().toLowerCase().contains(arrival.toLowerCase())){
                return;
            }

            // handle the business flight due to the difference in response.
            if(f != null && f.getUuid() != null){
                FlightDTO flightDTO = new FlightDTO();
                Date departureTime = DateTimeUtil.parseDateTimeFromString(f.getDeparture());
                Date arrivalTime = DateTimeUtil.parseDateTimeFromString(f.getArrival());
                flightDTO.setDepartureTime(departureTime != null ? departureTime : null);
                flightDTO.setArrivalTime(arrivalTime != null ? arrivalTime : null);
                String flight = f.getFlight();
                if(flight != null){
                    String[] array = flight.split("->");
                    if(array.length > 1){
                        flightDTO.setDeparture(array[0].trim());
                        flightDTO.setArrival(array[1].trim());
                    }else{
                        flightDTO.setDeparture(null);
                        flightDTO.setArrival(null);
                    }
                }
                flightDTO.setBusinessFlight(true);
                flightDTO.setId(f.getUuid().toString());
                result.add(flightDTO);


            }
            else {
                result.add(new FlightDTO(f));
            }

        });

        // sort the result with comparator
        result.sort(comparator);

        // handle paging
        if(startIndex < result.size()) {
            flightDTOS = result.subList(startIndex, (endIndex < result.size()) ? endIndex : result.size());
        }else{
            flightDTOS = Collections.EMPTY_LIST;
        }

        return new PageImpl<FlightDTO>(flightDTOS, pageable,result.size());

    }
}
