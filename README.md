# ECOMM Product Service

Spring Boot microservice responsible for the product catalog in the ECOMM e-commerce platform — products, categories, pricing, images, attributes, and catalog search/filtering.

## How it fits in the platform

The platform is split into independently deployable Spring Boot services, registered with and discovered through Eureka:

| Service | Responsibility |
|---|---|
| [Auth Service](https://github.com/sarangKaliyath/ECOMM_Auth_ServiceApplication) | Identity, tokens, sessions |
| [Profile Service](https://github.com/sarangKaliyath/ECOMM_Profile_Service_Application) | User profile data (created reactively on signup) |
| **Product Service** [*(this repo)*](https://github.com/sarangKaliyath/ECOMM_Product_ServiceApplication) | Product catalog |
| [Cart Service](https://github.com/sarangKaliyath/ECOMM_Cart_Service_Application) | Shopping cart |
| [Ordering Service](https://github.com/sarangKaliyath/ECOMM_Ordering_Service_Application) | Order lifecycle |
| [Payment Service](https://github.com/sarangKaliyath/ECOMM_Payment_Gateway_Service_Application) | Payment processing |
| [Email Service](https://github.com/sarangKaliyath/ECOMM_Email_Service_Application) | Transactional email delivery |
| [Service Discovery](https://github.com/sarangKaliyath/ECOMM_Service_Discovery_Application) | Eureka registry |

## Tech stack

- Java 17
- Spring Boot 3.3.3 (Spring Data JPA pinned to 3.5.7)
- Spring Cloud Netflix Eureka Client 4.1.4 — service registration/discovery
- MySQL (`mysql-connector-j`)
- Lombok
- JUnit 5 / Mockito / Spring Boot Test (via `spring-boot-starter-test`)

## Project structure

```
src/main/java/com/ecomm/ecomm_product_serviceapplication/
├── config/        CORS configuration
├── controller/     REST controllers + global exception handler
├── dto/           Request/response DTOs
├── exceptions/    Custom exceptions
├── mapper/        Entity <-> DTO mapping
├── model/         JPA entities and enums
├── repository/    Spring Data JPA repositories
└── service/       Business logic
```

## API

### Products (`/product`)

| Method | Path | Description |
|---|---|---|
| GET | `/product` | List all active products |
| GET | `/product/list` | Paginated, filterable product listing — query params: `category_id`, `min_price`, `max_price`, `rating`, `in_stock`, `on_sale`, plus standard `page`/`size`/`sort` |
| GET | `/product/{id}` | Get a product by id (404 if not found) |
| POST | `/product` | Create a product |
| PUT | `/product/{id}` | Replace a product *(currently unimplemented — stubbed)* |
| DELETE | `/product/{id}` | Delete a product *(currently a no-op — soft/hard delete was removed, see Known limitations)* |

### Categories (`/category`)

| Method | Path | Description |
|---|---|---|
| GET | `/category` | List all categories |
| GET | `/category/{categoryId}` | Get a category by id |
| POST | `/category` | Create a category (409 if the name already exists) |

## Product model

A `Product` belongs to a `Category` and owns collections of regional `ProductPrice`s, `ProductImage`s, and key/value `ProductAttribute`s. Key fields:

- `name`, `slug` (unique), `brand`, `shortDescription`, `description`
- `primaryImageUrl`, `images` (ordered list)
- `defaultPrice`, `defaultCurrency` (`CurrencyCode`: INR/USD/EUR/GBP)
- `productStatus` (`DRAFT`/`ACTIVE`/`INACTIVE`/`ARCHIVED`), `inventoryStatus` (`IN_STOCK`/`OUT_OF_STOCK`/`PRE_ORDER`)
- `onSale`, `discountRate` (percentage)
- `averageRating`, `reviewCount`
- `sellerId` (references Auth/User Service)
- `category` (many-to-one)
- `prices` (regional/multi-currency pricing, scaffolded but not yet exposed via the API)

All entities extend `BaseModel` (`id`, `createdAt`, `updatedAt`, `state`).

## Configuration

Config lives in `src/main/resources/application.properties`:

| Property | Purpose |
|---|---|
| `server.port` | Defaults to `8082`, overridable via `SERVER_PORT` env var |
| `spring.datasource.*` | MySQL connection (schema `shop_easy`) |
| `spring.jpa.hibernate.ddl-auto` | `update` |
| `eureka.client.service-url.defaultZone` | Eureka registry URL, defaults to `http://localhost:8761/eureka/` |

The datasource credentials in that file are local-dev defaults only — override them via environment/profile-specific config for any non-local environment.

## Running locally

Prerequisites: Java 17, MySQL running locally with a `shop_easy` schema, and (optionally) the Eureka [Service Discovery](https://github.com/sarangKaliyath/ECOMM_Service_Discovery_Application) service running on `8761`.

```bash
./mvnw spring-boot:run
```

The service starts on port `8082` by default and registers itself with Eureka on startup.

## Known limitations

- `PUT /product/{id}` and category update/delete are stubbed and not yet implemented.
- `DELETE /product/{id}` is currently a no-op; soft/hard delete support was removed pending redesign.
- No Bean Validation (`@Valid`) on request DTOs — inputs aren't validated at the framework level yet.
- No Dockerfile/docker-compose yet for containerized local setup.
- Automated tests beyond the Spring context load test are currently commented out and need updating to match the current model/DTO shapes.
