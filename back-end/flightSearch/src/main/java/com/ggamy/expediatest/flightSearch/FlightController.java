package com.ggamy.expediatest.flightSearch;

import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.stream.Collectors;

enum Meridiem { AM, PM }

@RestController
@RequestMapping("flights")
public class FlightController {

    /**
     * Get all available flights
     * */
    @GetMapping
    @CrossOrigin(origins = "*")
    public FlightSearchResponse getAll() {
        return new FlightSearchResponse("Success", FlightResource.availableFlights(), false);
    }

    /**
     * Search a flight using the departure time.
     * The search algorithm will only display flights at a distance (plus or minus) of 5 hours from the
     * selecte time. For example if you search for a 6AM flight, you
     * will see both the 7:30AM and the 10:30AM flights in the results.
     * */
    @GetMapping(path = "/search")
    @CrossOrigin(origins = "*")
    public FlightSearchResponse search(@RequestParam(value="hour") int hour,
                                       @RequestParam(value="minutes") int minutes,
                                       @RequestParam(value="meridiem") Meridiem meridiem,
                                       @RequestParam(value="range", defaultValue = "5") int range) {

        List<Flight> results = new ArrayList<>();

        try {
            // Add a zero left padding to the hour and minutes
            String formattedHour = String.format("%s", (hour < 10) ? "0" + hour : hour);
            String formattedMinutes = String.format("%s", (minutes < 10) ? "0" + minutes : minutes);

            // Get the formatted departure time (e.g hh:mm AM)
            String formattedDepartureTime = String.format(
                "%s:%s %s",
                formattedHour,
                formattedMinutes,
                meridiem
            );

            LocalTime searchTime = LocalTime.parse(
                formattedDepartureTime,
                DateTimeFormatter.ofPattern("hh:mm a")
            );

            // Only display flights at a distance (plus or minus) of "range" hours from the search time.
            results = filterFlights(FlightResource.availableFlights(), searchTime, range);

        } catch (DateTimeParseException ex){
            return (new FlightSearchResponse("The departure time (hour, minutes) provided is not a valid time", results, true));
        } catch (Exception ex) {
            return (new FlightSearchResponse(ex.getMessage(), results, true));
        }

        return (new FlightSearchResponse("Success", results, false));
    }

    /**
     * Filter flights to return only the ones at a distance (plus or minus) hours from a search time.
     * */
    private List<Flight> filterFlights(List<Flight> flights, LocalTime time, int minusOrPlusRange) {
        return (flights.stream().filter(f -> {
                LocalTime flightDepartureTime = f.departure;

                // Check if the search time is exactly at a distance of "minusOrPlusRange" hours
                if(time.minusHours(minusOrPlusRange).compareTo(flightDepartureTime) == 0 ||
                   time.plusHours(minusOrPlusRange).compareTo(flightDepartureTime) == 0)
                    return true;

                // Check if the search time is included in "minusOrPlusRange" hours
                return (flightDepartureTime.isAfter(time.minusHours(minusOrPlusRange)) &&
                        flightDepartureTime.isBefore(time.plusHours(minusOrPlusRange)));

            }).collect(Collectors.toList()));
    }
}
