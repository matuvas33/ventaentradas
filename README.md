# 🎟️ Ticket Sales API

API RESTful desarrollada con **Spring Boot** para gestionar la venta y devolución de entradas para eventos, con control automático de aforo y migraciones de base de datos.

## 🚀 Características principales

* **Gestión de Eventos:** Creación, consulta detallada y listado paginado/filtrado.

* **Venta de Entradas:** Compra de entradas con límite de hasta 5 por transacción.

* **Control de Capacidad:** Ajuste atómico de la capacidad disponible tras cada compra o devolución.

* **Gestión de Devoluciones:** Reembolso y reposición de tickets adquiridos.

* **Migraciones de BD:** Versionado de esquemas y carga inicial de datos mediante Flyway.

* **Documentación Interactiva:** Interfaz gráfica integrada con Swagger UI / OpenAPI 3.

## 🛠️ Tecnologías utilizadas

* **Lenguaje:** Java 21

* **Framework:** Spring Boot (Spring MVC, Spring Data JPA, Bean Validation)

* **Base de Datos:** MySQL 8.x

* **Migración:** Flyway

* **Gestor de Dependencias:** Maven

* **Utilidades:** Project Lombok

* **Documentación:** SpringDoc OpenAPI

* **Testing:** JUnit 5, Mockito

## 📋 Requisitos previos

* **JDK 21** o superior instalado.

* **MySQL Server** en ejecución.

* **Git** instalado.

## ⚙️ Instalación y Configuración

### 1. Clonar el repositorio

```
git clone https://github.com/matuvas33/ventaentradas.git
cd ventaentradas

```

### 2. Configurar la Base de Datos

Inicia sesión en MySQL y crea la base de datos:

```
CREATE DATABASE eventosdb;

```

> **Nota:** Flyway se encargará de crear las tablas automáticamente en el primer arranque (`src/main/resources/db/migration`).

### 3. Configurar Credenciales

Configura las variables de entorno para la conexión:

```
export DB_USERNAME=tu_usuario_mysql
export DB_PASSWORD=tu_contraseña_mysql

```

*(Opcional: puedes definir estos valores directamente en `src/main/resources/application.properties`).*

### 4. Compilar y Ejecutar

Usa el Maven Wrapper incluido en el proyecto:

```
# En Linux/macOS:
./mvnw spring-boot:run

# En Windows:
mvnw.cmd spring-boot:run

```

La aplicación quedará disponible en: `http://localhost:8080`

## 📖 Documentación de la API (Swagger)

Una vez que la aplicación esté corriendo, accede a Swagger UI para probar los endpoints interactivamente:

👉 <http://localhost:8080/swagger-ui.html>

## 📌 Endpoints Principales

### Eventos (`/api/eventos`)

| Método | Endpoint | Descripción | 
| ----- | ----- | ----- | 
| `POST` | `/api/eventos` | Crear un nuevo evento | 
| `GET` | `/api/eventos` | Listar eventos (soporta filtros `fecha`, `soloDisponible`, `page`, `size`) | 
| `GET` | `/api/eventos/{id}` | Obtener detalle de un evento por ID | 

#### Ejemplo de creación (`POST /api/eventos`):

```
{
  "nombre": "Concierto de Rock",
  "fecha": "2026-11-15",
  "capacidadTotal": 500,
  "precio": 45.00
}

```

### Tickets (`/api/tickets`)

| Método | Endpoint | Descripción | 
| ----- | ----- | ----- | 
| `POST` | `/api/tickets` | Comprar entradas (máx. 5) | 
| `POST` | `/api/tickets/{id}/devoluciones` | Procesar la devolución de tickets de una compra | 

#### Ejemplo de compra (`POST /api/tickets`):

```
{
  "eventoId": 1,
  "emailComprador": "usuario@example.com",
  "cantidad": 2
}

```

#### Ejemplo de devolución (`POST /api/tickets/{id}/devoluciones`):

```
{
  "cantidad": 1
}

```

## 🧪 Pruebas unitarias y de integración

Ejecuta el conjunto de tests con:

```
./mvnw test

```

## 👤 Autor

* **Matias** - [@matuvas33](https://github.com/matuvas33)
