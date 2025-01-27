# Spring Boot Application

This application is a test job designed as a REST API that enables businesses to create tenders for various services. Other users or businesses can then submit their favorable terms to win the tender.

## Prerequisites

- Docker installed on your machine.

## Steps to Run the Application

1. Clone the repository:

```bash
git clone https://github.com/NumeroQuadro/TenderService.git
```

2. Navigate into the cloned repository:

```bash
cd your-repo-name
```

3. Build the Docker image:

```bash
docker build -t my-app .
```

4. Run the Docker image:

```bash
docker run -p 8080:8080 my-app
```

## Endpoints
- To see the available endpoints, open the following link after running the Docker image: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
