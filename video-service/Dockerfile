FROM openjdk:14-jdk-alpine AS root

EXPOSE 8190

RUN apk add --no-cache ffmpeg

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]