FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY api-gateway/pom.xml api-gateway/
COPY appointment-service/pom.xml appointment-service/
COPY auth-service/pom.xml auth-service/
COPY blood-stock-service/pom.xml blood-stock-service/
COPY checkin-service/pom.xml checkin-service/
COPY donation-center-service/pom.xml donation-center-service/
COPY eligibility-service/pom.xml eligibility-service/
COPY notification-service/pom.xml notification-service/

RUN mvn -q -pl auth-service -am dependency:go-offline

COPY auth-service auth-service

RUN mvn -q -pl auth-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/auth-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]