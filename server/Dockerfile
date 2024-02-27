# Start with a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Server-0.0.1-SNAPSHOT.jar /app/Server-0.0.1-SNAPSHOT.jar

# Add PostgreSQL JDBC driver
ADD https://jdbc.postgresql.org/download/postgresql-42.2.24.jar /app/postgresql.jar

# Expose the port your application runs on
EXPOSE 9090

# Add a script to wait for SQL server and then list JAR file
COPY bootscript.sh /app/bootscript.sh
RUN chmod +x /app/bootscript.sh

# Command to run your application after SQL server is ready
CMD ["java", "-jar", "/app/Server-0.0.1-SNAPSHOT.jar"]

