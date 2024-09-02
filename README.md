# Clínica API

Este proyecto es una API desarrollada en Java Spring Boot para la gestión de una clínica genérica. La API permite la administración de pacientes, doctores, medicamentos, recetas y otros aspectos relacionados con la operación de una clínica.

## Características

- **Autenticación y Autorización:** Implementada utilizando JWT y Spring Security para asegurar las operaciones y gestionar roles de usuarios.
- **Caché:** Se utiliza ECache para optimizar el rendimiento y reducir la carga en la base de datos.
- **Modularidad:** La API está estructurada para soportar múltiples módulos como pacientes, doctores, medicamentos, recetas, entre otros.
- **Base de datos**: Se utiliza un gestor de base de datos relacional, el cual se modela con aproximidad una Clinica.

## Tecnologías Utilizadas

- **Java Spring Boot:** Framework principal para el desarrollo de la API.
- **JWT (JSON Web Token):** Para la autenticación segura de usuarios.
- **Jdbc**: Para la conexión e iteracción con la base de datos.
- **Spring Security:** Para gestionar la autorización y roles de usuarios.
- **ECache:** Implementación de caché para mejorar el rendimiento.
- **Maven:** Gestión de dependencias y ciclo de vida del proyecto.
- **MySQL:** Base de datos relacional para almacenar la información.
- **Swagger-ui**: Esta librería proporciona una interfaz personlizable e iterativa para nuestra API
- **openapi-generator**:El openapi-generator-maven-plugin es un plugin de Maven que se utiliza para generar automáticamente código fuente, clientes API, servidores, y documentación a partir de un archivo de especificación OpenAPI 

## Requisitos Previos

- **Java 17+**
- **Maven**
- **Base de datos MySQL**
- **Spring Boot 3.2.x+**

## Configuración

1. Clona este repositorio:
   ```bash
   git clone https://github.com/Bautistadev/Clinica.git
   cd Clinica
   ```
2. Variables de entorno
   ```bash
      export ENV_DATABASE_DB = "Nombre de la base de datos"
      export ENV_PASSWORD_DB = "Contraseña de la base de datos"
      export ENV_USER_DB = "Usuario de la base de datos"
      export ENV_PORT_SERVICE = "Puerto en el cual se ejecuta"
      export ENV_URI_DB = "Direccion de la base de datos (localhost, 192.168.xxx.xxx, http/.....)"
   ```
3. Resolver dependencias
   ``` Maven
       mvn dependency:resolve
   ```
4. Compilar 
   ```Maven
      mvn install
   ```  
5. Correr test (Opcional y verificar haber configurado las variables de entorno)
   ```Maven
      mvn test
   ```


## Base de datos:

![Untitled](https://github.com/user-attachments/assets/09dbaf22-daad-49b3-beb9-606bcdb395a6)

   
