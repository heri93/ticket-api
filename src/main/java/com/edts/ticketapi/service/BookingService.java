package com.edts.ticketapi.service;

import com.edts.ticketapi.model.Booking;
import com.edts.ticketapi.model.request.BookingRequest;

public interface BookingService {

	Booking bookTicket(BookingRequest bookingRequest);

}
