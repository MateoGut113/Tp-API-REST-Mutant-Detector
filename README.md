# Trabajo Práctico Integrador - API Mutant Detector

## 📝 Descripción del proyecto
Este proyecto es una **API REST** que permite detectar automáticamente si una persona es mutante analizando su secuencia de ADN.  
El sistema evalúa matrices NxN de bases nitrogenadas (**A, T, C, G**) y aplica un algoritmo que busca **secuencias de 4 letras iguales** en direcciones horizontales, verticales y diagonales.  
Un humano es considerado **mutante** si se encuentran **más de una secuencia válida**.

La aplicación incluye:
- 📊 **Persistencia de datos** con H2 en memoria (migrable a PostgreSQL).
- 🧱 **Arquitectura en capas** (Controller, Service, Repository, Entity, DTOs).
- ✅ **Validaciones personalizadas** y manejo global de excepciones.
- 🧪 **Tests unitarios e integración** con JUnit 5 y cobertura con Jacoco.
- 📖 **Documentación interactiva** con Swagger/OpenAPI.
- 🐳 **Despliegue con Docker** para portabilidad y consistencia.

## ⚙️ Tecnologías utilizadas
- **Java. Versión: 17** → Lenguaje principal de desarrollo.
- **Spring Boot. Versión: 3.5.7**  → Framework para crear la API REST.
- **Gradle** → Gestor de dependencias.
- **Lombok** → Utilizado para simplificar getters/setters.
- **Swagger / OpenAPI** → Documentación interactiva de la API.
- **Spring Data JPA** → Acceso a datos y mapeo objeto-relacional.
- **H2 Database** → Base de datos en memoria.
- **PostgreSQL** → Base de datos relacional.
- **JUnit 5 + Jacoco** → Testing y medición de cobertura.
- **Docker** → Contenerización y despliegue portable.
- **Docker Compose** → Orquestación de servicios (API + PostgreSQL).

Se hizo uso de **Spring Initializr** (start.spring.io) para crear el proyecto.

## 🚀 Instrucciones para clonar y ejecutar el proyecto
1. Clonar el repositorio:

   ```bash
   git clone https://github.com/MateoGut113/Tp-API-REST-Mutant-Detector.git
   cd Tp-API-REST-Mutant-Detector

2. Ejecutar el proyecto con Gradle:

    ```bash
    ./gradlew bootRun

O directamente desde el IDE ejecutando la clase `MutantDetectorApplication`.

## ⚙️ Comandos mas utilizados

```bash
# Ejecutar todos los tests
./gradlew test
```

```bash
# Generar reporte de cobertura (Jacoco)
./gradlew jacocoTestReport
xdg-open build/reports/jacoco/test/html/index.html
```

```bash
# Construir JAR ejecutable
./gradlew bootJar
java -jar build/libs/Mutantes-0.0.1-SNAPSHOT.jar
```

**Para estos comandos debe tener la aplicacion Docker Desktop instalada y abierta:**
```bash
# Construir imagen Docker
docker build -t mutantes-api .
```

```bash
# Ejecutar contenedor Docker
docker run -p 8080:8080 mutantes-api
```

## 🌐 Tabla de endpoints

```
| Método | Ruta                     | Descripción                                 |  Código HTTP          |
|--------|--------------------------|---------------------------------------------|-----------------------|
| POST   | /dna/mutant              | Detecta si un ADN es mutante                | 201 / 400 / 403       |
| GET    | /dna/stats               | Devuelve estadísticas mutantes/humanos      | 200 / 400             |
| GET    | /dna/health              | Endpoint de salud de la aplicación          | 200                   |
| DELETE | /dna/{hash}              | Eliminar dna por su hash                    | 204 / 404             |
```

## 📸 Capturas de pantalla

**Documentación completa de endpoints**

1. Verificación de Mutante
![Captura de pantalla - POST Mutant 1°.png](capturas/Captura%20de%20pantalla%20-%20POST%20Mutant%201%C2%B0.png)
![Captura de pantalla - POST Mutant 2°.png](capturas/Captura%20de%20pantalla%20-%20POST%20Mutant%202%C2%B0.png)


