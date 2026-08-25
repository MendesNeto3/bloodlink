FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY shared/pom.xml shared/
COPY blood-stock-service/pom.xml blood-stock-service/

RUN mvn -q -pl blood-stock-service -am dependency:go-offline

COPY shared shared
COPY blood-stock-service blood-stock-service

RUN mvn -q -pl blood-stock-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/blood-stock-service/target/*.jar app.jar

EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]