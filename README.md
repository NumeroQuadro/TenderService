# Spring Boot Application

Avito is a big company where users not only sell/buy goods and services, but also provide assistance to big businesses and enterprises.
This application is a test job. AvitoTestTask is a Rest API that will allow a business to create a tender for any services. And users/other businesses will offer their favourable terms to get this tender.
## Prerequisites

- Docker installed on your machine.

## Steps to run application
1. Clone the repository

```bash
git clone https://github.com/NumeroQuadro/AvitoTestTask.git
```

2. Navigate into the cloned repository

```bash
cd your-repo-name
```
3. Build the Docker image

```bash
docker build -t avito .
```

4. Run the Docker image:

``` bash
docker run -p 8080:8080 avito
```

## Endpoints
- to see endpoints - proceed here (after running docker image): http://localhost:8080/swagger-ui/index.html