# Java Test API

This repository contains a Java Spring Boot test application that provides APIs for managing categories and items using PostgreSQL. The application is containerized using Docker for ease of deployment.

---

# Getting Started

Follow these steps to build and run the application using Docker.

## Prerequisites
Ensure the following are installed on your system:
- [Docker](https://www.docker.com/)
- Docker Compose (optional)
- `curl` for testing (or use an API testing tool like Postman)

---

## Building and Running the Docker Containers

### 1. Clone the repository
```bash
git clone https://github.com/epolancog/test-api.git
cd test-api
```

### 2. Prepare the environment variables

Create a .env file in the root of the repository using .env.example as a base:
```bash
cp .env.example .env
```
Edit the .env file with your desired values, if needed. The default values should work for most cases.

### 3. Build the JAR file

Before building and running the application in Docker, you need to build the JAR file. Make sure you have Java and Maven installed on your system.

Run the following command to build the JAR file:

```bash
mvn clean package
```
This command will run the tests and create the JAR file in the `target/` directory, which will be used by the Docker container.

### 4. Build the Docker images

Use Docker compose to build the two images at the same time - the app itself and the PostgreSQL.

```bash
docker-compose build
```


### 5. Run the application

Use Docker Compose.

**NOTE:** Make sure there are no other servers running on the 8080 and 5432 ports.

```bash
docker-compose up
```

---
# Running Tests

You can run the unit tests and other tests included in the project using Maven. Here’s how:

```bash
mvn test
```


---
# Testing the API

To test the application, you can either use the `curl` command or another third party application like [Postman](https://www.postman.com/).

## Using Curl

### Categories endpoints

#### List All Categories
```bash
curl -X GET http://localhost:8080/categories/
```

#### Create a Category
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"Technology"}' http://localhost:8080/categories/
```

#### Display a specific Category
Where **/1** is a valid `category id`.
```bash
curl -X GET http://localhost:8080/categories/1
```

#### Update an existing Category
Where **/1** is a valid `category id`.
```bash
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Technologies"}' http://localhost:8080/categories/1
```

#### Delete an existing Category
Where **/1** is a valid `category id`.
```bash
curl -X DELETE http://localhost:8080/categories/1
```

---

### Items endpoints

#### List All Item
```bash
curl -X GET http://localhost:8080/items/
```

#### Create an Item
Where **/1** is a valid `Category id`.
```bash
curl -X POST -H "Content-Type: application/json" -d '{
	"name": "iPhone", "category": { "id": 1},  "price": 1999.99}' http://localhost:8080/items/
```
eg. It will create the `iPhone` item under the `Technology` *category* with *price* `$1,999.99`.

#### Display a specific Item
Where **/1** is a valid `item id`.
```bash
curl -X GET http://localhost:8080/items/1
```

#### Update an existing Item
Where **/1** is a valid `item id`.
```bash
curl -X PUT -H "Content-Type: application/json" -d '{"name":"iPhone 13"}' http://localhost:8080/items/1
```

#### Delete an existing Item
Where **/1** is a valid `item id`.
```bash
curl -X DELETE http://localhost:8080/items/1
```

---

## FAQ

### Q: How do I stop the containers?
A: Run the following command:
```bash
docker-compose down
```

### Q: How do I clear all containers, images, and volumes?
A: Use this command to clean up:
```bash
docker system prune -a
```

---

## Pending to add
* Increase Test coverage (Unit, Integration and E2E)
* Add authentication feature

---

## Contributions

Feel free to submit pull requests or issues if you’d like to contribute or have suggestions.
