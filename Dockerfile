# -------------------------------
# ETAPA 1: Build con Maven + Temurin 21
# -------------------------------
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del código
COPY . .

# Construir el JAR sin tests
RUN mvn clean package -DskipTests

# -------------------------------
# ETAPA 2: Runtime con OpenJDK 21
# -------------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/vitalia-0.0.1-SNAPSHOT.jar vitalia.jar

# Exponer puerto
EXPOSE 8080

# Definir variables de entorno por defecto (pueden ser sobrescritas desde .env)
ENV DATABASE=localhost
ENV USER=postgres
ENV PASSWORD=postgres
ENV SECRET=secretkey
ENV EMAIL=email@example.com
ENV PASS_EMAIL=secretemail

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "vitalia.jar"]
