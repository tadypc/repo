FROM eclipse-temurin:21-jdk-alpine

# Встановлюємо Maven
RUN apk add --no-cache maven

WORKDIR /app

# Копіюємо весь проєкт у контейнер
COPY . .

EXPOSE 8080

# Запускаємо додаток напряму через Maven без попереднього пакування в jar
CMD ["mvn", "spring-boot:run"]
