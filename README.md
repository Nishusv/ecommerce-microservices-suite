# E-Commerce Microservices Suite — 4-Day Training

Java 21, Spring Boot, Spring Cloud Gateway, PostgreSQL, Docker Compose, Kubernetes/Helm, GitHub Actions, Prometheus, Grafana and Postman.

## Requirements (Windows 10/11)
Install Docker Desktop (Linux containers), Postman, and for Days 3–4 install Minikube, kubectl and Helm. Java 21 and Maven are optional for local runs because Docker builds the applications.

Check:
```cmd
java -version
javac -version
mvn -version
git --version
wsl --version
docker --version
docker compose version
kubectl version --client
helm version
minikube version
```
Check:
```powershell
docker --version
docker compose version
kubectl version --client
helm version
minikube version
```

## Day 1 — Start everything with Docker Compose
From PowerShell in this folder:
```powershell
docker compose config -q (If it completes without an error, the configuration is valid.)
docker compose up --build -d
docker compose ps
docker compose logs --tail=100 (check if service is not running)
docker compose logs -f api-gateway
```
Wait for startup, then open Postman and send `GET http://localhost:8080/actuator/health`.
- Gateway: `http://localhost:8080`
- Product service direct: `http://localhost:8081` 
- Order service direct: `http://localhost:8082`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000` (demo login `admin` / `admin`)

Stop while retaining data: `docker compose down`
Reset all PostgreSQL data (destructive): `docker compose down -v`

## Day 2 — Postman REST testing
Import `postman/E-Commerce-Microservices.postman_collection.json`. Base URL is `http://localhost:8080`.

Run: health, list products, get product 1, create order, list orders. Product seeds are created on the first startup.

Create product body:
```json
{"name":"USB-C Hub","description":"Seven-port USB-C hub","price":2499.00}
```
Create order body:
```json
{"productId":1,"quantity":2}
```

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/products` | List products |
| GET | `/api/products/{id}` | Get product |
| POST | `/api/products` | Add product |
| GET | `/api/orders` | List orders |
| GET | `/api/orders/{id}` | Get order |
| POST | `/api/orders` | Create order |
| GET | `/actuator/health` | Gateway health |

The Order service calls Product Catalog over REST to validate the product and get its current price. This is a training demo; it does not reserve inventory or process payment.

## Day 3 — Kubernetes and Helm on Minikube
Start a local cluster:
```powershell
minikube start --driver=docker
minikube -p minikube docker-env --shell powershell | Invoke-Expression
```
Build images into Minikube's Docker daemon:
```powershell
docker build -t ecommerce/product-service:1.0.0 ./product-service
docker build -t ecommerce/order-service:1.0.0 ./order-service
docker build -t ecommerce/api-gateway:1.0.0 ./api-gateway
```
Deploy:
```powershell
helm upgrade --install ecommerce ./helm/ecommerce
kubectl get pods -n ecommerce
kubectl get services -n ecommerce
kubectl get pods -n ecommerce -w
```
Wait for pods to be ready, then Ctrl+C. Forward the gateway:
```powershell
kubectl port-forward -n ecommerce service/ecommerce-api-gateway 8080:8080
```
Keep that terminal open. Test `http://localhost:8080` in Postman. If Compose is still running, stop it first to free port 8080.

The Helm chart deploys one PostgreSQL pod and two databases for a local workshop only; it is not a production database architecture.

## Day 4 — CI/CD and monitoring
`.github/workflows/ci.yml` builds/tests each Maven service and builds Docker images on pushes and pull requests. It does not automatically deploy to Kubernetes; deployment is a local trainer step. Push this folder to GitHub and inspect the Actions tab.

Prometheus scrapes Spring Boot Actuator metrics. In Grafana choose Explore → Prometheus and query `up` or `http_server_requests_seconds_count`.

## Four-day teaching sequence
1. Dockerfiles, images, Compose, container networks, PostgreSQL volume.
2. REST endpoints, API Gateway routing, service-to-service calls, Postman.
3. Kubernetes pods/deployments/services, readiness probes, Helm chart.
4. GitHub Actions CI, Actuator metrics, Prometheus and Grafana.

## Troubleshooting
- Database startup can take 30–60 seconds: `docker compose logs postgres`.
- Gateway 503: check `docker compose ps` and `docker compose logs order-service product-service`.
- Port conflict: stop the app using 8080/5432/9090/3000 or change host ports.
- Kubernetes image pull error: ensure images were built after switching Docker to Minikube's daemon.
- To rebuild after code edits: `docker compose up --build -d`.

## Security note
All passwords are workshop-only. Do not use them in production or commit real secrets. Authentication, TLS, payment processing, inventory reservation, database migrations, backups, secret management and production-grade HA are outside this introductory project.
