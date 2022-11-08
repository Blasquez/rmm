package com.ninjaone.backendinterviewproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.BackendInterviewProjectException;
import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.Devices;
import com.ninjaone.backendinterviewproject.model.ErrorResponse;
import com.ninjaone.backendinterviewproject.service.impl.DeviceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

	@Mock
    private DeviceRepository repository;

    @InjectMocks
    private DeviceServiceImpl service;
    
    private Devices sampleDevice;
    
    @BeforeEach
    void setup(){
    	sampleDevice = new Devices(1L, "System Name Test", "Test", null);
    }

    @Test
    @Order(1)
    @DisplayName("Happy Path - The device was created successfully")
    void saveDeviceSuccessfully() {
    	 when(repository.save(ArgumentMatchers.any(Devices.class))).thenReturn(sampleDevice);
         Long id = service.saveOrUpdate(new Devices(null, "System Name Test", "Test", null));
    	 assertEquals(1L, id);
    }
    
    @Test
    @Order(2)
    @DisplayName("409 - DeviceServiceImpl.saveOrUpdate(device) returns 409. Device has already added.")
    void saveDevice_thenEntityAlreadyAddedException() {
    	 when(repository.save(ArgumentMatchers.any(Devices.class))).thenThrow(DataIntegrityViolationException.class);
    	 EntityAlreadyAddedException exception = Assertions.assertThrows(EntityAlreadyAddedException.class, () -> {
    		 	service.saveOrUpdate(new Devices(null, "System Name Test", "Test", null));
    	 	});
    	 Assertions.assertEquals("There is a device with System Name: System Name Test and Type: Test.", exception.getMessage());
		 Assertions.assertEquals(409, exception.getStatus().value());
    }
    
    @Test
    @Order(3)
    @DisplayName("Happy Path - Retries the device filtered by ID")
    void getDeviceById() {
    	 when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(sampleDevice));
         Devices device = service.getDeviceById(1L);
    	 assertEquals(1L, device.getId());
    	 assertEquals("System Name Test", device.getSystemName());
    }
    
    @Test
    @Order(4)
    @DisplayName("404 - DeviceServiceImpl.getDeviceById(1) returns 404. Device not found.")
    void getDeviceById_thenNotFoundExcepetion() {
    	 when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
    	 NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> { 
    		 	service.getDeviceById(1L); 
    		 });
        	 
    	 Assertions.assertEquals("There is not a device for id 1", exception.getMessage());
		 Assertions.assertEquals(404, exception.getStatus().value());
    }
}
