# Subject Reservation Service

![alt text](https://github.com/imgrbs/SubjectReservationService/blob/master/requirements.png)

## Table of Contents
- [Docker](#docker)
  - [Docker login](#docker-login)
  - [Docker nginx 👉 Hello, name](#docker-nginx)
  - [Dockerfile](#dockerfile)
  - [Docker compose](#docker-compose)
  - [Docker tag](#docker-tag)
  - [Docker compose for deployment](#docker-compose-for-deployment)

___

## Docker

### Docker Login
```
echo "<GITHUB_TOKEN>" | docker login ghcr.io -u <USERNAME>
```

### Docker nginx
task
- Hello myname

Build image
```
docker build -t ghcr.io/<username>/hello-myname nginx
# E.g.
docker build -t ghcr.io/bazsup/hello-myname nginx
```
Push image
```
docker push ghcr.io/<username>/hello-myname
# E.g.
docker push ghcr.io/bazsup/hello-myname
```
Pull image
```
docker pull ghcr.io/<username>/hello-myname
# E.g.
docker pull ghcr.io/bazsup/hello-myname
```

### Dockerfile

```
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
```

### Docker compose
```
version: '3.4'

services:
  subject-api:
    image: subject-api
    build:
      context: .
      dockerfile: ./Dockerfile
    ports:
      - 8080:9090
```

with Nginx
```
version: '3.4'

services:
  subject-api:
    image: subject-api
    build:
      context: .
      dockerfile: ./Dockerfile
    ports:
      - 8080:9090

  nginx:
    image: docker.io/nginx:1.21.1-alpine
    ports:
      - 9090:80
```

Reference https://github.com/wdrdres3qew5ts21/MCW-Cloud-native-applications

### Docker tag
```
docker tag <old-name> <new-name>
# Example
docker tag subject-api ghcr.io/bazsup/subject-api
```

## Docker compose for deployment
```
version: '3.4'

services:
  subject-api:
    image: ghcr.io/bazsup/subject-api:latest
    ports:
      - 80:9090
```


Thanks for watching !
