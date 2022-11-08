package com.ninjaone.backendinterviewproject.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.Devices;

public interface DeviceService {

	Long saveOrUpdate(Devices device) throws EntityAlreadyAddedException, NotFoundException;
	
	Devices getDeviceById(final Long id) throws NotFoundException;
	
	Page<Devices> getAllDevices(final Pageable pageable);
	
	void deleteDeviceById(final Long id) throws NotFoundException;
	
	void updateService(final Long deviceId, final Long serviceId) throws NotFoundException, EntityAlreadyAddedException;
	
	BigDecimal calculateDeviceCost(final Long deviceId) throws NotFoundException;
}
