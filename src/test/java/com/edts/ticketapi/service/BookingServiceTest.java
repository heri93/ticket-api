package com.edts.ticketapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.edts.ticketapi.exception.ConcertNotFoundException;
import com.edts.ticketapi.exception.TicketsNotAvailableException;
import com.edts.ticketapi.model.Booking;
import com.edts.ticketapi.model.Concert;
import com.edts.ticketapi.model.Ticket;
import com.edts.ticketapi.model.request.BookingRequest;
import com.edts.ticketapi.repository.BookingRepository;
import com.edts.ticketapi.repository.TicketRepository;
import com.edts.ticketapi.service.impl.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private BookingServiceImpl bookingService;

	private static final String PHONE_NUMBER = "000000";
	private static final String CUSTOMER_NAME = "Test customer name";

	@Test
	void bookTicket_Valid_Available() {
		// Arrange
		Integer availableTicket = 100;
		Ticket ticket = new Ticket();
		ticket.setAvailableSeat(availableTicket);
		ticket.setId(new Long(1));
		ticket.setPrice(new BigDecimal("1000"));
		ticket.setTicketType("VIP");

		Booking expectedBooking = new Booking();
		expectedBooking.setPhoneNumber(PHONE_NUMBER);
		expectedBooking.setCustomerName(CUSTOMER_NAME);
		expectedBooking.setTicket(ticket);
		expectedBooking.setId(new Long(1));

		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
		when(bookingRepository.save(any())).thenReturn(expectedBooking);

		// Create a BookingRequest with test values
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCustomerName(CUSTOMER_NAME);
		bookingRequest.setPhoneNumber(PHONE_NUMBER);
		bookingRequest.setTicketId(new Long("1"));

		// Act
		Booking bookingResult = bookingService.bookTicket(bookingRequest);

		// Assert result and expected Booking object
		assertNotNull(bookingResult);
		assertEquals(expectedBooking.getTicket(), bookingResult.getTicket());

		// Assert logic available ticket if success
		assertEquals(expectedBooking.getTicket().getAvailableSeat(), availableTicket - 1);

		// Verify that save method is called
		verify(ticketRepository, times(1)).save(any());
		verify(bookingRepository, times(1)).save(any());
	}

	@Test
	void bookTicket_Valid_Unavailable() {
		// Arrange
		Integer availableTicket = 0;
		Ticket ticket = new Ticket();
		ticket.setAvailableSeat(availableTicket);
		ticket.setId(new Long(1));
		ticket.setPrice(new BigDecimal("1000"));
		ticket.setTicketType("VIP");
		
		Concert concert = new Concert();
		concert.setConcertDate(new Date());
		concert.setName("Concert nam test");
		concert.setLocation("Concert location test");
		ticket.setConcert(concert);

		Booking expectedBooking = new Booking();
		expectedBooking.setPhoneNumber(PHONE_NUMBER);
		expectedBooking.setCustomerName(CUSTOMER_NAME);
		expectedBooking.setTicket(ticket);
		expectedBooking.setId(new Long(1));

		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

		// Create a BookingRequest with test values
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCustomerName(CUSTOMER_NAME);
		bookingRequest.setPhoneNumber(PHONE_NUMBER);
		bookingRequest.setTicketId(new Long("1"));

		// Act
		TicketsNotAvailableException thrown = assertThrows(TicketsNotAvailableException.class,
				() -> bookingService.bookTicket(bookingRequest), "Expected bookTicket() to throw, but it didn't");

		assertTrue(thrown.getMessage().equals("Tickets not available for concert " + ticket.getConcert().getName()
				+ " with ticket ID: " + bookingRequest.getTicketId()));

		// Verify that save method is called
		verify(ticketRepository, times(0)).save(any());
		verify(bookingRepository, times(0)).save(any());
	}

	@Test
	void bookTicket_Invalid() {
		// Arrange
		Integer availableTicket = 100;
		Ticket ticket = new Ticket();
		ticket.setAvailableSeat(availableTicket);
		ticket.setId(new Long(1));
		ticket.setPrice(new BigDecimal("1000"));
		ticket.setTicketType("VIP");

		Booking expectedBooking = new Booking();
		expectedBooking.setPhoneNumber(PHONE_NUMBER);
		expectedBooking.setCustomerName(CUSTOMER_NAME);
		expectedBooking.setTicket(ticket);
		expectedBooking.setId(new Long(1));

		when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

		// Create a BookingRequest with test values
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCustomerName(CUSTOMER_NAME);
		bookingRequest.setPhoneNumber(PHONE_NUMBER);
		bookingRequest.setTicketId(new Long("1"));

		// Act
		ConcertNotFoundException thrown = assertThrows(ConcertNotFoundException.class,
				() -> bookingService.bookTicket(bookingRequest), "Expected bookTicket() to throw, but it didn't");

		assertTrue(thrown.getMessage().equals("Ticket not found with ticket ID: " + bookingRequest.getTicketId()));

		// Verify that save method is called
		verify(ticketRepository, times(0)).save(any());
		verify(bookingRepository, times(0)).save(any());
	}

}
