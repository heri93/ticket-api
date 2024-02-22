package com.edts.ticketapi.service;

import java.util.List;

import com.edts.ticketapi.model.Concert;
import com.edts.ticketapi.model.request.SearchConcertRequest;

public interface ConcertService {

	List<Concert> searchConcerts(SearchConcertRequest searchConcert);

}
