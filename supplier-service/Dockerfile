FROM alpine:latest

RUN apk add --update-cache openjdk11 \
    && adduser -D supplier \
    && mkdir -p /app

COPY build/libs/supplier-service-*.jar /app/supplier-service.jar
COPY docker/entrypoint.sh /app/entrypoint.sh

RUN chown -R supplier /app && chmod +x /app/entrypoint.sh

USER supplier

WORKDIR /app

ENTRYPOINT ["./entrypoint.sh"]