FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Копіюємо Maven обгортку та конфігурацію
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Завантажуємо залежності
RUN ./mvnw dependency:go-offline -B

# Копіюємо решту вихідного коду
COPY src src

# Збираємо проєкт без тестів
RUN ./mvnw package -DskipTests

# Запускаємо зібраний jar-файл
EXPOSE 8080
CMD ["sh", "-c", "java -jar target/*.jar"]
