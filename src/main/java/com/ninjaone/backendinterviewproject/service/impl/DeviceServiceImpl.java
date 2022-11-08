package com.ninjaone.backendinterviewproject.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.Devices;
import com.ninjaone.backendinterviewproject.model.Services;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import com.ninjaone.backendinterviewproject.service.ServicesService;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository repository;
	
	@Autowired
	private ServicesService servicesService;
	
	@Override
	public Page<Devices> getAllDevices(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Devices getDeviceById(Long id) throws NotFoundException {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("There is not a device for id " + id));
	}

	@Override
	public Long saveOrUpdate(Devices device) throws EntityAlreadyAddedException, NotFoundException {
		try {
			if(device.getId() != null) {
				this.getDeviceById(device.getId());
			}
			device = repository.save(device);
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyAddedException("There is a device with System Name: " + device.getSystemName() + " and Type: " + device.getType() + ".");
		}
		return device.getId();
	}

	@Override
	public void deleteDeviceById(Long id) throws NotFoundException {
		Devices device = repository.findById(id).orElseThrow(() -> new NotFoundException("There isn't a device for id " + id));
		repository.delete(device);
	}

	@Override
	public void updateService(Long deviceId, Long serviceId) throws NotFoundException, EntityAlreadyAddedException {
		Devices device = this.getDeviceById(deviceId);
		Services service = servicesService.getServiceById(serviceId);
		
		List<Services> services = device.getServices();
		if(services != null) {
			if(services.contains(service)) {
				services.remove(service);
			}else {
				services.add(service);
			}
		}else {
			services = new ArrayList<>(1);
			services.add(service);
		}
		
		device.setServices(services);
		this.saveOrUpdate(device);
	}

	@Override
	public BigDecimal calculateDeviceCost(Long deviceId) throws NotFoundException {
		Devices device = this.getDeviceById(deviceId);
		List<Services> services = device.getServices();
		return services.stream().map(service -> service.getPrice()) .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
