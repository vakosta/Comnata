FROM openjdk:14-jdk-alpine

EXPOSE 8188

# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]