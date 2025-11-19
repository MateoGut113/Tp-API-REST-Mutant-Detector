# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
# Imagen base con JDK 17 sobre Alpine (ya incluye Java)
FROM eclipse-temurin:17-jdk-alpine AS build

# Definir directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar todo el código fuente al contenedor
COPY . .

# Dar permisos de ejecución al Gradle Wrapper
RUN chmod +x ./gradlew

# Compilar y generar el JAR ejecutable
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
# Imagen base con solo el runtime de Java 17 sobre Alpine
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no root para mayor seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Definir directorio de trabajo
WORKDIR /app

# Documentar que la aplicación escucha en el puerto 8080
EXPOSE 8080

# Copiar el JAR generado en la etapa de build
COPY --from=build /app/build/libs/Mutantes-0.0.1-SNAPSHOT.jar app.jar

# Healthcheck para monitoreo automático
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]