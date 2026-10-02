FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

# Копіюємо весь проєкт у контейнер
COPY . .

EXPOSE 8080

# Спочатку компілюємо проєкт, а потім запускаємо
CMD ["mvn", "compile", "spring-boot:run"]
