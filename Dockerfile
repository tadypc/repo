FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

# Копіюємо весь проєкт у контейнер
COPY . .

# Збираємо звичайний jar-пакет (без тестів)
RUN mvn clean package -DskipTests

EXPOSE 8080

# Запускаємо безпосередньо через java з маскою шуканого jar-файлу
CMD ["sh", "-c", "java -jar target/*.jar"]
