package com.edts.ticketapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.edts.ticketapi.model.Concert;
import com.edts.ticketapi.model.request.SearchConcertRequest;
import com.edts.ticketapi.service.impl.ConcertServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ConcertServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ConcertServiceImpl concertService;

    @SuppressWarnings("unchecked")
	@Test
    void testSearchConcerts() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Mock the CriteriaBuilder, CriteriaQuery, Root, Join, and TypedQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Concert> criteriaQuery = mock(CriteriaQuery.class);
        Root<Concert> root = mock(Root.class);
        Join<Object, Object> ticketJoin = mock(Join.class);
        TypedQuery<Concert> typedQuery = mock(TypedQuery.class);
        Predicate predicate = mock(Predicate.class);

        Path<Object> path = mock(Path.class);
        // Mock the all object to return the mocked objects
        when(root.get(any(String.class))).thenReturn(path);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Concert.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Concert.class)).thenReturn(root);
        when(root.join("tickets", JoinType.LEFT)).thenReturn(ticketJoin);
        when(ticketJoin.get(any(String.class))).thenReturn(path);
        when(entityManager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
        when(criteriaBuilder.like(any(Expression.class), any(String.class))).thenReturn(predicate);
        when(criteriaBuilder.equal(any(Expression.class), any(Date.class))).thenReturn(predicate);
        when(criteriaBuilder.greaterThan(any(Expression.class), any(Integer.class))).thenReturn(predicate);
        when(criteriaBuilder.or(any(Predicate.class))).thenReturn(predicate);
        when(criteriaBuilder.and(any(Predicate.class),any(Predicate.class))).thenReturn(predicate);
        when(criteriaQuery.select(any(Root.class))).thenReturn(criteriaQuery);
        when(criteriaQuery.distinct(any(Boolean.class))).thenReturn(criteriaQuery);
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        
        // Mock the Concert entity
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setName("Test Concert");
        concert.setLocation("Test Location");
        concert.setConcertDate(new Date());
        List<Concert> expectedConcerts = new ArrayList<>();
        expectedConcerts.add(concert);

        // Mock the result of the query execution
        when(typedQuery.getResultList()).thenReturn(expectedConcerts);

        // Create a SearchConcertRequest with test values
        SearchConcertRequest searchConcertRequest = new SearchConcertRequest();
        searchConcertRequest.setLocation("Test Location");
        searchConcertRequest.setDate(new Date());

        // Call the method to be tested
        List<Concert> resultConcerts = concertService.searchConcerts(searchConcertRequest);

        // Verify that the query was constructed with the correct parameters
        verify(criteriaBuilder).like(any(Expression.class), any(String.class));
        verify(criteriaBuilder).equal(any(Expression.class), any(Date.class));
        verify(criteriaBuilder).greaterThan(any(Expression.class), any(Integer.class));
        verify(criteriaQuery).select(any(Root.class));
        verify(criteriaQuery).distinct(true);
        verify(criteriaQuery).where(any(Predicate.class));
        verify(typedQuery).getResultList();

        // Assert that the result matches the expectedConcerts
        assertEquals(expectedConcerts, resultConcerts);
    }	
}
