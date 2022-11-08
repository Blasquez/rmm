package com.ninjaone.backendinterviewproject.controller;

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
import com.ninjaone.backendinterviewproject.model.Services;
import com.ninjaone.backendinterviewproject.service.ServicesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/services")
@Tag(name = "service", description = "Service Management")
public class ServiceController {

	@Autowired
	private ServicesService service;
	
	@Operation(summary = "Create a new service", tags = "service")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created"),
	                       @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> postService(@Valid @RequestBody final Services service) throws EntityAlreadyAddedException, URISyntaxException, NotFoundException{
        Long id = this.service.saveOrUpdate(service);
		URI uri = new URI("/services/" + id);
		return ResponseEntity.created(uri).build(); 
    }
	
	@Operation(summary = "Get the service by ID", tags = "service")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
						   @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@GetMapping(value = "{serviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Services> getServiceById(@PathVariable(name = "serviceId",required = true) final Long id) throws NotFoundException{
		return ResponseEntity.ok(service.getServiceById(id));
	}
	
	@Operation(summary = "Get all services", tags = "service")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
						   @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<Services>> getServices( @RequestParam(required = false, defaultValue = "0") final Integer page,
			                                          @RequestParam(required = false, defaultValue = "10") final Integer size){
		
		final Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(service.getAllServices(pageable));
	}
	
	@Operation(summary = "Update a service by ID", tags = "service")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content"),
			               @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
			               @ApiResponse(responseCode = "409", description = "Conflict", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
						   @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@PutMapping(value = "{serviceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> putService(@PathVariable(name = "serviceId",required = true) final Long id,
			                              @Valid @RequestBody final Services service) throws NotFoundException, EntityAlreadyAddedException{
		service.setId(id);
		this.service.saveOrUpdate(service);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Delete a service by ID", tags = "service")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content"),
			               @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
						   @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
	                       @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})})
	@DeleteMapping(value = "{serviceId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteServiceById(@PathVariable(name = "serviceId",required = true) final Long id) throws NotFoundException{
		service.deleteServiceById(id);
		return ResponseEntity.noContent().build();
	}
}
