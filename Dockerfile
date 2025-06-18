# Stage 1: Build
FROM eclipse-temurin:17-alpine AS build

WORKDIR /app
COPY target/notepad-0.0.1-SNAPSHOT.jar notepad-0.0.1-SNAPSHOT.jar

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

RUN set -eux; \
    apk add -U --no-cache \
        curl \
        git  \
        make \
        bash \
    ;

WORKDIR /app
COPY --from=build /app/notepad-0.0.1-SNAPSHOT.jar notepad-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "notepad-0.0.1-SNAPSHOT.jar"]