package com.harmonia.store.repository;

import com.harmonia.store.model.Customer;
import com.harmonia.store.model.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByEmailIgnoreCase(String email);
    
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    List<Customer> findByStatus(CustomerStatus status);
    
    List<Customer> findByLoyaltyPointsGreaterThanEqual(Integer minPoints);
} 