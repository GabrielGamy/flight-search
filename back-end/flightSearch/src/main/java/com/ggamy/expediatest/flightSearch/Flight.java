package com.ggamy.expediatest.flightSearch;

import java.time.LocalTime;

public class Flight {
    public String flight;
    public LocalTime departure;

    public Flight(String flight, LocalTime departure) {
        this.flight = flight;
        this.departure = departure;
    }
}
