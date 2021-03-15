#
# Build stage
#
FROM maven:3.6-jdk-8-slim AS build
COPY src /build/src
COPY pom.xml /build/
RUN mvn -f /build/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-alpine
COPY --from=build /build/target/*.jar /usr/local/app/app.jar
ENTRYPOINT ["java","-jar","/usr/local/app/app.jar"]
