# Segui tus compras

Segui tus compras es una aplicación web que permitira a los clientes realizar el seguimiento de compras, maximizando así las ventas y mejorando la experiencia del usuario.

## Desarrolladores

- Laime Matias Daniel
- Cabezas Juan Manuel

## Tecnologias

* Java
* Spring Boot
* TypeScript
* React
* Postgres

## Caracteristicas

- Ver compras realizadas
- Agregar productos de interés
- Calificar productos
- Agregar reseña a los productos

## Contenedores 
  #### Base de datos
  - nombre de servicio: postgres
  - nombre de contenedor: postgres_container_grupo2 
  - puerto: 5432

  #### Backend
  - nombre de servicio: spring
  - nombre de contenedor: backend_container_grupo2 
  - puerto: 8080

  #### FrontEnd
  - nombre de servicio: frontend
  - nombre de contenedor: frontend_container_grupo2
  - puerto: 80

  #### Swagger 
    http://localhost:8080/swagger-ui/index.html

  Se levanta en el directorio principal del repositorio con el comando:   
  
  *docker-compose up -f ./contenedores.yaml up -d*


  **Se tienen que setear los secrets en el entorno**
