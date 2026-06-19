# Microservices E-Commerce Backend

A hands-on microservices project built using Spring Boot and Spring Cloud to demonstrate modern distributed system architecture patterns including Service Discovery, API Gateway, Load Balancing, Circuit Breaker, Event-Driven Communication using Kafka, and Dockerized infrastructure.

---

## 🚀 Project Overview

This project simulates an e-commerce backend consisting of multiple independently deployable services communicating through both synchronous and asynchronous mechanisms.

### Architecture

```text
                    ┌─────────────┐
                    │   Client    │
                    └──────┬──────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │   API Gateway   │
                  └──────┬──────────┘
                         │
         ┌───────────────┴───────────────┐
         ▼                               ▼

 ┌─────────────────┐           ┌─────────────────┐
 │ Product Service │           │  Order Service  │
 └────────┬────────┘           └────────┬────────┘
          ▲                              │
          │                              │
          │                              ▼
          │                      Kafka Topic
          │                    (order-created)
          │                              │
          │                              ▼
          │                    ┌─────────────────┐
          │                    │Inventory Service│
          │                    └─────────────────┘
          │
          ▼
    Eureka Server
```

---

# 🛠 Tech Stack

### Backend

* Java 17
* Spring Boot 3.5.3
* Spring Data JPA
* Hibernate
* MySQL

### Spring Cloud

* Eureka Server
* OpenFeign
* Spring Cloud LoadBalancer
* Spring Cloud Gateway
* Resilience4j Circuit Breaker

### Messaging

* Apache Kafka
* Spring Kafka

### Infrastructure

* Docker
* Docker Compose
* Kafka UI

### Build Tool

* Maven

---

# 📦 Services

## Eureka Server

Acts as the Service Registry.

Responsibilities:

* Service Registration
* Service Discovery
* Dynamic Instance Lookup

Dashboard:

```text
http://localhost:8761
```

---

## Product Service

Manages product-related operations.

Features:

* Create Product
* Get Product
* Update Product
* Delete Product

Registered with Eureka.

---

## Order Service

Handles order creation.

Features:

* Create Order
* Product Validation using OpenFeign
* Service Discovery using Eureka
* Client Side Load Balancing
* Circuit Breaker Integration
* Kafka Producer

When an order is successfully created:

```text
OrderCreatedEvent
```

is published to Kafka.

---

## Inventory Service

Kafka Consumer Service.

Responsibilities:

* Listen to OrderCreatedEvent
* Consume messages from Kafka
* Process inventory updates asynchronously

Consumer Group:

```text
inventory-group
```

---

## API Gateway

Single entry point for all requests.

Responsibilities:

* Routing
* Load Balancing
* Service Discovery Integration

Example:

```text
/api/products/**
/api/orders/**
```

---

# 🔥 Kafka Integration

## Why Kafka?

Traditional Synchronous Flow:

```text
Order Service
      |
      ▼
Inventory Service
```

Problem:

If Inventory Service is unavailable, Order Service fails.

---

Event Driven Flow:

```text
Order Service
      |
      ▼
Kafka
      |
      ▼
Inventory Service
```

Benefits:

* Loose Coupling
* Asynchronous Processing
* Scalability
* Reliability
* Fault Tolerance

---

## Kafka Components Used

### Topic

```text
order-created
```

### Producer

Order Service

```java
kafkaTemplate.send("order-created", event);
```

### Consumer

Inventory Service

```java
@KafkaListener(
    topics = "order-created",
    groupId = "inventory-group"
)
```

---

# ⚡ Circuit Breaker

Implemented using Resilience4j.

Purpose:

Prevent cascading failures when Product Service becomes unavailable.

Fallback responses are returned only when Product Service is actually unreachable.

---

# ⚖ Load Balancing

Implemented using:

```text
Spring Cloud LoadBalancer
```

Order Service communicates with Product Service using service names instead of hardcoded URLs.

Example:

```java
@FeignClient(name = "product-service")
```

Requests are automatically distributed among available instances.

---

# 🐳 Docker Setup

Kafka and Kafka UI are containerized using Docker Compose.

Start Services:

```bash
docker compose up -d
```

Stop Services:

```bash
docker compose down
```

Kafka UI:

```text
http://localhost:8088
```

---

# 🚀 Running the Project

## 1. Start Kafka

```bash
docker compose up -d
```

---

## 2. Start Eureka Server

```bash
mvn spring-boot:run
```

---

## 3. Start Product Service

```bash
mvn spring-boot:run
```

---

## 4. Start Order Service

```bash
mvn spring-boot:run
```

---

## 5. Start Inventory Service

```bash
mvn spring-boot:run
```

---

## 6. Start API Gateway

```bash
mvn spring-boot:run
```

---

# 📚 Key Concepts Demonstrated

* Microservices Architecture
* Service Discovery
* API Gateway Pattern
* Client-Side Load Balancing
* Circuit Breaker Pattern
* Event-Driven Architecture
* Kafka Producer & Consumer
* Consumer Groups
* Topic & Partition Concepts
* Asynchronous Communication
* Dockerized Infrastructure

---

# 🎯 Future Enhancements

* Config Server
* Distributed Tracing
* Centralized Logging
* JWT Authentication
* Inventory Database
* Dead Letter Queue (DLQ)
* Kafka Retry Mechanism
* Kubernetes Deployment

---

# 👨‍💻 Author

Deepak Gupta

Java Full Stack Developer

Tech Stack:
Java | Spring Boot | Spring Cloud | Kafka | MySQL | Angular | Microservices
