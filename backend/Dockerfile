# --- Build Stage ---
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Nur notwendige Dateien kopieren
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dependencies herunterladen (macht Build schneller)
RUN ./mvnw dependency:go-offline

# Jetzt den Source Code kopieren
COPY src src

# Build
RUN ./mvnw clean package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app

# Die erzeugte JAR kopieren
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
