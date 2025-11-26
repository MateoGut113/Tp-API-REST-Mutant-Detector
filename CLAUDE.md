# CLAUDE.md — Guía para el asistente IA Claude

## 🧠 Contexto del proyecto

El sistema desarrollado es una aplicación orientada a objetos que modela la detección automática de mutantes a partir de secuencias de ADN.
Este proyecto es una API REST, creada en Java 17 con Spring Boot 3.5.7, que implementa algoritmos de análisis de patrones en matrices.
La persistencia se maneja con Spring Data JPA, inicialmente con H2 en memoria y luego migrable a PostgreSQL. El build se gestiona con Gradle.

## 🧱 Estructura del código

El código fuente está ubicado en src/main/java/org.example.Mutantes

### La entidad principal
**DnaRecord:** Representa un registro de ADN analizado.
Contiene hash único (dna_hash), resultado (is_mutant) y fecha de creación (created_at).
Se persiste en la tabla dna_records con su @Entity e @Id necesario.

### **DTOs (Data Transfer Objects)**
**DnaRequest:** Define el contrato de entrada para el endpoint /mutant.

**StatsResponse:** Define el contrato de salida para el endpoint /stats.

**ErrorResponse:** Define el formata para la salida de errores de validación.

**HealthResponse:** Endpoint de salud de la aplicación.

**ErrorDateSchema:** Esquema de ejemplo para mostrar en interfaz.

**Error404Schema:** Esquema de ejemplo para mostrar en interfaz.

### **Controladores**
**MutantController:** Clase controladora principal de la API Mutant Detector. Expone los endpoints bajo la ruta `/dna`.
No contiene lógica de negocio propia: actúa como puente entre la capa de presentación (HTTP) y la capa de servicios.

**RootController:** Clase controladora auxiliar para la raíz de la aplicación (`/`).

### **Servicios**
**MutantService:** Orquesta la lógica de negocio, calcula hash, consulta BD y delega al detector.

**MutantDetector:** Implementa el algoritmo de detección de secuencias mutantes en 4 direcciones (horizontal, vertical, diagonal ascendiente y descendiente).

**StatsService:** Calcula estadísticas de mutantes y de humanos.

### **Repositorios**
**DnaRecordRepository:** Interfaz JPA para acceder a dna_records.
Métodos principales: findByDnaHash(), countByIsMutant(), countByIsMutantAndCreatedAtBetween.

### **Validaciones**
**ValidDnaSequenceValidator:** Asegura que las secuencias sean NxN y solo contengan A/T/C/G.

### **Excepciones**
**GlobalExceptionHandler:** Maneja errores globales.

### **Configuraciones**
**SwaggerConfig:** Configura documentación automática de API.

### **Tool** 
**CalculaorDnaHash:** Calcula la matriz a VARCHAR 64.

**ConvertCharDna:** Convierte la matriz a cadena de Chars.

**RateLimitRequest:** Asegura un limite de 10 request por minuto.

## Build the project

BUILD:
```bash
 ./gradlew build
```

RUN:
```bash
 ./gradlew run
```

Generar JAR ejecutable:
```bash
./gradlew bootJar
java -jar Mutantes-0.0.1-SNAPSHOT.jar
```

## 🎨 Convenciones de estilo

**Nombres de clases:** PascalCase (MutantService, DnaRecord).

**Nombres de métodos y atributos:** camelCase (findByDnaHash, isMutant).

**Nombres de constantes:** UPPER_SNAKE_CASE.

**Distribucion de paquetes:**
- config/ → interface en API
- controller/ → endpoints REST
- dto/ → contratos de API
- entity/ → modelo JPA
- exception/ → manejo de errores
- repository/ → acceso a datos
- service/ → lógica de negocio
- tool/ → herramientas personalizadas
- validation/ → validacion de matriz

**Encapsulación:**
- Atributos privados.
- Uso de Lombok (@Data, @NoArgsConstructor, @RequiredArgsConstructor).
- Evitar setters públicos en entidades, preferir inmutabilidad lógica.

## 🧭 Instrucciones para Claude (reglas de negocio)

Un **humano es mutante** si existen ≥ 2 secuencias de exactamente 4 letras iguales en la matriz NxN.

**Secuencias válidas:** horizontales, verticales, diagonales descendentes y ascendentes.

**Validaciones críticas:**

- Matriz cuadrada NxN.

- Tamaño mínimo 4x4.

- Solo caracteres A/T/C/G.

**Persistencia:** cada ADN se guarda con hash SHA-256 para evitar duplicados.

**Estadísticas:** /stats devuelve ratio mutantes/humanos.

## 🧪 Ejemplos de código deseado (json)

**Mutante:**
```json
POST /mutant
{
"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

Response:
- 200 OK → es mutante.

**Humano:**
```json
POST /mutant
{
  "dna": ["ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "GCCCTA", "TCACTG"]
}
```

Response:
- 403 Forbidden → no es mutante.

## 🔐 Restricciones

**Claude debe respetar** las siguientes reglas técnicas y de estilo para garantizar compatibilidad, claridad y coherencia en el proyecto:

- **Evitar duplicación de ADN:** dna_hash es único.

- **No violar reglas de negocio** (mínimo 2 secuencias, tamaño de matriz máximo 999x999).

- **No lógica de negocio dentro de entidades JPA.**

- **Evitar constructores duplicados** si Lombok ya los genera.

## 🐳 Despliegue con Docker

**Dockerfile**

Multi-stage build:

- Etapa 1: compila con Gradle y genera JAR.

- Etapa 2: ejecuta con JRE Alpine, usuario no root, healthcheck activado.

**Docker Compose**

```yaml
services:
postgres:
image: postgres:15-alpine
environment:
POSTGRES_USER: mutant_user
POSTGRES_PASSWORD: mutant_pass
POSTGRES_DB: mutantdb
ports:
- "5432:5432"

app:
build: .
environment:
SPRING_PROFILES_ACTIVE: dev
SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/mutantdb
SPRING_DATASOURCE_USERNAME: mutant_user
SPRING_DATASOURCE_PASSWORD: mutant_pass
ports:
- "8080:8080"
```

________________________________________
## 👥 Equipo de Desarrollo
Los **MateoGut** Team

📄 Licencia
- Este proyecto es de código abierto y está disponible para fines educativos y de demostración.
- Sientase libre de disfrutar de dicho codigo.
________________________________________
Versión: 2.0 Última actualización: Noviembre 2025