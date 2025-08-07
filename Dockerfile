# Multi-stage build para optimizar el tamaño de la imagen
FROM maven:3.8.6-openjdk-17 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (capa de caché)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Imagen de producción
FROM openjdk:17-jre-slim

# Instalar dependencias del sistema
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Crear usuario no-root para seguridad
RUN groupadd -r harmonia && useradd -r -g harmonia harmonia

# Establecer directorio de trabajo
WORKDIR /app

# Copiar JAR desde la etapa de build
COPY --from=build /app/target/harmonia-1.0-SNAPSHOT.jar app.jar

# Cambiar propietario de archivos
RUN chown -R harmonia:harmonia /app

# Cambiar al usuario no-root
USER harmonia

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"] 