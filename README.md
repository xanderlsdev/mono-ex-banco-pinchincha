# Exchange Support API

Documentación del proceso de construcción y despliegue del proyecto

## Despliegue

```bash
docker-compose up --build -d
or
docker compose up --build -d
```

## Rutas de acceso
- Lista de Usuarios Fake: [https://gorest.co.in/public/v2/users](https://gorest.co.in/public/v2/users)
- Exchange: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- Exchange Support: [http://localhost:8081/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- RabbitMQ: [http://localhost:15672](http://localhost:15672)
    - Usuario: admin
    - Contraseña: admin

## Descripción
Aplicación de soporte para almacenar datos de cliente que realizan intercambios de monedas.

## Información del Desarrollador
- **Autor**: Xander LS
- **Contacto**: [xanderlsdev@gmail.com](mailto:xanderlsdev@gmail.com)
- **Sitio web**: [https://xanderls.dev/](https://xanderls.dev/)


## Endpoints

### 1. Obtener todos los registros de auditoría de tipo de cambio
**GET /**
- **Descripción**: Obtiene todos los registros de auditoría de tipo de cambio.
- **Respuesta (200 OK)**:
  ```json
  [
    {
      "id": 1,
      "nombre": "Cliente1",
      "montoInicial": 100.0,
      "montoFinal": 98.0,
      "tipoCambio": 0.98,
      "fechaRegistro": "2024-02-27T12:00:00Z"
    }
  ]
  ```

### 2. Crear un nuevo registro de auditoría de tipo de cambio
**POST /**
- **Descripción**: Crea un nuevo registro de auditoría de tipo de cambio.
- **Cuerpo de la solicitud**:
  ```json
  {
    "nombre": "Cliente1",
    "montoInicial": 100.0,
    "montoFinal": 98.0,
    "tipoCambio": 0.98,
    "fechaRegistro": "2024-02-27T12:00:00Z"
  }
  ```
- **Respuesta (201 Created)**:
  ```json
  {
    "id": 2,
    "nombre": "Cliente2",
    "montoInicial": 200.0,
    "montoFinal": 196.0,
    "tipoCambio": 0.98,
    "fechaRegistro": "2024-02-27T12:30:00Z"
  }
  ```

### 3. Obtener un registro de auditoría por ID
**GET /{id}**
- **Descripción**: Obtiene un registro de auditoría de tipo de cambio por ID.
- **Parámetros**:
  - `id` (integer, requerido): ID del registro.
- **Ejemplo de respuesta (200 OK)**:
  ```json
  {
    "id": 1,
    "nombre": "Cliente1",
    "montoInicial": 100.0,
    "montoFinal": 98.0,
    "tipoCambio": 0.98,
    "fechaRegistro": "2024-02-27T12:00:00Z"
  }
  ```

## Documentación Swagger
- Swagger UI: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- API Docs JSON: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

## Tecnologías Utilizadas
- **Spring Boot**
- **Spring WebFlux**
- **R2DBC (H2 en memoria, modo MySQL)**
- **RabbitMQ**
- **Springdoc OpenAPI para documentación**

## Configuración
El servicio utiliza los siguientes valores de configuración:
```properties
spring.application.name=exchange-support-api
server.port=8081

spring.r2dbc.url=r2dbc:h2:mem:///postdb;DB_CLOSE_DELAY=-1;MODE=MYSQL
spring.r2dbc.username=sa
spring.r2dbc.password=
spring.r2dbc.initialization-mode=always

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

## Instalación y Ejecución
1. Clonar el repositorio.
2. Compilar y ejecutar el proyecto con Maven o Gradle.
   ```sh
   mvn spring-boot:run
   ```
   o
   ```sh
   ./gradlew bootRun
   ```
3. Acceder a la documentación en [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html).

## Contribuciones
Para contribuir a este proyecto, puedes hacer un fork del repositorio, realizar cambios y enviar un pull request.

