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

## Digrama
- Microservicios Reactivos con Mensajería Asíncrona y JWT

  ![image](/digrama.png)

## Descripción
Aplicación de soporte para almacenar datos de cliente que realizan intercambios de monedas.

## Organización del código
MVC (Modelo-Vista-Controlador)

- Controller → Maneja las peticiones HTTP.
- Service → Contiene la lógica de negocio.
- Model → Define entidades y DTOs.
- Repository → Accede a la base de datos.
- Config → Configuraciones generales.

## Información del Desarrollador
- **Autor**: Xander LS
- **Contacto**: [xanderlsdev@gmail.com](mailto:xanderlsdev@gmail.com)
- **Sitio web**: [https://xanderls.dev/](https://xanderls.dev/)


## Contribuciones
Para contribuir a este proyecto, puedes hacer un fork del repositorio, realizar cambios y enviar un pull request.

