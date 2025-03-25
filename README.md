# demo-bff-pattern
This project is an example of the BFF (Backend for Frontend) pattern in a microservice context.

## Solution Example
![App Screenshot](https://github.com/rafaelnarbutis/demo-bff/blob/main/BFF_ARCH.png)

## Available Services

### 1. RabbitMQ
- **Description**: Messaging service used for asynchronous communication.
- **Access URL**: 
  - Management interface: [http://localhost:15672](http://localhost:15672)
  - Default credentials:
    - **Username**: guest
    - **Password**: guest

---

### 2. Order Service
- **Description**: Service responsible for managing orders.
- **Base URL**: [http://localhost:8080](http://localhost:8080)
- **Available Routes**:
  - **POST /orders**: Creates a new order.
    - **Body** (JSON):
      ```json
      {
        "client_id": "string",
        "total": "string",
        "items_id": ["string"]
      }
      ```
  - **GET /orders/:id**: Returns the details of a specific order.
  - **GET /orders**: Returns all orders.

---

### 3. Customer Service
- **Description**: Service responsible for managing customers.
- **Base URL**: [http://localhost:8082](http://localhost:8082)
- **Available Routes**:
  - **GET /customers/{client_id}**: Returns the details of a specific customer.
    - **Parameter**:
      - `client_id`: The ID of the customer.

---

### 4. Store BFF
- **Description**: Backend for Frontend that integrates customer and order services.
- **Base URL**: [http://localhost:8085](http://localhost:8085)
- **Available Routes**:
  - **GraphQL Endpoint**: [http://localhost:8085/graphql](http://localhost:8085/graphql)
    - **Mutations**:
      - `createOrder(costumerId: String, itemsId: [String]): Boolean`: Creates a new order.
    - **Queries**:
      - `costumerById(id: String): Costumer`: Returns the details of a customer.
      - `items: [Item]`: Returns the list of available items.
      - `itemsById(id: String): Item`: Returns the details of a specific item.

---

### 5. Mongo Express
- **Description**: Web interface for managing the MongoDB database.
- **Access URL**: [http://localhost:8081](http://localhost:8081)
- **Default credentials**:
  - **Username**: root
  - **Password**: root

---

### 6. MongoDB
- **Description**: Database used by the order service.
- **Connection URL**: `mongodb://root:root@localhost:27017`

---

### 7. PostgreSQL (Customer Database)
- **Description**: Database used by the customer service.
- **Connection URL**: `jdbc:postgresql://localhost:5432/customer_manager`
- **Default credentials**:
  - **Username**: postgres
  - **Password**: 1234

---

## How to Run

1. Make sure Docker and Docker Compose are installed.
2. In the .docker directory of the project, run:
   ```sh
   docker-compose up --build