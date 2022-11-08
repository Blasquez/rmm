package com.ninjaone.backendinterviewproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.Services;

public interface ServicesService {
	
	Long saveOrUpdate(Services service) throws EntityAlreadyAddedException, NotFoundException;
	
	Services getServiceById(final Long id) throws NotFoundException;
	
	Page<Services> getAllServices(final Pageable pageable);
	
	void deleteServiceById(final Long id) throws NotFoundException;
	
	List<Services> getAllServicesById(List<Long> ids);

}
