FROM alpine:latest

RUN apk add --update-cache openjdk11 \
    && adduser -D customer-app \
    && mkdir -p /app

COPY build/libs/customer-service-*.jar /app/customer-service.jar
COPY docker/entrypoint.sh /app/entrypoint.sh

RUN chown -R customer-app /app && chmod +x /app/entrypoint.sh

USER customer-app

WORKDIR /app

ENTRYPOINT ["./entrypoint.sh"]