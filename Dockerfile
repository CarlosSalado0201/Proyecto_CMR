# Etapa 1: Build con Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la app
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/com.mx.CMR-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
