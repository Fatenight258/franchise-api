# franchise-api

API reactiva para gestionar franquicias, sus sucursales y los productos de cada sucursal.

**Stack:** Java 21 · Spring Boot 3.5 · Spring WebFlux (endpoints funcionales) · R2DBC · MySQL 8 · Docker

## Modelo

```
Franquicia (nombre)
 └── Sucursal (nombre)
      └── Producto (nombre, stock)
```

## Arquitectura (hexagonal)

```
com.franchise
├── api                       # Entrada HTTP
│   ├── router                # RouterFunction (definición de rutas)
│   ├── handler               # Handlers funcionales (request -> response)
│   └── exception             # Manejo global de errores
├── application               # Casos de uso
│   ├── dto                   # Request / response
│   ├── mapper                # MapStruct (DTO <-> dominio)
│   └── usecase               # Orquestación de la lógica
├── domain                    # Núcleo, sin dependencias de framework
│   ├── model                 # Franchise, Branch, Product
│   ├── port                  # Interfaces de persistencia
│   └── exception             # Excepciones de negocio
└── infrastructure            # Salida
    ├── entity                # Tablas R2DBC (@Table)
    ├── repository            # ReactiveCrudRepository
    └── adapter               # Implementación de los puertos
```

## Ejecutar en local

### Opción 1: todo con Docker

```bash
docker compose up --build
```

### Opción 2: MySQL en Docker y la app con Maven

```bash
docker compose up -d mysql
mvn spring-boot:run
```

El esquema (`src/main/resources/schema.sql`) se crea al arrancar.

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health

| Variable      | Default     |
|---------------|-------------|
| `SERVER_PORT` | `8080`      |
| `DB_HOST`     | `localhost` |
| `DB_PORT`     | `3306`      |
| `DB_NAME`     | `franchise` |
| `DB_USER`     | `franchise` |
| `DB_PASSWORD` | `franchise` |

## Tests

```bash
mvn test        # usa H2 en memoria, no necesita MySQL
```

## Endpoints

_Pendiente._