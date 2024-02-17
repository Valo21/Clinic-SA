FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./mvnw clean package

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build ./target/Clinic-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]