# Spring Boot Application

This project is a Spring Boot application. Below are instructions for building and running the application using Docker.

## Prerequisites

- Docker installed on your machine.

## Building the Docker Image

To build the Docker image, navigate to the root directory of the project and run:

```bash
docker build -t avito .
```

To run the Docker image run this command:
``` bash
docker run -p 8080:8080 avito
```
