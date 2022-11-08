package com.ninjaone.backendinterviewproject.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjaone.backendinterviewproject.model.Services;

public interface ServiceRepository extends JpaRepository<Services, Long>{

	
}
