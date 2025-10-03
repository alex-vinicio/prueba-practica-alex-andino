# ===========================
# Etapa 1: build con Gradle
# ===========================
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app

# Copiar los archivos de Gradle primero (cache de dependencias)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Descargar dependencias para cachear
RUN ./gradlew build --no-daemon -x test || return 0

# Copiar el código fuente
COPY src ./src

# Compilar el jar
RUN ./gradlew clean bootJar --no-daemon

# ===========================
# Etapa 2: runtime con JDK ligero
# ===========================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/build/libs/*.jar account_service.jar

EXPOSE 8095

ENTRYPOINT ["java", "-jar", "account_service.jar"]

#docker build -t springboot-app:latest .