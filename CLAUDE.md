# CLAUDE.md — Guía para el asistente IA Claude

## 🧠 Contexto del proyecto

El sistema desarrollado es una aplicación orientada a objetos que modela la detección automática de mutantes a partir de secuencias de ADN.
Este proyecto es una API REST educativa, creada en Java 17 con Spring Boot 3.5.7, que implementa algoritmos de análisis de patrones en matrices.
La persistencia se maneja con Spring Data JPA, inicialmente con H2 en memoria y luego migrable a PostgreSQL. El build se gestiona con Gradle.

## 🧱 Estructura del código

El código fuente está ubicado en src/main/java/org.example.Mutantes

### Las entidades principales son:
DnaRecord: Representa un registro de ADN analizado. Contiene hash único (dna_hash), resultado (is_mutant) y fecha de creación (created_at). Se persiste en la tabla dna_records.

### **DTOs (Data Transfer Objects)**
DnaRequest: Define el contrato de entrada para el endpoint /mutant.

StatsResponse: Define el contrato de salida para el endpoint /stats.

### **Servicios**
MutantService: Orquesta la lógica de negocio, calcula hash, consulta BD y delega al detector.

MutantDetector: Implementa el algoritmo de detección de secuencias mutantes en 4 direcciones (horizontal, vertical, diagonal ↘ y diagonal ↗).

StatsService: Calcula estadísticas de mutantes vs humanos.

### **Repositorios**
DnaRecordRepository: Interfaz JPA para acceder a dna_records. Métodos principales: findByDnaHash(), countByIsMutant().

### **Capas transversales**
Validaciones: ValidDnaSequenceValidator asegura que las secuencias sean NxN y solo contengan A/T/C/G.

Excepciones: GlobalExceptionHandler maneja errores globales.

SwaggerConfig: Configura documentación automática de API.

Tool: Ayuda a completar los requerimientos.

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

Clases: PascalCase (MutantService, DnaRecord).

Métodos y atributos: camelCase (findByDnaHash, isMutant).

Constantes: UPPER_SNAKE_CASE.

Paquetes:

- controller/ → endpoints REST

- service/ → lógica de negocio

- repository/ → acceso a datos

- entity/ → modelo JPA

- dto/ → contratos de API

Encapsulación:

- Atributos privados.

- Uso de Lombok (@Data, @NoArgsConstructor, @RequiredArgsConstructor).

- Evitar setters públicos en entidades, preferir inmutabilidad lógica.

## 🧭 Instrucciones para Claude (reglas de negocio)

Un humano es mutante si existen ≥ 2 secuencias de exactamente 4 letras iguales en la matriz NxN.

Secuencias válidas: horizontales, verticales, diagonales descendentes y ascendentes.

Validaciones críticas:

- Matriz cuadrada NxN.

- Tamaño mínimo 4x4.

- Solo caracteres A/T/C/G.

Persistencia: cada ADN se guarda con hash SHA-256 para evitar duplicados.

Estadísticas: /stats devuelve ratio mutantes/humanos.

## 🧪 Ejemplos de código deseado (json)

```json
POST /mutant
{
"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

Response:

- 200 OK → es mutante.

- 403 Forbidden → no es mutante.

## 🔐 Restricciones

Claude debe respetar las siguientes reglas técnicas y de estilo para garantizar compatibilidad, claridad y coherencia en el proyecto:

- Evitar duplicación de ADN: dna_hash es único.

- No violar reglas de negocio (mínimo 2 secuencias).

- Usar @Enumerated(EnumType.STRING) si se agregan enums futuros.

- No lógica de negocio dentro de entidades JPA.

- Evitar constructores duplicados si Lombok ya los genera.

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