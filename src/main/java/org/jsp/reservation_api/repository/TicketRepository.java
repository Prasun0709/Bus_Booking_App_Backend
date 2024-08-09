package org.jsp.reservation_api.repository;

import org.jsp.reservation_api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
