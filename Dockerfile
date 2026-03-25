# Paso 1: Usar una imagen de Maven con Temurin para compilar
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Usar la imagen oficial de Eclipse Temurin para correr la aplicación
# Esta imagen es más moderna y compatible con Render
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]