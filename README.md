# Biblioteca Barrial — Backend

API REST para la gestión de una biblioteca barrial: libros, ejemplares, socios y préstamos. Implementa todas las reglas de negocio del sistema y expone endpoints que consume el [frontend](https://github.com/candela06/BibliotecaBarrial-frontend).

---

## Descripción

El backend está construido con **Spring Boot** y **Spring Data JPA**. Se encarga de la persistencia, la validación de datos y la lógica de negocio (por ejemplo, que un socio no pueda tener más de 3 préstamos activos, o que un ejemplar dado de baja no pueda volver a prestarse).

Todos los errores de negocio se representan con excepciones específicas, de modo que el frontend pueda mostrar mensajes claros al usuario en lugar de errores genéricos.

---

## Stack tecnológico

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **Bean Validation** (Jakarta Validation: `@NotBlank`, `@Email`, `@ISBN`)
- **Base de datos relacional**: H2 / MySQL / PostgreSQL (según configuración)

---

## Estructura del proyecto

```
src/main/java/com/biblioteca/demo/
├── controller/     # Endpoints REST
├── entity/         # Libro, Ejemplar, Socio, Prestamo, Estado
├── repository/     # Interfaces JpaRepository
└── service/        # Reglas de negocio
```

---

## Cómo ejecutarlo

1. Cloná el repositorio.
2. Asegurate de tener Java 17+ instalado.
3. Ejecutá:

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

> ⚠️ Acordate de habilitar **CORS** para que el frontend pueda consumir los endpoints desde otro origen.

---

## Endpoints principales

### Libros

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/libros` | Catálogo completo |
| `GET` | `/libros/{id}` | Libro por ID |
| `POST` | `/libros` | Registrar libro |
| `PUT` | `/libros/{id}` | Actualizar libro |
| `DELETE` | `/libros/{id}` | Eliminar libro |

### Ejemplares

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/ejemplares` | Todos los ejemplares |
| `GET` | `/ejemplares/{id}` | Ejemplar por ID |
| `POST` | `/ejemplares` | Registrar ejemplar |
| `PATCH` | `/ejemplares/{id}` | Dar de baja |
| `PATCH` | `/ejemplares/disponible/{id}` | Reactivar ejemplar |

### Socios

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/socios` | Todos los socios |
| `GET` | `/socios/{id}` | Socio por ID |
| `POST` | `/socios` | Registrar socio |

### Préstamos

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/prestamos` | Todos los préstamos |
| `GET` | `/prestamos/activos` | Préstamos activos del sistema |
| `GET` | `/prestamos/{idSocio}` | Préstamos activos de un socio |
| `POST` | `/prestamos` | Realizar préstamo |
| `PATCH` | `/prestamos/{id}` | Devolver préstamo |

---

## Reglas de negocio

- Un **ISBN** no puede repetirse entre libros.
- Un **correo electrónico** no puede pertenecer a dos socios.
- No puede crearse un ejemplar asociado a un libro inexistente.
- No puede prestarse un ejemplar que no esté `DISPONIBLE`.
- No puede prestarse un ejemplar dado de `BAJA`.
- No puede realizarse un préstamo a un socio inexistente.
- Un socio no puede tener más de **3 préstamos activos** simultáneamente.
- Un ejemplar no puede tener más de **un préstamo activo** simultáneamente.
- No puede devolverse un ejemplar que no tenga un préstamo activo.
- Un ejemplar dado de baja **no** puede volver a estar disponible mediante una devolución.
- Los identificadores de libros, ejemplares, socios y préstamos deben ser únicos.
- Los datos obligatorios no pueden ser `null` ni estar vacíos.
- El ISBN debe respetar un formato válido de **ISBN-13**.
- El correo electrónico debe tener un formato válido.
- El nombre del socio debe contener al menos un carácter no vacío.
- Las fechas de préstamo y vencimiento deben ser coherentes.
- Los errores de negocio se representan explícitamente mediante excepciones específicas.

---

## Repos relacionados

- **Frontend**: [biblioteca-barrial-frontend](https://github.com/candela06/BibliotecaBarrial-frontend)

---

## Licencia

Este proyecto está bajo la licencia **MIT**. Ver el archivo [LICENSE](./LICENSE) para más detalles.

---