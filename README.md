# MediCare - Gestión de Citas Médicas y Usuarios

Caso Práctico 2 — Desarrollo de Aplicaciones Web y Patrones
Universidad Fidélitas · Ingeniería en Sistemas de Computación

Estudiante: **Rolando Marti**

---

## Descripción

Aplicación web para la plataforma de servicios de salud **MediCare** que permite
administrar los usuarios del sistema, gestionar sus roles, restringir el acceso a
funcionalidades según permisos con **Spring Security** y consultar información sobre
las citas médicas. Al registrar un usuario se envía automáticamente un correo de
bienvenida mediante **Spring Mail**.

## Tecnologías

- Java 17
- Spring Boot 3.3.5 (Spring Web, Spring Data JPA, Spring Security, Spring Mail)
- Thymeleaf + Bootstrap 5
- MySQL 8

## Estructura de paquetes

```
com.medicare
├── domain          Entidades JPA (Rol, Usuario, CitaMedica)
├── repository      Repositorios JPA con consultas derivadas y @Query
├── service         Interfaces de servicio
├── serviceimpl     Implementaciones de servicio (lógica de negocio + Spring Mail)
├── controllers     Controladores MVC (GET/POST, redirecciones por rol)
└── config          Spring Security y redirección personalizada por rol
```

## Configuración

- Puerto: **78**
- Base de datos: **medicare** (MySQL, puerto 3306)

### 1. Crear la base de datos

Ejecute el script oficial incluido en la raíz del proyecto:

```bash
mysql -u root -p < medicare.sql
```

### 2. Credenciales (variables de entorno opcionales)

| Variable                  | Valor por defecto      |
|---------------------------|------------------------|
| `MEDICARE_DB_USER`        | `root`                 |
| `MEDICARE_DB_PASSWORD`    | `1234`                 |
| `MEDICARE_MAIL_USER`      | `your-email@gmail.com` |
| `MEDICARE_MAIL_PASSWORD`  | `your-app-password`    |

Para el envío real de correos configure `MEDICARE_MAIL_USER` y
`MEDICARE_MAIL_PASSWORD` con una cuenta de Gmail y una contraseña de aplicación.

### 3. Ejecutar

```bash
mvn spring-boot:run
```

La aplicación queda disponible en `http://localhost:78`.

## Usuarios de prueba

| Correo                   | Contraseña | Rol      |
|--------------------------|------------|----------|
| admin@medicare.com       | 12345      | ADMIN    |
| medico@medicare.com      | 12345      | MEDICO   |
| paciente@medicare.com    | 12345      | PACIENTE |

## Permisos por rol

- **ADMIN**: gestiona Usuarios, Roles y Citas Médicas.
- **MEDICO**: gestiona Citas Médicas.
- **PACIENTE**: visualiza Citas Médicas.

## Consultas avanzadas (JPA / Hibernate)

1. Buscar citas médicas por estado (activas / inactivas).
2. Buscar citas médicas dentro de un rango de fechas.
3. Buscar citas por coincidencia parcial en la especialidad.
4. Buscar usuarios por rol asignado.
5. Contar citas médicas activas.
