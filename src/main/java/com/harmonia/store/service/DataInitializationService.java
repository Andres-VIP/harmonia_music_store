package com.harmonia.store.service;

import com.harmonia.store.model.*;
import com.harmonia.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private InstrumentRepository instrumentRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Only populate if there are no existing data
        if (categoryRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Create categories
        Category guitars = createCategory("Guitars", "String instruments");
        Category pianos = createCategory("Pianos", "Keyboard instruments");
        Category percussion = createCategory("Percussion", "Percussion instruments");
        Category winds = createCategory("Wind Instruments", "Wind instruments");
        Category basses = createCategory("Basses", "Bass instruments");
        
        // Create instruments
        createInstruments(guitars, pianos, percussion, winds, basses);
        
        // Create customers
        createCustomers();
        
        // Create reviews
        createReviews();
        
        System.out.println("Database successfully populated with sample data!");
    }

    private Category createCategory(String name, String description) {
        Category category = new Category(name, description);
        return categoryRepository.save(category);
    }

    private void createInstruments(Category guitars, Category pianos, Category percussion, 
                                 Category winds, Category basses) {
        
        // Guitars
        createInstrument("Fender Stratocaster", "Fender", new BigDecimal("1299.99"), 
                        InstrumentType.GUITAR, Condition.NEW, 15, 
                        "Classic electric guitar with versatile sound", guitars);
        
        createInstrument("Gibson Les Paul", "Gibson", new BigDecimal("2499.99"), 
                        InstrumentType.GUITAR, Condition.NEW, 8, 
                        "Electric guitar with warm tone and exceptional sustain", guitars);
        
        createInstrument("Taylor 214ce", "Taylor", new BigDecimal("899.99"), 
                        InstrumentType.GUITAR, Condition.NEW, 12, 
                        "Acoustic guitar with integrated electronics", guitars);
        
        createInstrument("Yamaha C40", "Yamaha", new BigDecimal("199.99"), 
                        InstrumentType.GUITAR, Condition.NEW, 20, 
                        "Classical guitar perfect for beginners", guitars);
        
        // Pianos
        createInstrument("Roland FP-30X", "Roland", new BigDecimal("699.99"), 
                        InstrumentType.PIANO, Condition.NEW, 10, 
                        "Digital piano with 88 keys and realistic sound", pianos);
        
        createInstrument("Yamaha P-45", "Yamaha", new BigDecimal("499.99"), 
                        InstrumentType.PIANO, Condition.NEW, 8, 
                        "Compact digital piano with PHA-4 keys", pianos);
        
        createInstrument("Kawai ES110", "Kawai", new BigDecimal("799.99"), 
                        InstrumentType.PIANO, Condition.EXCELLENT, 6, 
                        "Digital piano with realistic hammer action", pianos);
        
        // Drums
        createInstrument("Pearl Export", "Pearl", new BigDecimal("599.99"), 
                        InstrumentType.DRUMS, Condition.NEW, 5, 
                        "Acoustic drum kit for beginners", percussion);
        
        createInstrument("Alesis Nitro Mesh", "Alesis", new BigDecimal("399.99"), 
                        InstrumentType.DRUMS, Condition.NEW, 8, 
                        "Electronic drum kit with mesh pads", percussion);
        
        // Wind instruments
        createInstrument("Yamaha YAS-280", "Yamaha", new BigDecimal("899.99"), 
                        InstrumentType.SAXOPHONE, Condition.NEW, 4, 
                        "Alto saxophone for students", winds);
        
        createInstrument("Bach TR300", "Bach", new BigDecimal("1299.99"), 
                        InstrumentType.TRUMPET, Condition.NEW, 6, 
                        "Professional trumpet with bright sound", winds);
        
        createInstrument("Yamaha YFL-222", "Yamaha", new BigDecimal("699.99"), 
                        InstrumentType.FLUTE, Condition.NEW, 7, 
                        "Student transverse flute", winds);
        
        // Vintage and collection instruments
        createInstrument("1959 Gibson Les Paul", "Gibson", new BigDecimal("45000.00"), 
                        InstrumentType.GUITAR, Condition.EXCELLENT, 1, 
                        "Vintage 1959 guitar, collector's piece", guitars);
        
        createInstrument("Fender Telecaster 52 Reissue", "Fender", new BigDecimal("1899.99"), 
                        InstrumentType.GUITAR, Condition.NEW, 3, 
                        "Reissue of the legendary 1952 Telecaster", guitars);
        
        createInstrument("Steinway Model D", "Steinway", new BigDecimal("85000.00"), 
                        InstrumentType.PIANO, Condition.EXCELLENT, 1, 
                        "Concert grand piano", pianos);
    }

    private Instrument createInstrument(String name, String brand, BigDecimal price, 
                                InstrumentType type, Condition condition, int stock, 
                                String description, Category category) {
        Instrument instrument = new Instrument();
        instrument.setName(name);
        instrument.setBrand(brand);
        instrument.setPrice(price);
        instrument.setType(type);
        instrument.setCondition(condition);
        instrument.setDescription(description);
        instrument.setCategory(category);
        instrument.setStockQuantity(stock);
        return instrumentRepository.save(instrument);
    }

    private void createCustomers() {
        List<Customer> customers = List.of(
            new Customer("John", "Smith", "john.smith@email.com"),
            new Customer("Mary", "Johnson", "mary.johnson@email.com"),
            new Customer("Carl", "Williams", "carl.williams@email.com"),
            new Customer("Anna", "Brown", "anna.brown@email.com"),
            new Customer("Louis", "Jones", "louis.jones@email.com"),
            new Customer("Carmen", "Garcia", "carmen.garcia@email.com"),
            new Customer("Michael", "Miller", "michael.miller@email.com")
        );
        
        customers.forEach(customer -> {
            customer.setPhone("+34" + (600000000 + (int)(Math.random() * 99999999)));
            customer.setAddress("Street " + (int)(Math.random() * 100) + ", New York");
            customer.setTotalPurchases(new BigDecimal(Math.random() * 5000));
            customer.setLoyaltyPoints((int)(Math.random() * 1000));
            customerRepository.save(customer);
        });
    }

    private void createReviews() {
        List<Instrument> instruments = instrumentRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        
        String[] comments = {
            "Excellent instrument, very good sound quality",
            "Perfect for beginners, very easy to play",
            "Professional sound, ideal for recordings",
            "Very good quality-price ratio",
            "Collector's item, unique sound",
            "Excellent for live concerts",
            "Very versatile, adapts to any style",
            "Exceptional build quality",
            "Warm and rich in harmonics",
            "Perfect for recording studios"
        };
        
        for (int i = 0; i < 50; i++) {
            Instrument instrument = instruments.get((int)(Math.random() * instruments.size()));
            Customer customer = customers.get((int)(Math.random() * customers.size()));
            
            Review review = new Review(
                comments[(int)(Math.random() * comments.length)],
                (int)(Math.random() * 5) + 1,
                customer.getFullName(),
                instrument
            );
            review.setAuthorEmail(customer.getEmail());
            reviewRepository.save(review);
        }
    }
} 