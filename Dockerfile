FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create runtime container
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV FIXER_API_KEY=dummy_key

EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]