2. Estadisticas de los dna
![Captura de pantalla - GET Stats 1°.png](capturas/Captura%20de%20pantalla%20-%20GET%20Stats%201%C2%B0.png)
![Captura de pantalla - GET Stats 2°.png](capturas/Captura%20de%20pantalla%20-%20GET%20Stats%202%C2%B0.png)


3. Endpoint de salud de la aplicacion
![Captura de pantalla - GET Health.png](capturas/Captura%20de%20pantalla%20-%20GET%20Health.png)


4. Eliminar dna mediante el hash
![Captura de pantalla DELETE Mutant.png](capturas/Captura%20de%20pantalla%20DELETE%20Mutant.png)


**Prueba de GET (estadistica dna)**
![Captura de pantalla - Prueba de GET Stats.png](capturas/Captura%20de%20pantalla%20-%20Prueba%20de%20GET%20Stats.png)

**Error 400 de validación (matriz contiene una "F")**
![Captura de pantalla - Error de validacion de matriz.png](capturas/Captura%20de%20pantalla%20-%20Error%20de%20validacion%20de%20matriz.png)

**Error 404 cuando hash no existe**
![Captura de pantalla - Hash no encontrado.png](capturas/Captura%20de%20pantalla%20-%20Hash%20no%20encontrado.png)

**Consola H2 con datos persistidos**
![Captura de pantalla - Datos en H2.png](capturas/Captura%20de%20pantalla%20-%20Datos%20en%20H2.png)

## 🔍 Instrucciones para acceder a Swagger UI y consola H2
Asegurarse de que la aplicación esté corriendo (./gradlew bootRun).

### 📘 Swagger UI
Abrir el navegador y acceder a:

http://localhost:8080/swagger-ui/index.html

Desde allí puedes:

- Probar todos los endpoints de la API
- Ver los modelos de entrada y salida de datos (DTOs)
- Consultar los códigos de respuesta HTTP

------------------------------------------------------------------
### 🗄️ Consola H2 (Base de datos en memoria)
Acceder a:

http://localhost:8080/h2-console

Usar los siguientes datos de conexión:
- JDBC URL: jdbc:h2:mem:mutantesdb
- Usuario: sa
- Contraseña: (dejar vacío)

Luego:
- Probar la conexión con "Test Connection"
- Presionar "Connect" para visualizar la tabla producto y consultar los datos persistidos.

## 💭 Conclusiones personales sobre lo aprendido
En lo personal, este trabajo práctico integrador me permitió:

- Implementar una **API REST completa**, integrando validaciones con **Bean Validation**, manejo global de excepciones
y respuestas consistentes con códigos HTTP apropiados.
- Experimentar con **Spring Data JPA** y bases de datos en memoria como **H2**, lo que me ayudó a visualizar el ciclo de vida de los datos
y luego migrar a PostgreSQL para un entorno más realista.
- Usar **Gradle** como herramienta de build, gestionando dependencias y automatizando tareas de compilación y testing.
- Incorporar **tests unitarios y de integración** con JUnit 5 y medir la cobertura con **Jacoco**.
- Documentar la API con **Swagger/OpenAPI**, lo que me dio una visión más profesional sobre cómo presentar y probar servicios web.
- Explorar el despliegue con **Docker**, entendiendo cómo empaquetar la aplicación en contenedores
y cómo integrarla con PostgreSQL mediante Docker Compose, como a su vez el migrado de datos.

Este proyecto me ayudó a implementar desde el diseño de algoritmos para detectar mutantes
hasta la puesta en marcha de un servicio web robusto y portable.

## 📚 Recursos Adicionales
**Para profundizar mejor los conceptos de la aplicación, ver el archivo:**\
`GUIA-INTEGRADOR-ESTUDIANTE.md`

**Para ver una guia tecnica sobre la aplicación, ver el archivo:**\
`CLAUDE.md`

## 👤 Nombre y legajo
**Nombre:** Mateo Gutierrez\
**Comision:** 3k10\
**Legajo:** 48855\
**Año:** 2025