FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/SecurityJwtProject-0.0.1-SNAPSHOT.jar /app/SecurityJwtProject.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "SecurityJwtProject.jar"]