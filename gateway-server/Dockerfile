FROM openjdk:14-jdk-alpine

EXPOSE 80

ENV EUREKA_URI=http://eureka-server:8761/eureka
# ENV WEBSOCKET_URI=http://websocket-server:8188
ENV MAIN_URI=http://main-service:8192
# ENV USER_URI=http://user-service:8191
ENV VIDEO_URI=http://video-service:8190

# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]