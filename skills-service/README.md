## Overview

Spring Boot REST Application Skills API.

The API is used to manage skills resources.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

### Build the application using Maven

`mvn clean install`

### Run the application

`java -jar target/skills-service-1.0.0-SNAPSHOT.jar`

### Alternative

`mvn clean spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:9999`

Swagger UI can be accessed with the following link:

`http://localhost:9999/swagger-ui.html`

### Exposed Endpoints

All exposed endpoints can be found using the [swagger api documentation](http://localhost:9999/v2/api-docs) or the [swagger-ui page](http://localhost:9999/swagger-ui.html).
