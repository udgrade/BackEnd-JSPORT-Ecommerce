# Paso 1: Usar una imagen de Maven para compilar el código
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Usar una imagen ligera de Java para correr la aplicación
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]