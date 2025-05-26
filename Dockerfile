# Use a slim OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper files to enable building inside the container
# This includes the wrapper script and its configuration files
COPY gradlew .
COPY gradle gradle

# Copy the Gradle build files
# These are crucial for dependency resolution and project structure
COPY build.gradle .
COPY settings.gradle .

# Download project dependencies
# This step leverages Docker's layer caching. If build.gradle or settings.gradle
# don't change, this layer will be reused, speeding up subsequent builds.
# --no-daemon is used to prevent Gradle from running as a daemon inside the container,
# which is generally not needed for Docker builds and can cause issues.
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the application source code
COPY src src

# Build the application into a JAR file
# -x test is used to skip running tests during the Docker build.
# It's often better to run tests in a separate CI/CD stage.
RUN ./gradlew build -x test --no-daemon

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Command to run the application
# Assumes the JAR file is named shopping-cart-service-0.0.1-SNAPSHOT.jar
# Adjust the JAR name if your project's version changes.
ENTRYPOINT ["java", "-jar", "build/libs/CartCheckout-0.0.1-SNAPSHOT.jar"]
