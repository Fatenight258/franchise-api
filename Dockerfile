# build
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# runtime
FROM eclipse-temurin:21-jre-alpine
RUN addgroup --system app && adduser --system --ingroup app app
WORKDIR /app
COPY --from=builder /workspace/target/franchise-api.jar app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]