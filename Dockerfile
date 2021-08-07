FROM maven:3.8.1-jdk-11-slim as build

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/

RUN mvn package

FROM openjdk:11.0.11-jre-slim

ARG JAR_FILE=/app/target/*.jar

COPY --from=build ${JAR_FILE} app.jar

EXPOSE 9090

ENTRYPOINT exec java -jar app.jar
