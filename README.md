# Clínica API 🩺

## Descripción
Este proyecto es una API desarrollada en Java Spring Boot para la gestión de una clínica genérica. La API permite la administración de pacientes, doctores, medicamentos, recetas y otros aspectos relacionados con la operación de una clínica.

## ¿Por que una aplicacion para la gestion de una Clīnica?
Implementar una aplicación para la gestión de una clínica puede transformar significativamente la manera en que opera tradicionalmente, mejorando la eficiencia, precisión y accesibilidad.

## Características
### 1. **Automatización de Procesos** 🤖
**Tradicional:** Los registros y procesos se manejan manualmente, lo que puede llevar a errores y duplicidad de datos.
**Con la Aplicación:** Automatiza procesos como la gestión de pacientes, citas, recetas y medicamentos, reduciendo errores y mejorando la eficiencia operativa.

### 2. **Acceso Rápido a la Información** 📊
**Tradicional:** La información está dispersa, dificultando el acceso rápido y la coordinación.
**Con la Aplicación:** Centraliza la información, permitiendo un acceso rápido a historiales médicos, citas y datos de pacientes, mejorando la toma de decisiones.

### 3. **Mejora en la Gestión de Citas** 📅
**Tradicional:** La gestión de citas puede ser manual y propensa a errores.
**Con la Aplicación:** Permite a los pacientes reservar, reprogramar o cancelar citas en línea y recibe recordatorios automáticos para reducir ausencias.

### 4. **Seguridad y Privacidad de los Datos** 🔒
**Tradicional:** Los datos en papel son vulnerables y la protección puede ser insuficiente.
**Con la Aplicación:** Implementa autenticación JWT y permisos basados en roles para proteger los datos sensibles y garantizar su privacidad.

### 5. **Generación de Reportes y Análisis** 📈
**Tradicional:** La recopilación de datos para reportes es lenta y laboriosa.
**Con la Aplicación:** Ofrece capacidades de análisis y generación de reportes automáticos para obtener información detallada sobre el funcionamiento de la clínica.

### 6. **Integración con Otros Sistemas** 🔗
**Tradicional:** La integración con otros sistemas puede ser limitada.
**Con la Aplicación:** Facilita la integración con sistemas de laboratorio, farmacias y seguros médicos, creando un flujo de trabajo más cohesivo.


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

   
