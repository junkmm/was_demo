FROM eclipse-temurin:20-jdk-alpine as build
WORKDIR /workspace/app

ENV DATABASE_URL Dummy
ENV DATABASE_USERNAME Dummy
ENV DATABASE_PASSWORD Dummy

COPY mvnw .
COPY .mvn .mvn
COPY mvnw.cmd .
COPY pom.xml .
COPY src src

RUN apk update && \
    apk add dos2unix && \
    find . -type f -exec dos2unix {} \; && \
    apk del dos2unix && \
    rm -rf /var/cache/apk/*

RUN ./mvnw clean package

FROM openjdk:20
WORKDIR /usr/src/myapp
COPY --from=build /workspace/app/target/demo-0.0.1-SNAPSHOT.jar /usr/src/myapp
CMD ["java", "-jar", "/usr/src/myapp/demo-0.0.1-SNAPSHOT.jar"]