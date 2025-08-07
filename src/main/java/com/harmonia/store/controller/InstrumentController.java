package com.harmonia.store.controller;

import com.harmonia.store.model.Instrument;
import com.harmonia.store.model.InstrumentType;
import com.harmonia.store.service.InstrumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/v1/instruments")
@Tag(name = "Instruments", description = "API for managing musical instruments")
@CrossOrigin(origins = "*")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @GetMapping("/")
    @Operation(summary = "Get all instruments",
               description = "Returns a list of all available instruments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of instruments obtained successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Instrument>> getAllInstruments() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return ResponseEntity.ok(instruments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get instrument by ID",
            description = "Returns a specific instrument by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Instrument found"),
        @ApiResponse(responseCode = "404", description = "Instrument not found")
    })
    public ResponseEntity<Instrument> getInstrumentById(
            @Parameter(description = "Instrument ID") @PathVariable Long id) {
        return instrumentService.getInstrumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @Operation(summary = "Create new instrument",
               description = "Creates a new instrument in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Instrument created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<Instrument> createInstrument(
            @Parameter(description = "Instrument data") @Valid @RequestBody Instrument instrument) {
        Instrument created = instrumentService.addInstrument(instrument);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update instrument",
               description = "Updates an existing instrument")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Instrument updated"),
        @ApiResponse(responseCode = "404", description = "Instrument not found"),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<Instrument> updateInstrument(
            @Parameter(description = "Instrument ID") @PathVariable Long id,
            @Parameter(description = "Updated instrument data") @Valid @RequestBody Instrument instrument) {
        instrument.setId(id);
        Instrument updated = instrumentService.updateInstrument(instrument);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete instrument",
               description = "Deletes an instrument from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Instrument deleted"),
        @ApiResponse(responseCode = "404", description = "Instrument not found")
    })
    public ResponseEntity<Void> deleteInstrument(
            @Parameter(description = "Instrument ID") @PathVariable Long id) {
        instrumentService.deleteInstrument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search instruments by name",
               description = "Searches for instruments containing the specified name")
    public ResponseEntity<List<Instrument>> searchInstruments(
            @Parameter(description = "Name to search") @RequestParam String name) {
        List<Instrument> results = instrumentService.searchByName(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get instruments by type",
               description = "Returns instruments of a specific type")
    public ResponseEntity<List<Instrument>> getInstrumentsByType(
            @Parameter(description = "Instrument type") @PathVariable InstrumentType type) {
        List<Instrument> results = instrumentService.getInstrumentsByType(type);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Search instruments by price range",
               description = "Searches for instruments within a price range")
    public ResponseEntity<List<Instrument>> getInstrumentsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        List<Instrument> results = instrumentService.getInstrumentsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/in-stock")
    @Operation(summary = "Get instruments in stock",
               description = "Returns instruments that have stock available")
    public ResponseEntity<List<Instrument>> getInStockInstruments() {
        List<Instrument> results = instrumentService.getInStockInstruments();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get instruments out of stock",
               description = "Returns instruments that have no stock")
    public ResponseEntity<List<Instrument>> getOutOfStockInstruments() {
        List<Instrument> results = instrumentService.getOutOfStockInstruments();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated instruments",
               description = "Returns instruments with pagination support")
    public ResponseEntity<Page<Instrument>> getInstrumentsPaginated(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Instrument> results = instrumentService.getInstrumentsPaginated(pageable);
        return ResponseEntity.ok(results);
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update instrument stock",
               description = "Updates the stock quantity of an instrument")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock updated"),
        @ApiResponse(responseCode = "404", description = "Instrument not found")
    })
    public ResponseEntity<Void> updateStock(
            @Parameter(description = "Instrument ID") @PathVariable Long id,
            @Parameter(description = "New stock quantity") @RequestParam int quantity) {
        instrumentService.updateStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/add-stock")
    @Operation(summary = "Add stock to instrument",
               description = "Adds additional quantity to existing stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock added"),
        @ApiResponse(responseCode = "404", description = "Instrument not found")
    })
    public ResponseEntity<Void> addStock(
            @Parameter(description = "Instrument ID") @PathVariable Long id,
            @Parameter(description = "Quantity to add") @RequestParam int quantity) {
        instrumentService.addStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count/type/{type}")
    @Operation(summary = "Count instruments by type",
               description = "Returns the number of instruments of a specific type")
    public ResponseEntity<Long> getCountByType(
            @Parameter(description = "Instrument type") @PathVariable InstrumentType type) {
        long count = instrumentService.getCountByType(type);
        return ResponseEntity.ok(count);
    }
}
