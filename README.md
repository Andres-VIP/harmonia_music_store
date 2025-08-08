# 🎵 Harmonia Music Store API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 📋 Description

**Harmonia Music Store** is a complete REST API for managing a musical instruments store.

## ✨ Main Features

### 🚀 **Basic Functionalities**
**Harmonia Music Store** is a complete management system for a musical instruments store. Think of it as the digital backbone that helps store owners and staff manage everything from inventory to customer relationships.

The application handles the day-to-day operations of a music store, allowing you to keep track of all the instruments in stock, organize them by categories, and manage customer information. When someone wants to find a specific guitar or check if a piano is available, the system provides quick and accurate searches. It also helps maintain customer relationships by tracking their purchase history and loyalty points.

Behind the scenes, the system uses smart caching to make searches faster and ensures all data is properly validated. Whether you're adding new instruments to the catalog, updating stock levels, or helping customers find what they're looking for, this application streamlines the entire process.

### 🎯 **Technical Features**
- **H2 Database**: For development and testing
- **JPA/Hibernate**: Object-relational mapping
- **Spring Data JPA**: Repositories with derived methods
- **API Documentation**: Integrated Swagger UI
- **Test Data**: Automatically populated database
- **Caching**: Redis-based caching for improved performance

## 🛠️ Technologies Used

### **Backend**
- **Java 17** - Programming language
- **Spring Boot 3.2.4** - Main framework
- **Spring Data JPA** - Data persistence
- **Spring Cache** - Caching system with Redis
- **Hibernate** - ORM
- **Bean Validation** - Data validation
- **Lombok** - Code reduction and boilerplate elimination

### **Database**
- **H2 Database** - In-memory database (development)

### **Documentation**
- **OpenAPI 3.0** - API documentation
- **Swagger UI** - Documentation interface

## 🏗️ Project Architecture

```
src/main/java/com/harmonia/store/
├── controller/             # REST Controllers
│   ├── InstrumentController.java
│   ├── CategoryController.java
│   └── CustomerController.java
├── model/                  # JPA Entities
│   ├── Instrument.java
│   ├── Category.java
│   ├── Customer.java
│   ├── Review.java
│   └── Enums/
│       ├── CustomerStatus.java
│       ├── Condition.java
│       └── InstrumentType.java
├── repository/             # Data Repositories
│   ├── InstrumentRepository.java
│   ├── CategoryRepository.java
│   ├── CustomerRepository.java
│   └── ReviewRepository.java
├── service/                # Business Logic
│   ├── InstrumentService.java
│   ├── CategoryService.java
│   ├── CustomerService.java
│   └── DataInitializationService.java
├── config/                 # Configurations
│   ├── CacheConfig.java    # Redis Configuration
│   └── OpenApiConfig.java  # Swagger Configuration
└── HarmoniaApplication.java
```

## 🚀 Installation and Configuration

### **Prerequisites**
- Java 17 or higher
- Maven 3.8+

### **1. Clone the Repository**
```bash
git clone https://github.com/your-username/harmonia-music-store.git
cd harmonia-music-store
```

### **2. Run the Application**
```bash
# Compile and run
mvn spring-boot:run

# Or compile JAR
mvn clean package
java -jar target/store-1.0-SNAPSHOT.jar
```

### **3. Configure Redis (Optional)**
```bash
# Install Redis
docker run -d -p 6379:6379 redis:alpine
```

## 📚 API Endpoints

### **Instruments** (`/api/v1/instruments`)
- `GET /` - Get all instruments
- `GET /{id}` - Get instrument by ID
- `POST /` - Create new instrument
- `PUT /{id}` - Update instrument
- `DELETE /{id}` - Delete instrument
- `GET /search?name={name}` - Search by name
- `GET /type/{type}` - Instruments by type
- `GET /price-range?minPrice={min}&maxPrice={max}` - Search by price range
- `GET /in-stock` - Instruments in stock
- `GET /out-of-stock` - Instruments out of stock
- `GET /paginated?page={page}&size={size}&sort={sort}` - Paginated instruments
- `PATCH /{id}/stock?quantity={qty}` - Update stock
- `PATCH /{id}/add-stock?quantity={qty}` - Add stock
- `GET /count/type/{type}` - Count by type

### **Categories** (`/api/v1/categories`)
- `GET /` - Get all categories
- `GET /{id}` - Get category by ID
- `POST /` - Create new category
- `PUT /{id}` - Update category
- `DELETE /{id}` - Delete category
- `GET /name/{name}` - Get category by name
- `GET /search?name={name}` - Search categories by name

### **Customers** (`/api/v1/customers`)
- `GET /` - Get all customers
- `GET /{id}` - Get customer by ID
- `GET /email/{email}` - Get customer by email
- `POST /` - Create new customer
- `PUT /{id}` - Update customer
- `DELETE /{id}` - Delete customer
- `GET /search?name={name}` - Search customers by name
- `GET /status/{status}` - Get customers by status
- `GET /loyalty/{minPoints}` - Get customers by loyalty points
- `PATCH /{id}/purchase?amount={amount}` - Add purchase to customer
- `PATCH /{id}/loyalty?points={points}` - Add loyalty points
- `PATCH /{id}/status?status={status}` - Update customer status

## 🔍 Usage Examples

### **Create Instrument**
```bash
POST /api/v1/instruments
Content-Type: application/json

{
  "name": "Fender Stratocaster",
  "brand": "Fender",
  "price": 1299.99,
  "type": "GUITAR",
  "condition": "NEW",
  "description": "Classic electric guitar",
  "stockQuantity": 10,
  "category": {
    "id": 1
  }
}
```

### **Search Instruments**
```bash
# Search by name
GET /api/v1/instruments/search?name=fender

# Instruments by type
GET /api/v1/instruments/type/GUITAR

# Search by price range
GET /api/v1/instruments/price-range?minPrice=500&maxPrice=2000
```

### **Get Paginated Instruments**
```bash
GET /api/v1/instruments/paginated?page=0&size=10&sort=name,asc
```

## 📈 Monitoring

### **Spring Boot Actuator**
- **Health Checks**: `/actuator/health`
- **Info**: `/actuator/info`

## 📝 API Documentation

Once the application is running, access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs