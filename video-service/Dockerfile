FROM openjdk:14-jdk-alpine AS root

EXPOSE 8190

ENV EUREKA_URI=http://eureka-server:8761/eureka

RUN apk add --no-cache htop
RUN apk add --no-cache ffmpeg

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]