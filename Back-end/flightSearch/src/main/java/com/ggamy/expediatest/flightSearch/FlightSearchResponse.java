package com.ggamy.expediatest.flightSearch;

import java.util.List;

class FlightSearchResponse {
    public boolean error;
    public String message;
    public List<Flight> flights;

    public FlightSearchResponse(String message, List<Flight> flights, boolean error) {
        this.message = message;
        this.flights = flights;
        this.error = error;
    }
}