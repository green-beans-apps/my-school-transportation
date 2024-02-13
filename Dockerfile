FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY target/my-school-transportation-1.0.0.jar my-school-transportation-1.0.0.jar

EXPOSE 8080

CMD ["java", "--jar", "my-school-transportation-1.0.0.jar"]