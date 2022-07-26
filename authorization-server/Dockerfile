FROM alpine:latest

RUN apk add --update-cache openjdk11 \
    && adduser -D authorization-server-app \
    && mkdir -p /app

COPY build/libs/authorization-server-*.jar /app/authorization-server.jar
COPY docker/entrypoint.sh /app/entrypoint.sh

RUN chown -R authorization-server-app /app && chmod +x /app/entrypoint.sh

USER authorization-server-app

WORKDIR /app

ENTRYPOINT ["./entrypoint.sh"]