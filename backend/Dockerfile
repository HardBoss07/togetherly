# Use Maven + JDK base image for dev (includes mvnw)
FROM maven:3.8.6-openjdk-17-slim

WORKDIR /app

# Copy only pom.xml and mvnw to leverage Docker cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw

# Copy source code
COPY src ./src

# Install inotify-tools for better file watch (optional, Linux only)
RUN apt-get update && apt-get install -y inotify-tools

# Use maven to run Spring Boot with DevTools enabled
CMD ["./mvnw", "spring-boot:run"]
