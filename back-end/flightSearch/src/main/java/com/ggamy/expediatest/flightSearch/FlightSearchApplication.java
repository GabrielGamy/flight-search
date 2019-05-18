package com.ggamy.expediatest.flightSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FlightSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApplication.class, args);
	}

	@GetMapping(path = "/")
	public String index() {
		return ("<h1>Flight REST API</h1>" +
			    "<div>Visite <a href='./flights'>/flights</a> to see the available flights.</div><br />" +
				"<div>You can also see the web application here " +
				"<a href='https://flightsearch-expedia.firebaseapp.com/'>https://flightsearch-expedia.com/<a></div>");
	}
}
