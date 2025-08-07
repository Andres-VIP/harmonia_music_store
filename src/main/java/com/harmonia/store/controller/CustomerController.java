package com.harmonia.store.controller;

import com.harmonia.store.model.Customer;
import com.harmonia.store.model.CustomerStatus;
import com.harmonia.store.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description = "API for managing customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    @Operation(summary = "Get all customers",
            description = "Returns a list of all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of customers obtained successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID",
            description = "Returns a specific customer by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Customer> getCustomerById(
            @Parameter(description = "Customer ID") @PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get customer by email",
            description = "Returns a specific customer by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Customer> getCustomerByEmail(
            @Parameter(description = "Customer email") @PathVariable String email) {
        return customerService.getCustomerByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @Operation(summary = "Create new customer",
            description = "Creates a new customer in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<Customer> createCustomer(
            @Parameter(description = "Customer data") @Valid @RequestBody Customer customer) {
        Customer created = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer",
            description = "Updates an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<Customer> updateCustomer(
            @Parameter(description = "Customer ID") @PathVariable Long id,
            @Parameter(description = "Updated customer data") @Valid @RequestBody Customer customer) {
        customer.setId(id);
        Customer updated = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer",
            description = "Deletes a customer from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "Customer ID") @PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search customers by name",
            description = "Searches for customers containing the specified name")
    public ResponseEntity<List<Customer>> searchCustomers(
            @Parameter(description = "Name to search") @RequestParam String name) {
        List<Customer> results = customerService.searchCustomersByName(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get customers by status",
            description = "Returns customers with a specific status")
    public ResponseEntity<List<Customer>> getCustomersByStatus(
            @Parameter(description = "Customer status") @PathVariable CustomerStatus status) {
        List<Customer> results = customerService.getCustomersByStatus(status);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/loyalty/{minPoints}")
    @Operation(summary = "Get customers by loyalty points",
            description = "Returns customers with minimum loyalty points")
    public ResponseEntity<List<Customer>> getCustomersByLoyaltyPoints(
            @Parameter(description = "Minimum loyalty points") @PathVariable int minPoints) {
        List<Customer> results = customerService.getCustomersByLoyaltyPoints(minPoints);
        return ResponseEntity.ok(results);
    }

    @PatchMapping("/{id}/purchase")
    @Operation(summary = "Add purchase to customer",
            description = "Adds a purchase to customer history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase added"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Void> addPurchase(
            @Parameter(description = "Customer ID") @PathVariable Long id,
            @Parameter(description = "Purchase amount") @RequestParam BigDecimal amount) {
        customerService.addPurchaseToCustomer(id, amount);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/loyalty")
    @Operation(summary = "Add loyalty points to customer",
            description = "Adds loyalty points to customer account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loyalty points added"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Void> addLoyaltyPoints(
            @Parameter(description = "Customer ID") @PathVariable Long id,
            @Parameter(description = "Points to add") @RequestParam int points) {
        customerService.addLoyaltyPoints(id, points);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update customer status",
            description = "Updates customer status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "Customer ID") @PathVariable Long id,
            @Parameter(description = "New status") @RequestParam CustomerStatus status) {
        customerService.updateCustomerStatus(id, status);
        return ResponseEntity.ok().build();
    }
} 