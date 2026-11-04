# franchise-api

API reactiva para gestionar franquicias, sus sucursales y los productos de cada sucursal.

**Stack:** Java 21 · Spring Boot 3.5 · Spring WebFlux (endpoints funcionales) · R2DBC · MySQL 8 · Docker · OpenAPI

## Modelo

```
Franquicia (nombre, estado)
 └── Sucursal (nombre, estado)
      └── Producto (nombre, stock)
```

Las franquicias y sucursales tienen estado `ACTIVE` / `INACTIVE` (borrado lógico). Los productos se eliminan físicamente.

## Arquitectura (hexagonal)

```
com.franchise
├── api                         # Entrada HTTP
│   ├── router                  # RouterFunction (definición de rutas)
│   ├── handler                 # Handlers funcionales (request -> response)
│   └── exception               # Manejo global de errores
├── application                 # Casos de uso
│   ├── model                   # DTOs generados desde el contrato OpenAPI
│   ├── mapper                  # DTO <-> entidad de dominio
│   └── service                 # Orquestación de la lógica
├── domain                      # Núcleo
│   ├── entity                  # Franchise, Branch, Product, TopStockProduct
│   ├── port                    # Interfaces de persistencia
│   ├── service                 # Reglas de negocio
│   └── exception               # NotFoundException
└── infrastructure              # Salida
    ├── adapter                 # Fila de BD -> entidad
    └── repository/mysql        # Implementación de los puertos con DatabaseClient
        └── queries             # SQL de cada tabla
```

Los DTOs de `application/model` se generan con `openapi-generator` a partir de `src/main/resources/static/openapi/franchise-api.yaml` cada vez que se compila (`mvn compile`).

## Ejecutar en local

### Requisitos

- Docker
- Java 21 y Maven 3.9 (solo para la opción 2)

### Opción 1: todo con Docker

```bash
docker compose up --build
```

### Opción 2: MySQL en Docker y la app con Maven

```bash
docker compose up -d mysql
mvn spring-boot:run
```

Al arrancar se crea el esquema y se cargan datos de prueba (`src/main/resources/schema.sql`).

- API: http://localhost:8080/api/v1
- Swagger UI: http://localhost:8080/api/v1/swagger-ui.html
- Health: http://localhost:8080/api/v1/actuator/health

### Variables de entorno

Opcionalmente se puede crear un archivo `.env` en la raíz con estas variables. Docker Compose lo lee y la app también lo carga al correr con Maven o desde el IDE. Si no existe, se usan los valores por defecto.

| Variable           | Default              |
|--------------------|----------------------|
| `SERVER_PORT`      | `8080`               |
| `DB_HOST`          | `localhost`          |
| `DB_PORT`          | `3306`               |
| `DB_NAME`          | `franchise`          |
| `DB_USER`          | `franchise`          |
| `DB_PASSWORD`      | `franchise`          |
| `DB_ROOT_PASSWORD` | `root` (solo Docker) |

### Datos de prueba

| Franquicia        | Estado   | Sucursales                                          |
|-------------------|----------|-----------------------------------------------------|
| 1 - Cosechas      | ACTIVE   | El Poblado, Laureles, Belén (sin productos)         |
| 2 - El Rancherito | ACTIVE   | Las Palmas, Centro (inactiva)                       |
| 3 - Arturo Calle  | INACTIVE | C.C. Santafé (inactiva), C.C. Oviedo (inactiva)     |

## Endpoints

Base: `http://localhost:8080/api/v1`

### Franquicias

| Método | Ruta                                 | Descripción                                          |
|--------|--------------------------------------|------------------------------------------------------|
| GET    | `/franchises`                        | Lista las franquicias                                |
| GET    | `/franchises/{id}`                   | Consulta una franquicia                              |
| POST   | `/franchises`                        | Crea una franquicia                                  |
| PUT    | `/franchises/{id}`                   | Actualiza el nombre                                  |
| DELETE | `/franchises/{id}`                   | Cambia el estado entre `ACTIVE` e `INACTIVE`         |
| GET    | `/franchises/{id}/top-stock-products`| Producto con más stock en cada sucursal activa       |

### Sucursales

| Método | Ruta                        | Descripción                         |
|--------|-----------------------------|-------------------------------------|
| POST   | `/franchises/{id}/branches` | Agrega una sucursal a la franquicia |
| PUT    | `/branches/{id}`            | Actualiza el nombre                 |

### Productos

