# Comnata

Comnata is a co-watching movie app. This repository contains a server-side sources of Comnata.

## Architecture

The application implements a microservice architecture. The application contains:

- [eureka-server](eureka-server): Service for service discovery. Registered services are shown on its web frontend.
- [gateway-server](gateway-server): Gateway service to route requests based on [spring-cloud-gateway](https://github.com/spring-cloud/spring-cloud-gateway/).
- [main-service](main-service): Service for interaction with clients over the WebSocket protocol.
- [video-service](video-service): Service for processing video files.
- [docker](docker-compose.yml): Docker compose support.

## Related repositories

- [Frontend](https://github.com/Vakosta/ComnataDocumentation);
- [Documentation](https://github.com/Vakosta/ComnataDocumentation) in Latex.

## Contact

Send me a message in Telegram: [@Vakosta](https://t.me/Vakosta).

Or in VK: [/vakosta](https://vk.com/vakosta).

Or E-mail: [v.a.annenkov@ya.ru](mailto:v.a.annenkov@ya.ru).
