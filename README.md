# Promact_Customer_Success Application

This repository contains a Dockerized application with a Spring Boot backend, React frontend, and PostgreSQL database. The application is named Promact_Customer_Success.

## Project Structure

```
Promact_Customer_Success/
│
├── Backend/
│   ├── Dockerfile
│   └── (other backend source files)
│
├── Frontend/
│   ├── Dockerfile
│   └── (other frontend source files)
│
├── docker-compose.yaml
└── README.md
```

- **Backend**: Contains the Spring Boot backend application.
- **Frontend**: Contains the React frontend application.
- **docker-compose.yaml**: Defines the services and configuration for Docker Compose.
- **README.md**: Documentation file you are currently reading.

## Backend

### Dockerfile

```Dockerfile
FROM maven:3.9.5 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests=true
ADD target/Promact_Customer_Success-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8081
```

This Dockerfile uses the Maven image to build the Spring Boot application and sets up the necessary configurations.

## Frontend

### Dockerfile

```Dockerfile
FROM node:18
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
```

This Dockerfile uses the Node.js image to build and run the React frontend application.

## docker-compose.yaml

```yaml
services:
 
  backend:
    build:
      context: ./Backend
      dockerfile: Dockerfile
    ports:
      - "8081:8081"
    depends_on:
      - pgdb

  frontend:
    build:
      context: ./Frontend
      dockerfile: Dockerfile
    ports:
      - "3000:3000"

  pgdb:
    image: postgres:latest
    container_name: pgdb
    environment:
      POSTGRES_DB: Customer_Success
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: aditya
    expose:
      - 5432
    ports:
      - "5432:5432"
```

This `docker-compose.yaml` file defines three services: `backend`, `frontend`, and `pgdb` (PostgreSQL). It sets up dependencies between services and exposes the necessary ports.

## Usage

To build and run the Docker containers, use the following command:

```bash
docker-compose up --build -d
```
This command will build the images and start the containers in detached mode.

## Result
![docker compose up --build -d](https://github.com/adityajha28/CSP_Devops/assets/127980079/3796161b-502d-4435-9a54-385eb78f0303)
![Running](https://github.com/adityajha28/CSP_Devops/assets/127980079/a84d08b6-b90b-44ed-8a42-8f76eee663b2)
![Docker-Desktop](https://github.com/adityajha28/CSP_Devops/assets/127980079/dfd7dc15-8308-4388-af8a-38a5884da688)

## Running Application
![Screenshot (83)](https://github.com/adityajha28/git-assignment/assets/127980079/8606c3a3-b521-461a-9466-7eddbf93e442)
![Screenshot (81)](https://github.com/adityajha28/git-assignment/assets/127980079/181f7bd3-1cd8-4698-a134-f70c33773060)

The application can be accessed at:
- Backend: [http://localhost:8081](http://localhost:8081)
- Frontend: [http://localhost:3000](http://localhost:3000)


