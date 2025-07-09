# CRUD Backend con Spring Boot, PostgreSQL y Docker Compose

Este proyecto implementa un servicio RESTful de backend para gestionar **Personas** y **Países**, utilizando Spring Boot, Spring Data JPA y PostgreSQL. La aplicación está completamente dockerizada para facilitar su despliegue y ejecución en cualquier entorno.

## Características

* **API RESTful**: Endpoints para operaciones CRUD en entidades Persona y País.
* **Base de Datos Relacional**: PostgreSQL como base de datos persistente.
* **Dockerización**: Aplicación y base de datos en contenedores Docker, orquestados con Docker Compose.
* **Datos Iniciales**: Precarga automática con datos de ejemplo.
* **Validación de Datos**: Spring Validation para asegurar la integridad.
* **Lombok**: Para reducir el código repetitivo.

## Tecnologías Utilizadas

* **Java**: 17
* **Spring Boot**: 3.4.7
* **Spring Data JPA**
* **PostgreSQL**
* **Docker** y **Docker Compose**
* **Lombok**

## Requisitos Previos

* Java Development Kit (JDK) 17+
* Apache Maven 3.6.0+
* Docker Desktop (con Docker Engine y Compose)

## Primeros Pasos

### Clonar el Repositorio

```bash
git clone https://github.com/Gleisk78/crud-backend-api-SpringBoot-Postgre.git
cd crud-backend-api-SpringBoot-Postgre
```

### Construir y Ejecutar Contenedores

Desde la raíz del proyecto:

```bash
docker-compose up --build
```

Esto construye la imagen del backend y ejecuta PostgreSQL. La base de datos se inicializa con `sql/init.sql`.

### Verificar Estado

```bash
docker ps
```

## Endpoints de la API

Base URL: `http://localhost:8080/api/v1/`

### Entidad País `/paises`

#### `GET /paises`

* Lista todos los países.

#### `GET /paises/{id}`

* Devuelve un país según su ID.

#### `POST /paises`

* Crea un nuevo país. Requiere:

```json
{
  "nombre": "Brasil"
}
```

#### `PUT /paises/{id}`

* Actualiza un país existente.

#### `DELETE /paises/{id}`

* Elimina un país por ID.

### Entidad Persona `/personas`

#### `GET /personas`

* Lista todas las personas.

#### `GET /personas/{id}`

* Devuelve una persona según su ID.

#### `POST /personas`

* Crea una nueva persona:

```json
{
  "nombre": "Ana Lopez",
  "edad": 28,
  "idPais": 3
}
```

#### `PUT /personas/{id}`

* Actualiza una persona existente.

#### `DELETE /personas/{id}`

* Elimina una persona por ID.

## Estructura del Proyecto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/Gleisk78/CRUD_backend/
│   │   │   ├── CrudBackendApplication.java
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
├── sql/
│   └── init.sql
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.
