package org.jsp.reservation_api.controller;

import org.jsp.reservation_api.dto.TicketResponse;
import org.jsp.reservation_api.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {
	@Autowired
	private TicketService ticketService;

	@PostMapping("/{busId}/{userId}/{numberOfSeats}")
	public TicketResponse bookTicket(@PathVariable int busId, @PathVariable int userId,
			@PathVariable int numberOfSeats) {
		return ticketService.bookTicket(userId, busId, numberOfSeats);
	}
}
