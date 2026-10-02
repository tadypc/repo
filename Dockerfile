FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

# Примусово збираємо та пакуємо через Spring Boot плагін
RUN mvn clean package spring-boot:repackage -DskipTests

EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
