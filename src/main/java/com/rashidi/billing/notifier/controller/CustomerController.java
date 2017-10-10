package com.rashidi.billing.notifier.controller;

import com.rashidi.billing.notifier.exception.ConflictException;
import com.rashidi.billing.notifier.exception.DuplicateException;
import com.rashidi.billing.notifier.exception.NotFoundException;
import com.rashidi.billing.notifier.model.Customer;
import com.rashidi.billing.notifier.service.CustomerService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Rest controller for using Customer.
 *
 * @author Mina Rashidi
 */

@RestController
@RequestMapping("/customers")
@Api(value = "customers", description = "Customer Api", tags = {"customer-api"}, consumes = "application/json")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;


    @ApiOperation(value = "Return all customers.", notes = "Returns list of all customers.", nickname = "findAll",
            httpMethod = "GET", protocols = "http", response = Customer.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Server Internal Error")})
    @GetMapping
    public ResponseEntity findAll() {
        try {
            List<Customer> result = customerService.listAllCustomers();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.warn("Problem in findAll customers:", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Return a customer by ID.", notes = "Return a customer by id.", nickname = "findById",
            httpMethod = "GET", protocols = "http", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Customer with specified id does not exist in the system."),
            @ApiResponse(code = 500, message = "Server Internal Error")})
    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@ApiParam(value = "Id of customer", required = true)
                                   @PathVariable("id") Long id) {
        try {
            Customer result = customerService.getCustomerById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            LOGGER.warn("Customer not found!", nfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            LOGGER.warn("Problem in findById for Customer!", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/email")
    @ApiOperation(value = "Return a customer by email.", notes = "Return a customer by email.",
            nickname = "findByEmail", httpMethod = "GET", protocols = "http", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Customer with specified email does not exist in the system."),
            @ApiResponse(code = 500, message = "Server Internal Error")})
    public ResponseEntity findByEmail(@ApiParam(value = "Email of customer", required = true)
                                      @RequestParam("email") String email) {
        try {
            Customer result = customerService.getCustomerByEmail(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            LOGGER.warn("Customer with email:{} not found!", email, nfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            LOGGER.warn("Problem in findByEmail for customer:", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Create a new customer.",
            notes = "Create a new customer and return the URL of the new resource in the Location header.",
            nickname = "add", httpMethod = "POST", protocols = "http")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer created in the system."),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 409, message = "Customer with specified email already exist in the system."),
            @ApiResponse(code = 500, message = "Server Internal Error")})
    public ResponseEntity add(@RequestBody Customer customer) throws IOException {
        try {
            Customer result = customerService.saveCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).location(new URI("/customers/" + result.getId())).body(null);
        } catch (DuplicateException de) {
            LOGGER.warn("Could not add duplicate customer:", de);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception ex) {
            LOGGER.warn("Problem while adding customer:", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update an existing customer.", notes = "Update an existing customer.",
            nickname = "update", httpMethod = "PUT", protocols = "http")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated."),
            @ApiResponse(code = 404, message = "Customer does not exist in the system."),
            @ApiResponse(code = 500, message = "Server Internal Error")})
    public ResponseEntity update(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer storedCustomer = customerService.getCustomerById(id);
            storedCustomer.setFirstName(customer.getFirstName());
            storedCustomer.setLastName(customer.getLastName());
            storedCustomer.setEmail(customer.getEmail());
            storedCustomer.setMobile(customer.getMobile());
            customerService.saveCustomer(storedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body("Customer updated successfully");
        } catch (NotFoundException nfe) {
            LOGGER.warn("Customer not found!", nfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (DuplicateException de) {
            LOGGER.warn("Could not update an existing Customer!", de);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception ex) {
            LOGGER.warn("Problem in updating Customer!", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete a customer by ID", notes = "remove a customer by ID", nickname = "removeById",
            httpMethod = "DELETE", protocols = "http")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer deleted."),
            @ApiResponse(code = 404, message = "Customer with specified id does not exist in the system."),
            @ApiResponse(code = 409, message = "Customer could not be removed, it's used in system!"),
            @ApiResponse(code = 500, message = "Server Internal Error")})
    public ResponseEntity remove(@ApiParam(value = "Id of customer", required = true)
                                 @PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
        } catch (EmptyResultDataAccessException nfe) {
            LOGGER.warn("Customer not found!", nfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ConflictException ce) {
            LOGGER.warn("Customer could not be removed, it's used in system!", ce);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception ex) {
            LOGGER.warn("Problem in removing Customer!", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
