# Stage 1: Build the JAR from source
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy the source code and pom.xml
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]