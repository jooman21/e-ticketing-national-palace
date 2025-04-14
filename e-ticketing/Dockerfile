# ----------- Build Stage ------------
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

# ----------- Runtime Stage ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy only the JAR from the builder image
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
