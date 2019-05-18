package com.ggamy.expediatest.flightSearch;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.time.LocalTime;
import java.util.List;

public class FlightResource {

    public static List<Flight> availableFlights() {
        List<Flight> flights = new ArrayList<>();

        flights.add(new Flight("Air Canada 8099", LocalTime.of(7, 30)));

        flights.add(new Flight("United Airline 6115", LocalTime.of(10, 30)));

        flights.add(new Flight("WestJet 6456", LocalTime.of(12, 30)));

        flights.add(new Flight("Delta 3833", LocalTime.of(15, 00)));

        return flights;
    }
}
