package com.harmonia.store.service;

import com.harmonia.store.model.Customer;
import com.harmonia.store.model.CustomerStatus;
import com.harmonia.store.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Cacheable(value = "customers", key = "'all'")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Cacheable(value = "customers", key = "#id")
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Cacheable(value = "customers", key = "'email_' + #email")
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmailIgnoreCase(email);
    }

    @CacheEvict(value = "customers", allEntries = true)
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @CacheEvict(value = "customers", key = "#customer.id")
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @CacheEvict(value = "customers", allEntries = true)
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    public List<Customer> getCustomersByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    public List<Customer> getCustomersByLoyaltyPoints(Integer minPoints) {
        return customerRepository.findByLoyaltyPointsGreaterThanEqual(minPoints);
    }

    @Transactional
    public void addPurchaseToCustomer(Long customerId, BigDecimal amount) {
        Optional<Customer> optional = customerRepository.findById(customerId);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            customer.addPurchase(amount);
            customerRepository.save(customer);
        }
    }

    @Transactional
    public void addLoyaltyPoints(Long customerId, Integer points) {
        Optional<Customer> optional = customerRepository.findById(customerId);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
            customerRepository.save(customer);
        }
    }

    @Transactional
    public void updateCustomerStatus(Long customerId, CustomerStatus status) {
        Optional<Customer> optional = customerRepository.findById(customerId);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            customer.setStatus(status);
            customerRepository.save(customer);
        }
    }
} 