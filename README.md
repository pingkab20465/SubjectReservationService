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
- [Github Actions](#github-actions)
  - [Build & Deploy script](#build--deploy-script)

___

## Docker

### Docker Login
```bash
echo "<GITHUB_TOKEN>" | docker login ghcr.io -u <USERNAME>
```

### Docker nginx
task
- Hello myname

Build image
```bash
docker build -t ghcr.io/<username>/hello-myname nginx
# E.g.
docker build -t ghcr.io/bazsup/hello-myname nginx
```
Push image
```bash
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

```dockerfile
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
```yaml
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
```yaml
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
```bash
docker tag <old-name> <new-name>
# Example
docker tag subject-api ghcr.io/bazsup/subject-api
```

### Docker compose for deployment
```yaml
version: '3.4'

services:
  subject-api:
    image: ghcr.io/bazsup/subject-api:latest
    ports:
      - 80:9090
```

## GitHub Actions

### Build & Deploy script

```yaml
# This workflow will build a Java project with Maven
# For more information see: https://help.github.com/actions/language-and-framework-guides/building-and-testing-java-with-maven

name: Deploy

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  install-dependencies:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
    - name: Build with Maven
      run: mvn -B package --file pom.xml
  
  build-image:
    needs: install-dependencies
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Login to GHCR
      uses: docker/login-action@v1
      with:
        registry: ghcr.io
        username: bazsup
        password: ${{ secrets.GITHUB_TOKEN }}

    - name: Extract metadata (tags, labels) for Docker
      id: meta
      uses: docker/metadata-action@v3
      with:
        images: ghcr.io/bazsup/subject-api
        tags: |
          type=sha
        flavor: |
          latest=${{ github.event_name == 'push' }}

    - name: Build and push
      uses: docker/build-push-action@v2
      with:
        context: .
        file: ./Dockerfile
        push: true
        tags: ${{ steps.meta.outputs.tags }}

    - name: Publish compose file
      uses: appleboy/scp-action@master
      with:
        host: ${{ secrets.SSH_HOSTNAME }}
        username: ${{ secrets.SSH_USERNAME }}
        key: ${{ secrets.SSH_PRIVATE_KEY }}
        source: "./docker-compose.prod.yml"
        target: "."
  
  deploy:
    needs: build-image
    runs-on: ubuntu-latest
    if: ${{ github.event_name == 'push' }}

    steps:
    - name: Run application
      uses: appleboy/ssh-action@master
      with:
        host: ${{ secrets.SSH_HOSTNAME }}
        username: ${{ secrets.SSH_USERNAME }}
        key: ${{ secrets.SSH_PRIVATE_KEY }}
        script: |
          docker-compose -f docker-compose.prod.yml pull
          docker-compose -f docker-compose.prod.yml up --build -d
```




Thanks for watching !
