FROM eclipse-temurin:21-jdk-alpine

# Встановлюємо глобальний Maven
RUN apk add --no-cache maven

WORKDIR /app

# Копіюємо весь проєкт у контейнер
COPY . .

# Примусово виконуємо повне збирання та упаковку Spring Boot
RUN mvn clean package spring-boot:repackage -DskipTests

EXPOSE 8080

# Запускаємо виключно виконуваний jar-файл (ігноруючи службові файли)
CMD ["sh", "-c", "java -jar target/power-monitor-*.jar"]
