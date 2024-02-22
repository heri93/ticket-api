package com.edts.ticketapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edts.ticketapi.model.Concert;
import com.edts.ticketapi.model.Ticket;
import com.edts.ticketapi.model.request.SearchConcertRequest;
import com.edts.ticketapi.repository.ConcertRepository;
import com.edts.ticketapi.service.ConcertService;

@Service
public class ConcertServiceImpl implements ConcertService {

	private ConcertRepository concertRepository;

	@Autowired
	public void setConcertRepository(ConcertRepository concertRepository) {
		this.concertRepository = concertRepository;
	}

	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	/**
	 * Searches for concerts based on the provided search criteria.
	 *
	 * @param searchConcert The search criteria for concerts.
	 * @return A list of concerts that match the search criteria.
	 */
	@Override
	public List<Concert> searchConcerts(
			@NotBlank(message = "searchConcert cannot be blank") SearchConcertRequest searchConcert) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Concert> criteriaQuery = criteriaBuilder.createQuery(Concert.class);
		Root<Concert> root = criteriaQuery.from(Concert.class);
		Join<Concert, Ticket> ticketJoin = root.join("tickets", JoinType.LEFT);
		List<Predicate> predicates = new ArrayList<>();

		// Parameters of search
		if (searchConcert.getLocation() != null && !searchConcert.getLocation().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("location"), "%" +searchConcert.getLocation()+ "%"));
		}

		if (searchConcert.getDate() != null) {
			predicates.add(criteriaBuilder.equal(root.get("concertDate"), searchConcert.getDate()));
		}
		
		if (searchConcert.getName() != null && !searchConcert.getName().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("name"),  "%" +searchConcert.getName()+ "%"));
		}
		
		// Add predicate to filter availableSeat > 0
		Predicate finalPredicate = criteriaBuilder.greaterThan(ticketJoin.get("availableSeat"), 0);
		
		// Add filter data from parameter values into criteria builder using 'OR'  
		if(!predicates.isEmpty()) {
			Predicate parametersPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
			finalPredicate = criteriaBuilder.and(finalPredicate, parametersPredicate);
			
		}

		// Build the final query
		criteriaQuery.select(root).distinct(true).where(finalPredicate);

		// Execute the query
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
