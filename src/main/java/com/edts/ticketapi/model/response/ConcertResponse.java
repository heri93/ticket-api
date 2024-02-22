package com.edts.ticketapi.model.response;

import java.util.Date;
import java.util.List;

public class ConcertResponse {

	private Long id;
	private String name;
	private String location;
	private Date date;
	private List<TicketResponse> tickets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<TicketResponse> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketResponse> tickets) {
		this.tickets = tickets;
	}

}
