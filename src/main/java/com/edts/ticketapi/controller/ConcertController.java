package com.edts.ticketapi.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edts.ticketapi.model.Concert;
import com.edts.ticketapi.model.request.SearchConcertRequest;
import com.edts.ticketapi.service.ConcertService;

@RestController
@RequestMapping("/concerts")
@Validated
public class ConcertController {

	private ConcertService concertService;

	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	@PostMapping("/search")
	public 	ResponseEntity<List<Concert>> searchConcerts(
			@RequestBody @NotNull(message = "searchConcert cannot be null") SearchConcertRequest searchConcert) {
		return new ResponseEntity<>(concertService.searchConcerts(searchConcert),  HttpStatus.OK);
	}
}
