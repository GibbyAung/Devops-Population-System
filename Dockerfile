FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copy the compiled Java application
COPY target/*-jar-with-dependencies.jar app.jar

# Copy the database folder containing world.sql into the container
COPY database/world.sql /app/database/world.sql

ENTRYPOINT ["java", "-jar", "app.jar"]