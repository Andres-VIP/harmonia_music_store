package com.harmonia.store.service;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.model.InstrumentType;
import com.harmonia.store.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;
    
    // Simple in-memory cache for frequent searches
    private final Map<String, List<Instrument>> searchCache = new HashMap<>();

    /**
     * Get all instruments with cache
     */
    @Cacheable(value = "instruments", key = "'all'")
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    /**
     * Get instrument by ID with cache
     */
    @Cacheable(value = "instruments", key = "#id")
    public Optional<Instrument> getInstrumentById(Long id) {
        return instrumentRepository.findById(id);
    }

    /**
     * Add instrument with cache invalidation
     */
    @CacheEvict(value = "instruments", allEntries = true)
    public Instrument addInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    /**
     * Update instrument with cache invalidation
     */
    @CacheEvict(value = "instruments", allEntries = true)
    public Instrument updateInstrument(Instrument instrument) {
        if (!instrumentRepository.existsById(instrument.getId())) {
            throw new RuntimeException("Instrument not found");
        }
        Instrument updated = instrumentRepository.save(instrument);
        searchCache.clear();
        return updated;
    }

    /**
     * Delete instrument with cache invalidation
     */
    @CacheEvict(value = "instruments", allEntries = true)
    public void deleteInstrument(Long id) {
        instrumentRepository.deleteById(id);
        searchCache.clear();
    }

    /**
     * Simple search by name
     */
    @Cacheable(value = "instruments", key = "'search_' + #name")
    public List<Instrument> searchByName(String name) {
        return instrumentRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Search by instrument type
     */
    @Cacheable(value = "instruments", key = "'type_' + #type")
    public List<Instrument> getInstrumentsByType(InstrumentType type) {
        return instrumentRepository.findByType(type);
    }

    /**
     * Search by price range
     */
    @Cacheable(value = "instruments", key = "'price_' + #minPrice + '_' + #maxPrice")
    public List<Instrument> getInstrumentsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return instrumentRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Get instruments in stock
     */
    public List<Instrument> getInStockInstruments() {
        return instrumentRepository.findByStockQuantityGreaterThan(0);
    }

    /**
     * Get instruments out of stock
     */
    public List<Instrument> getOutOfStockInstruments() {
        return instrumentRepository.findByStockQuantityLessThanEqual(0);
    }

    /**
     * Get instruments paginated
     */
    public Page<Instrument> getInstrumentsPaginated(Pageable pageable) {
        return instrumentRepository.findAll(pageable);
    }

    /**
     * Update instrument stock
     */
    public void updateStock(Long instrumentId, int newQuantity) {
        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new RuntimeException("Instrument not found"));
        instrument.setStockQuantity(newQuantity);
        instrumentRepository.save(instrument);
    }

    /**
     * Add stock to instrument
     */
    public void addStock(Long instrumentId, int quantityToAdd) {
        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new RuntimeException("Instrument not found"));
        instrument.addStock(quantityToAdd);
        instrumentRepository.save(instrument);
    }

    /**
     * Get count by type
     */
    public long getCountByType(InstrumentType type) {
        return instrumentRepository.countByType(type);
    }

    /**
     * Clear search cache
     */
    private void clearSearchCache() {
        searchCache.clear();
    }
}
