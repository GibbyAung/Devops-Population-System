# Population Report Command Reference

This document contains the application commands for the country and city reports released on `master`.

Run every command from the repository root, where `pom.xml` and `docker-compose.yaml` are located.

## Build and Start MySQL

Package the dependency-inclusive JAR before building the application image:

```shell
mvn clean test
mvn clean package
docker compose up -d mysql
docker compose build app
docker compose ps
```

After changing Java code, repeat both `mvn clean package` and `docker compose build app`. Otherwise Docker may run an older JAR.

## Country Reports

### Requirement 1: Countries in the World

Requirement 1 uses no application argument. With Compose MySQL running and port `3306` available, execute the JAR locally:

```shell
DB_HOST=localhost java -jar target/*-jar-with-dependencies.jar
```

### Requirements 2-6

```shell
# Requirement 2: countries in a continent
docker compose run --rm app Asia

# Requirement 3: countries in a region
docker compose run --rm app region "Eastern Asia"

# Requirement 4: Top-N countries in the world
docker compose run --rm app top-n 10

# Requirement 5: Top-N countries in a continent
docker compose run --rm app continent-top-n Asia 10

# Requirement 6: Top-N countries in a region
docker compose run --rm app region-top-n "Eastern Asia" 10
```

## City Reports

```shell
# Requirement 7: all cities in the world
docker compose run --rm app city-world

# Requirement 8: cities in a continent
docker compose run --rm app city-continent Asia

# Requirement 9: cities in a region
docker compose run --rm app city-region "Eastern Asia"

# Requirement 10: cities in a country (ISO country code)
docker compose run --rm app city-country CHN

# Requirement 11: cities in a district
docker compose run --rm app city-district California

# Requirement 12: Top-N cities in the world
docker compose run --rm app city-top-world 10

# Requirement 13: Top-N cities in a continent
docker compose run --rm app city-top-continent Asia 10

# Requirement 14: Top-N cities in a region
docker compose run --rm app city-top-region "Eastern Asia" 10

# Requirement 15: Top-N cities in a country (ISO country code)
docker compose run --rm app city-top-country CHN 10

# Requirement 16: Top-N cities in a district
docker compose run --rm app city-top-district California 10
```

## Controlled Checks

Use these examples to demonstrate validation and empty-result handling:

```shell
# Unknown scope returns an empty report without crashing
docker compose run --rm app city-continent Atlantis

# Invalid Top-N value returns an error and a non-zero exit
docker compose run --rm app city-top-world 0
```

## Stop Docker

```shell
docker compose down
```

Do not use `docker compose down -v` unless intentionally deleting and rebuilding the local MySQL volume.
