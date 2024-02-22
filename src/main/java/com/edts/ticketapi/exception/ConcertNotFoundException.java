package com.edts.ticketapi.exception;

public class ConcertNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8935940285688673136L;

	public ConcertNotFoundException(String message) {
		super(message);
	}
}
