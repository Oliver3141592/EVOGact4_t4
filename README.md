# API REST con Spring Boot, Spring Security y JWT

Actividad 4. API REST que gestiona estudiantes (CRUD) y protege sus endpoints con autenticación real mediante Spring Security y JWT. Probada y documentada con Bruno.

## Tecnologías

- Java 21
- Spring Boot 4.1.2 (Web MVC, Data JPA, Validation, Security)
- MySQL
- JJWT 0.12.6 (generación y validación de tokens)
- Lombok
- Bruno (pruebas de la API)

## URL base de la API (VPS)

```
http://179.236.236.29:8088
```

## Estructura del proyecto

```
src/main/java/com/example/crudrapido
├── controller   StudentController, AuthController
├── dto          StudentDto, AuthRequest
├── entity       Student, AppUser
├── exception    ErrorHandler
├── repository   StudentRepository, UserRepository
├── security     SecurityConfig, JwtFilter, JwtService
└── service      StudentService
bruno/           Colección de Bruno
capturas/        Capturas de pantalla del README
```

## Endpoints

| Método | Ruta | Token | Respuesta |
|---|---|---|---|
| POST | `/api/auth/register` | No | 201 con token |
| POST | `/api/auth/login` | No | 200 con token, 401 si las credenciales son incorrectas |
| GET | `/api/v1/students?page=0&size=10` | Sí | 200 (lista paginada) |
| GET | `/api/v1/students/{id}` | Sí | 200, 404 |
| POST | `/api/v1/students` | Sí | 201, 400 |
| PUT | `/api/v1/students/{id}` | Sí | 200, 400, 404 |
| DELETE | `/api/v1/students/{id}` | Sí | 204, 404 |

Cualquier ruta distinta de `/api/auth/**` responde **401** si no se envía un token JWT válido.

El token se envía en el header:

```
Authorization: Bearer <token>
```

### Ejemplos de cuerpo (JSON)

Registro y login:

```json
{
  "email": "ana@mail.com",
  "password": "Password123"
}
```

Crear o actualizar un estudiante:

```json
{
  "firstName": "Luis",
  "lastName": "Perez",
  "email": "luis@mail.com",
  "description": "Alumno"
}
```

## Cómo ejecutarlo en local

1. Tener instalado Java 21 y MySQL.
2. Crear la base de datos:
   ```sql
   CREATE DATABASE crudrapido;
   ```
3. Ajustar el usuario y la contraseña de MySQL en `src/main/resources/application.properties`.
4. Ejecutar:
   ```
   ./mvnw spring-boot:run
   ```
5. La API queda en `http://localhost:8080`.

## Colección de Bruno

La colección está en la carpeta `bruno/` e incluye 8 peticiones:

1. Registro
2. Login (guarda el token en la variable `token`)
3. Crear
4. Listar
5. VerUno
6. Actualizar
7. Eliminar
8. SinToken (comprueba que un endpoint protegido rechaza la petición con 401)

Tiene dos entornos con la variable `baseUrl`:

- **Local:** `http://localhost:8080`
- **VPS:** `http://179.236.236.29:8088`

Para usarla: abrir la carpeta `bruno/` desde Bruno (Open Collection), elegir el entorno y ejecutar las peticiones en el orden de la lista.

## Despliegue en el VPS

1. Compilar el jar:
   ```
   ./mvnw clean package -DskipTests
   ```
2. Subirlo a una carpeta nueva del servidor (`~/actividad4`).
3. Ejecutarlo en el puerto 8088, pasando los datos de la base de datos y la clave JWT por parámetro, para no guardar credenciales en el repositorio:
   ```
   nohup java -jar crudrapido-0.0.1-SNAPSHOT.jar \
     --server.port=8088 \
     --spring.datasource.url=jdbc:mysql://localhost:3306/crudrapido_act4 \
     --spring.datasource.username=USUARIO_BD \
     --spring.datasource.password='PASSWORD_BD' \
     --jwt.secret='CLAVE_DE_AL_MENOS_32_CARACTERES' \
     > actividad4.log 2>&1 &
   ```

## Capturas de pantalla

Pruebas hechas con Bruno.

### Registro

![Registro](capturas/01-registro.png)

### Login

![Login](capturas/02-login.png)

### CRUD con token

#### Crear (POST)

![Crear](capturas/03-crear.png)

#### Listar (GET)

![Listar](capturas/04-listar.png)

#### Ver uno (GET por id)

![Ver uno](capturas/05-ver-uno.png)

#### Actualizar (PUT)

![Actualizar](capturas/06-actualizar.png)

#### Eliminar (DELETE)

![Eliminar](capturas/07-eliminar.png)

### Petición sin token (401)

![Sin token](capturas/08-sin-token.png)
