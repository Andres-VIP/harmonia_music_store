package com.harmonia.store.repository;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.model.InstrumentType;
import com.harmonia.store.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    
    // Basic searches
    List<Instrument> findByNameContainingIgnoreCase(String name);
    List<Instrument> findByBrandContainingIgnoreCase(String brand);
    List<Instrument> findByType(InstrumentType type);
    List<Instrument> findByCondition(Condition condition);
    // Stock queries
    List<Instrument> findByStockQuantityGreaterThan(Integer quantity);
    List<Instrument> findByStockQuantityLessThanEqual(Integer quantity);
    
    // Price searches
    List<Instrument> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Instrument> findByPriceLessThanEqual(BigDecimal maxPrice);
    List<Instrument> findByPriceGreaterThanEqual(BigDecimal minPrice);
    
    // Combined searches
    List<Instrument> findByTypeAndPriceBetween(InstrumentType type, BigDecimal minPrice, BigDecimal maxPrice);
    List<Instrument> findByBrandAndType(String brand, InstrumentType type);
    
    // Basic statistical methods
    long countByType(InstrumentType type);
}
