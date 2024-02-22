package com.edts.ticketapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edts.ticketapi.model.Booking;
import com.edts.ticketapi.model.request.BookingRequest;
import com.edts.ticketapi.service.BookingService;

@RestController
@RequestMapping("/bookings")
@Validated
public class BookingController {

	private BookingService bookingService;

	@Autowired
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@PostMapping("/book")
	public ResponseEntity<Booking> searchConcerts(
			@RequestBody @Valid BookingRequest bookingRequest) {
		return new ResponseEntity<>(bookingService.bookTicket(bookingRequest),  HttpStatus.OK);
	}
}
