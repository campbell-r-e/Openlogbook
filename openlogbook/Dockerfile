# Use Debian-based OpenJDK 8
FROM openjdk:8-jdk

# Set working directory inside the container
WORKDIR /openlogbook

# Install missing X11 dependencies for Swing/AWT
RUN apt-get update && apt-get install -y \
    unzip bash libc6 libstdc++6 libxrender1 libxtst6 libxi6 libxt6 libxext6 x11-apps

# Copy project files
COPY . .

# Ensure Gradle wrapper is executable
RUN chmod +x ./gradlew

# Disable Gradle Daemon to avoid issues in Docker
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

# Build the project, skipping tests
RUN ./gradlew build -x test --no-daemon

# Set correct working directory to where the JAR is located
WORKDIR /openlogbook/openlogbook/openlogbook/release

# Set Java to headless mode (if needed)
ENV _JAVA_OPTIONS="-Djava.awt.headless=false"

# Run the Java GUI application
CMD ["java", "-jar", "openlogbook-1.0.jar"]
