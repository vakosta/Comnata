# Comnata

Comnata is a co-watching movie app. This repository contains a server-side sources of Comnata.

To use client click <a href="https://comnata.herokuapp.com/start" target="_blank">here</a>.

## Getting Started

Process of installation is very easy.

### Installation and Running

1. Download and install Docker from <a href="https://www.docker.com/products/docker-desktop" target="_blank">here</a>.
1. Download a Comnata source code of the latest release
   from <a href="https://github.com/Vakosta/Comnata/releases" target="_blank">here</a>.
1. In the root of downloaded source code run command `docker-compose up`.

### Uploading Videos

_Haven't written yet._

### Getting a Server URL

_Haven't written yet._

## Architecture

The application implements a microservice architecture. The application contains:

- [eureka-server](eureka-server): Service for service discovery. Registered services are shown on its web frontend.
- [gateway-server](gateway-server): Gateway service to route requests based on [spring-cloud-gateway](https://github.com/spring-cloud/spring-cloud-gateway/).
- [main-service](main-service): Service for interaction with clients over the WebSocket protocol.
- [video-service](video-service): Service for processing video files.
- [docker](docker-compose.yml): Docker compose support.

## Related Repositories

- [Web Frontend](https://github.com/Vakosta/ComnataDocumentation);
- [Documentation](https://github.com/Vakosta/ComnataDocumentation) in Latex.

## Contact

Send me a message in Telegram: [@Vakosta](https://t.me/Vakosta).

Or in VK: [/vakosta](https://vk.com/vakosta).

Or E-mail: [v.a.annenkov@ya.ru](mailto:v.a.annenkov@ya.ru).
