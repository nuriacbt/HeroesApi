# Heroes API

Proyecto realizado para la prueba técnica, enmarcado dentro del proceso de selección de AUBAY, para el cliente W2M.
Este consiste en la realización de una API REST CRUD que permite gestionar Heroes.

## Inicio de la aplicación:

comandos

La aplicación en local se levanta por defecto en el pueto 8080 y con el context path /hero-api.

## Seguridad: 
Todos los endpoints están protegidos mediante una API-KEY. Su valor por defecto es 8e366910. Esta se puede incorporar en 
los headers de la petición, mediante un header llamado X-API-KEY o también se puede incluir en la url, mediante el 
parámetro API-KEY. Está segunda opción se añadió para facilitar las pruebas con el SWAGGER, y de los TEST de integración,
aunque a nivel de seguridad no es la opción más recomendada.

- Añadir el API-KEY en los headers: X-API-KEY:8e366910
- Añadir el API-KEY en la url: ?API-KEY=8e366910

## Información de la API:

Actualmente la API de heroes cuenta con 115 heroes. La API contiene todos los métodos necesarios para poder realizar una 
gestión completa. Todos los endpoints, además disponen de una respuesta de error personalizada y única para cualquier 
problema que pueda suceder.

### Lista de endpoints:

- GET: /hero-api/v1/heroes/all --> devuelve todos los heroes existentes.
- GET: /hero-api/v1/heroes?page=12&elements=10 --> devuelve la lista de heroes de forma páginada.
- GET: /hero-api/v1/heroes/{id} --> busca y devuelve un heroe por su ID.
- GET: /hero-api/v1/heroes/search?name=MAN --> busca y devuelve los heroes que contengas el parámetro name en su nombre.
- POST: /hero-api/v1/heroes --> crea un nuevo heroe.
- PUT: /hero-api/v1/heroes --> modifica un heroe existente.
- DELETE: /hero-api/v1/heroes/{id} --> elimina el heroe por su ID.

### Objeto Heroe

#### Atributos:
- id --> ID única del heroe.
- name --> nombre del heroe.
- imageUrl --> url que contiene la imagen del heroe.
- intelligence --> valor númerico de (0 - 100) que indica el nivel de inteligencia del heroe. 
- speed --> valor númerico de (0 - 100) que indica el nivel de velocidad del heroe.
- combat --> valor númerico de (0 - 100) que indica el nivel de combate del heroe.

#### Ejemplo:
    {
        "id": 720,
        "name": "Wonder Woman",
        "imageUrl": "https://www.superherodb.com/pictures2/portraits/10/100/807.jpg",
        "intelligence": 59,
        "speed": 60,
        "combat": 71
    }

### Ejemplo de response errónea personalizada:
    {
        "timestamp": "04/02/2024 19:54:07",
        "status": 404,
        "error": "This heroe could not be removed because his id is incorrect"
    }


## Librerías usadas:

Tal y como se indicaba en los requisitos, se ha usado java 21 para compilar la aplicación juntamente con maven 3.

Además se han utilizado las siguientes librerías, todas ellas con la última versión disponible:
- springBoot v3.2.2 con todas las siguientes dependencias:
  - data-jpa
  - security
  - cache
  - aop
  - starter-test
  - test
- h2 database v2.2.224
- flyway v10.7.0 
- lombook 1.18.30
- mapstruct 1.5.5.Final
- springdoc-openapi v1.7.0
- springdoc-openapi-web v2.3.0
