FROM eclipse-temurin:21-jdk-alpine

# Встановлюємо Maven напряму через пакетний менеджер Alpine Linux
RUN apk add --no-cache maven

WORKDIR /app

# Копіюємо весь проєкт у контейнер
COPY . .

# Збираємо проєкт за допомогою глобального mvn безпосередньо
RUN mvn clean package -DskipTests

# Запускаємо зібраний jar-файл
EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
