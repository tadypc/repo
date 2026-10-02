FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Копіюємо весь проєкт у контейнер
COPY . .

# Даємо права на виконання для mvnw
RUN chmod +x mvnw

# Збираємо проєкт за допомогою Maven (без тестів)
RUN ./mvnw clean package -DskipTests

# Запускаємо зібраний jar-файл
EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
