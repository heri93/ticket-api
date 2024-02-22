package com.edts.ticketapi.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edts.ticketapi.exception.ConcertNotFoundException;
import com.edts.ticketapi.exception.TicketsNotAvailableException;
import com.edts.ticketapi.model.Booking;
import com.edts.ticketapi.model.Ticket;
import com.edts.ticketapi.model.request.BookingRequest;
import com.edts.ticketapi.repository.BookingRepository;
import com.edts.ticketapi.repository.TicketRepository;
import com.edts.ticketapi.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	private BookingRepository bookingRepository;

	private TicketRepository ticketRepository;

	@Autowired
	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Autowired
	public void setTicketRepository(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	/**
	 * Books a ticket based on the provided booking request.
	 * 
	 * @param bookingRequest The booking request containing the ticket ID, customer name, and phone number.
	 * @return The booking object representing the booked ticket.
	 * @throws ConcertNotFoundException If the ticket with the provided ID is not found.
	 * @throws TicketsNotAvailableException If there are no available tickets for the concert.
	 */
	@Transactional
	public Booking bookTicket(BookingRequest bookingRequest) {
		// Retrieve the ticket 
		Ticket ticket = ticketRepository.findById(bookingRequest.getTicketId()).orElseThrow(
				() -> new ConcertNotFoundException("Ticket not found with ticket ID: " + bookingRequest.getTicketId()));

		// Check if there are available tickets for the concert
		if (isTicketAvailable(ticket)) {
			// Create a booking
			Booking booking = new Booking();
			booking.setTicket(ticket);
			booking.setPhoneNumber(bookingRequest.getPhoneNumber());
			booking.setCreatedTime(new Date());
			booking.setCustomerName(bookingRequest.getCustomerName());
			
			ticket.setAvailableSeat(ticket.getAvailableSeat() - 1);
			ticketRepository.save(ticket);
			// Save the booking
			return bookingRepository.save(booking);
		} else {
			throw new TicketsNotAvailableException("Tickets not available for concert " + ticket.getConcert().getName()
					+ " with ticket ID: " + bookingRequest.getTicketId());
		}
	}

	/**
	 * @param ticket
	 * @return 
	 */
	private boolean isTicketAvailable(Ticket ticket) {

		return ticket.getAvailableSeat() > 0;
	}

}
