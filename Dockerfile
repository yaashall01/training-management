# Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:go-offline -B
COPY src /app/src
RUN mvn clean package -DskipTests

# Run Stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/training-management-0.0.1-SNAPSHOT.jar /app/training-management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/training-management.jar"]
