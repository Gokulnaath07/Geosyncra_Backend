# Use an official Maven image with JDK 21 to build the app
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a lightweight Java runtime with JDK 21 for the final image
FROM eclipse-temurin:21-jre-jammy

# Set the working directory for the runtime container
WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables (these will be overridden by Railway's environment variables)
ENV DB_URL=jdbc:postgresql://localhost:5432/GeoSyncra
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=yourpassword
ENV PORT=5000

# Expose the application's port
EXPOSE 5000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]