| Método | Ruta                      | Descripción                       |
|--------|---------------------------|-----------------------------------|
| POST   | `/branches/{id}/products` | Agrega un producto a la sucursal  |
| PUT    | `/products/{id}`          | Actualiza el nombre               |
| PATCH  | `/products/{id}/stock`    | Actualiza el stock                |
| DELETE | `/products/{id}`          | Elimina el producto               |

### Ejemplos

```bash
# Crear una sucursal
curl -X POST http://localhost:8080/api/v1/franchises/1/branches \
  -H "Content-Type: application/json" \
  -d '{"name": "Envigado"}'

# Modificar el stock de un producto
curl -X PATCH http://localhost:8080/api/v1/products/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock": 80}'

# Producto con más stock por sucursal
curl http://localhost:8080/api/v1/franchises/1/top-stock-products
```

Respuesta de `top-stock-products` para Cosechas:

```json
[
  { "branchId": 1, "branchName": "El Poblado", "productId": 1, "productName": "Jugo de Mango", "stock": 120 },
  { "branchId": 2, "branchName": "Laureles", "productId": 5, "productName": "Jugo de Mango", "stock": 60 },
  { "branchId": 2, "branchName": "Laureles", "productId": 6, "productName": "Batido de Banano", "stock": 60 }
]
```

Si hay empate en una sucursal se devuelven todos los productos empatados. Las sucursales sin productos o inactivas no aparecen.

### Errores

Todas las respuestas de error tienen la forma:

```json
{ "error": 404, "message": "Franchise not found: 99", "debugMessage": "NotFoundException" }
```

| Código | Cuándo                                                     |
|--------|------------------------------------------------------------|
| 400    | Id inválido, body vacío o campos que no cumplen el contrato |
| 404    | La franquicia, sucursal o producto no existe               |
| 409    | Ya existe un registro con ese nombre                       |

## Base de datos en la nube (AWS RDS con Terraform)

La base de datos se puede provisionar en AWS como infraestructura como código. La definición está en `infra/terraform/` y crea:

- Una instancia **RDS MySQL 8.4** (`db.t4g.micro`, 20 GB gp3 cifrado, Single-AZ).
- Un **security group** que solo permite conexiones al puerto 3306 desde la IP pública de quien ejecuta Terraform.
- Una contraseña aleatoria para el usuario de la base de datos.

### Requisitos

- Una cuenta de AWS y la [AWS CLI](https://aws.amazon.com/cli/) configurada (`aws configure`).
- [Terraform](https://developer.hashicorp.com/terraform/install) 1.6 o superior.

### Crear la base de datos

```bash
cd infra/terraform
terraform init
terraform apply
```

Tarda entre 5 y 10 minutos. Al terminar muestra el host de la base de datos:

```
db_host = "franchise-db.xxxxxxxx.us-east-1.rds.amazonaws.com"
```

La contraseña se consulta con:

```bash
terraform output -raw db_password
```

### Conectar la app a RDS

Poner en el `.env` de la raíz los valores que entrega Terraform:

```
DB_HOST=<db_host>
DB_USER=franchise
DB_PASSWORD=<db_password>
```

Al arrancar, la app crea el esquema y carga los datos de prueba en RDS igual que en local.

Si la IP pública cambia, se vuelve a ejecutar `terraform apply` para actualizar la regla del security group. También se pueden indicar otras IPs con la variable `allowed_cidrs`:

```bash
terraform apply -var='allowed_cidrs=["203.0.113.10/32"]'
```

### Eliminar la base de datos

```bash
terraform destroy
```

El estado de Terraform (`terraform.tfstate`) contiene la contraseña, por eso queda fuera del repositorio.

## Postman

En `postman-collection/` están la colección y el environment:

1. En Postman, **Import** y arrastrar los dos archivos.
2. Seleccionar el environment **DEV** (`franchise-api = http://localhost:8080`).

## Tests

```bash
mvn test        # no necesita MySQL ni Docker
```

| Capa          | Qué se prueba                                                           | Herramientas                  |
|---------------|-------------------------------------------------------------------------|-------------------------------|
| `domain`      | Reglas de los servicios: búsqueda, cambio de estado, errores 404        | JUnit 5, Mockito, StepVerifier |
| `application` | Orquestación: no se crea una sucursal o producto si el padre no existe  | JUnit 5, Mockito, StepVerifier |
| `api`         | Códigos HTTP de cada endpoint (201, 204, 400, 404, 409)                 | WebTestClient                 |

El test `ServiceRunnerTests` levanta la aplicación completa con H2 en memoria y verifica el health check.
