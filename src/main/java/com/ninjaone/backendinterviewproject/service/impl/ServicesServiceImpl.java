package com.ninjaone.backendinterviewproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.database.ServiceRepository;
import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.Services;
import com.ninjaone.backendinterviewproject.service.ServicesService;

@Service
public class ServicesServiceImpl implements ServicesService{

	@Autowired
	private ServiceRepository repository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Override
	public Long saveOrUpdate(Services service) throws EntityAlreadyAddedException,NotFoundException {
		try {
			repository.save(service);
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyAddedException("There is a service with name: " + service.getName() + ".");
		}
		return service.getId();
	}

	@Override
	public Services getServiceById(Long id) throws NotFoundException {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("There isn't a service for id " + id));
	}

	@Override
	public Page<Services> getAllServices(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void deleteServiceById(Long id) throws NotFoundException {
		Services service = repository.findById(id).orElseThrow(() -> new NotFoundException("There isn't a service for id " + id));
		service.getDevices().stream().forEach(device -> {
			device.getServices().remove(service);
			deviceRepository.save(device);
		});
		repository.delete(service);
		
	}

	@Override
	public List<Services> getAllServicesById(List<Long> ids) {
		return repository.findAllById(ids);
	}

}
