package org.jsp.reservation_api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BusRequest {
	private int id;
	private String name;
	private String busNumber;
	private LocalDate dateOfDeparture;
	private String from;
	private String to;
	private int numberOfSeats;
}
