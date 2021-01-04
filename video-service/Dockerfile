FROM openjdk:14-jdk-alpine AS root

# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring

RUN apk add --no-cache ffmpeg

WORKDIR /app

RUN apk update && \
    apk add --no-cache \
        wget \
        g++ \
        make \
        && \
    wget --no-check-certificate https://codeload.github.com/gpac/gpac/zip/master -O gpac-master.zip && \
    unzip gpac-master.zip

WORKDIR gpac-master

RUN ./configure --use-zlib=no && \
    make && \
    mkdir -p install/bin && \
    cp -R ./bin/gcc ./install/lib && \
    rm ./install/lib/gm_* ./install/lib/*.a && \
    rm -Rf ./install/lib/temp && \
    mv ./install/lib/MP4* ./install/bin

FROM openjdk:14-jdk-alpine

WORKDIR /app

COPY --from=root /app/gpac-master/install/lib /usr/lib
COPY --from=root /app/gpac-master/install/bin /usr/bin

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]