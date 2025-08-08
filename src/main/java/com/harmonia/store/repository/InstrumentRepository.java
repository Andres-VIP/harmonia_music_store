package com.harmonia.store.repository;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.model.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    
    // Basic searches
    List<Instrument> findByNameContainingIgnoreCase(String name);
    List<Instrument> findByType(InstrumentType type);
    
    // Stock queries
    List<Instrument> findByStockQuantityGreaterThan(Integer quantity);
    List<Instrument> findByStockQuantityLessThanEqual(Integer quantity);
    
    // Price searches
    List<Instrument> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Basic statistical methods
    long countByType(InstrumentType type);
}
