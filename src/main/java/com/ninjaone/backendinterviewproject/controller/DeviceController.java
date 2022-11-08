package com.ninjaone.backendinterviewproject.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.Devices;
import com.ninjaone.backendinterviewproject.service.DeviceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/devices")
@Tag(name = "device", description = "Device Management")
@SecurityRequirement(name = "basicAuth")
public class DeviceController {

	@Autowired
	private DeviceService service;
	
	@Operation(summary = "Create a new device", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created"),
	                       @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> postDevice(@Valid @RequestBody final Devices device) throws EntityAlreadyAddedException, NotFoundException, URISyntaxException{
        Long id = service.saveOrUpdate(device);
		URI uri = new URI("/devices/" + id);
		return ResponseEntity.created(uri).build(); 
    }
	
	@Operation(summary = "Get the device by ID", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
						   @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@GetMapping(value = "{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Devices> getDeviceById(@PathVariable(name = "deviceId",required = true) final Long id) throws NotFoundException{
		return ResponseEntity.ok(service.getDeviceById(id));
	}
	
	@Operation(summary = "Update a device by ID", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content"),
			               @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
			               @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
						   @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@PutMapping(value = "{deviceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> putDevice(@PathVariable(name = "deviceId",required = true) final Long id,
			                              @Valid @RequestBody final Devices device) throws NotFoundException, EntityAlreadyAddedException{
		device.setId(id);
		service.saveOrUpdate(device);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Get all devices", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
						   @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<Devices>> getDevices( @RequestParam(required = false, defaultValue = "0") final Integer page,
			                                        @RequestParam(required = false, defaultValue = "10") final Integer size){
		
		final Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(service.getAllDevices(pageable));
	}
	
	@Operation(summary = "Delete a device by ID", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content"),
			               @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
						   @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@DeleteMapping(value = "{deviceId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteDeviceById(@PathVariable(name = "deviceId",required = true) final Long id) throws NotFoundException{
		service.deleteDeviceById(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Add/Delete a service for the device", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content"),
			               @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
			               @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@PutMapping(value = "{deviceId}/services/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> putDeviceService(@PathVariable(name = "deviceId",required = true) final Long deviceId,
    		                                     @PathVariable(name = "serviceId",required = true) final Long serviceId ) throws NotFoundException, EntityAlreadyAddedException{
        service.updateService(deviceId, serviceId);
		return ResponseEntity.noContent().build();
    }
	
	@Operation(summary = "Get the service cost by device ID", tags = "device")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
						   @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@GetMapping(value = "{deviceId}/cost", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BigDecimal> getDeviceCostById(@PathVariable(name = "deviceId",required = true) final Long id) throws NotFoundException{
		return ResponseEntity.ok(service.calculateDeviceCost(id));
	}
	
}
