package com.edts.ticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edts.ticketapi.model.Concert;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long>{

}
