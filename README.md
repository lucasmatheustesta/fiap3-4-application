# Fast Food Order Management System

*   Bruno do Amor Divino da Paixão
*   Lucas Matheus Testa
*   Rodrigo de Lima Amora de Freitas

It was the first project made with Spring Boot by both of us

This project is a backend application for a **Fast Food Order Management System**, developed as part of the **SOAT Tech Challenge (Phase 1)**. The system is designed to optimize the operations of a fast-food restaurant expanding its services by implementing a self-service order and management platform.

The application implements core backend functionalities, following **Hexagonal Architecture** principles, to manage customer orders, monitor order status, and provide an administrative interface for product and order management.

## Features

### Customer-Facing
*   **Order Creation**: Customers can create orders by selecting items from predefined categories (e.g., burgers, sides, drinks, desserts).
*   **Order Status Tracking**: Real-time updates on the status of orders through four stages:
    *   Received
    *   In Preparation
    *   Ready
    *   Finalized
*   **Payment Integration**: Fake checkout implementation using QRCode (Mercado Pago).

### Administrative
*   **Customer Management**: Ability to manage registered customers and run promotional campaigns.
*   **Product Management**: Add, edit, and delete products with details such as name, category, price, and description.
*   **Order Monitoring**: Track all orders and their waiting times.

## Technical Stack

*   **Backend Framework**: Developed with Java Sprint Boot.
*   **Database**: Configurable database for storing orders, products, and customers.
*   **API Documentation**: Swagger documentation provided for seamless API interaction.
*   **Containerization**: Dockerfile and `docker-compose.yml` for easy deployment and setup.
*   **Event-Driven Architecture**: Designed with Event Storming and Domain-Driven Design (DDD) principles.

## Prerequisites

*   **Docker**: Ensure Docker and Docker Compose are installed on your system.
*   **Java** (if required for local builds outside Docker).
*   **Database Access**: (MySQL/PostgreSQL as configured).

## API Documentation

**Swagger:**

http://localhost:6060/swagger-ui

**Redoc:**

http://localhost:6060/redoc.html

Into directory `postman`, there is a collection to call the endpoints by Postman.

## Getting Started

1.  Clone the repository:

   git clone https://github.com/lucasmatheustesta/fiap
   cd fiap

2. Run the command:
   ```bash
   docker-compose up -d --build
   ```

3. See the credentials in docker-compose file:
   ```bash
   db: port 5432
   application: port 6060
   swagger: /swagger-ui/index.html
   ```

You can rum the Shell script docker_build_and_run.sh to up the containers of Docker.

## Kubernetes Deployment

We've choose to use Kubernetes for its capability to scale when the application needs.

### Services

*   App
    *   Type: NodePort because it can be accessed externally
*   db
    *   Type: ClusterIP because it can be accessed internally

### Deployments

*   App
*   db (Postgres)

### HPA (Horizontal Pod Autoscaler)

If any deployment uses 50% of CPU in any deployment (app, db) it can scale up from 1 to 5 replicas

### ConfigMap

It store the port that the app application uses (default: 6060)

### Secrets

It stores the credentials for the database

### PVC (Persistent Volume Claim)

Space used for the database to store its information without risk of loss in case any pod dies.

### Running Kubernetes

Execute the script "run_kubernetes.sh"
