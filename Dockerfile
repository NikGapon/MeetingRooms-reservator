# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim
LABEL authors="Nikola"
# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file to the container
COPY build/libs/MeetingRoomsReservator-0.0.2.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]