# RestApi para la gestión de usuarios UserApi

## Descripción del Proyecto

UserApi es una aplicación de gestión de usuarios que permite adicionalmente la validacion del login del usuario.

## Instalación

Sigue estos pasos para instalar y ejecutar el proyecto en tu máquina local:

1. Clona el repositorio desde GitHub: `git clone https://github.com/tuusuario/my-awesome-project.git`
2. Entra al directorio del proyecto: `cd my-awesome-project`
3. Ejecutar el comando para crear la imagen: `docker build --build-arg JAR_FILE=build/libs/*.jar -t apis/userapi .`
4. Inicia la aplicación: `docker run -p 8081:8081 myorg/myapp`

### Configuración base datos
`create table phone (id bigint not null, citycode varchar(255), contrycode varchar(255), number varchar(255), user_id binary(16), primary key (id))`

`create table user (id binary(16) not null, created timestamp(6), email varchar(255) not null, is_active boolean, last_login timestamp(6), modified timestamp(6), name varchar(255) not null, password varchar(255) not null, token varchar(2048), primary key (id))`

`alter table user add constraint UK_email unique (email)`

`alter table phone add constraint FK_user_id foreign key (user_id) references user (id)`

`create sequence phone_seq start with 1 increment by 50`

## Uso

### Endpoints documentación swagger

`http://localhost:8081/swagger-ui/index.html`

`http://localhost:8081/api-docs` json de la documentación



### Curls de ejemplo

Ejemplos de como utilizar el api.

curl --location --request GET 'http://localhost:8081/users/{{id_user}} ut' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer {{bearerToken}}'

Creacion de usuarios


curl --location --request POST 'http://localhost:8081/users/register' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
"email": "ut",
"name": "ex in ullamco",
"password": "amet",
"phones": [
{
"number": "fugiat ut",
"citycode": "Duis ex nulla",
"contrycode": "dolore"
},
{
"number": "cillum quis",
"citycode": "labo",
"contrycode": "non pariatur qui"
}
]
}'


## Configuración

Puedes personalizar la expresión regular del mail y la contraseña cambiando la propiedades `app.user.email.regex` y `app.user.password.regex.regexp`.

## Contacto

Si tienes preguntas o problemas, no dudes en ponerte en contacto con nosotros:

- Correo Electrónico: oliber.garcia@gmail.com

## FAQ

### Versión 1.0.0 (Fecha de Lanzamiento: 17/09/2023)

- Funcionalidad principal implementada.
- Corrección de errores menores.



