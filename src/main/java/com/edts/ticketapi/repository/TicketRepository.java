package com.edts.ticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edts.ticketapi.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

}
