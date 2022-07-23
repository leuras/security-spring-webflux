.PHONY: start stop build clean

start: .env secrets/ro-database secrets/rw-database
	@@echo "Starting local environment"
	docker-compose down && docker-compose up -d

stop: .env secrets/ro-database secrets/rw-database
	@@echo "Stopping local environment"
	docker-compose down

build:
	@@echo "Building supplier-service application"
	@@../supplier-service/gradlew -p ../supplier-service clean build
	@@echo "Building supplier-service image"
	@@docker build ../supplier-service -t suplier-service:latest

.env:
	@@echo "Generating .env file"
	@@echo "TOKEN=$(call generate_secret, 64)" > .env
	@@echo "POSTGRES_PASSWORD=$(call generate_secret, 16)" >> .env

secrets/ro-database secrets/rw-database:
	@@mkdir -p secrets

	@@echo "Generating secrets"
	@@echo "DB_HOST=db" > secrets/ro-database
	@@echo "DB_NAME=postgres" >> secrets/ro-database
	@@echo "DB_USER=challenge_app_ro" >> secrets/ro-database
	@@echo "DB_PASS=$(call generate_secret, 16)" >> secrets/ro-database

	@@echo "DB_HOST=db" > secrets/rw-database
	@@echo "DB_NAME=postgres" >> secrets/rw-database
	@@echo "DB_USER=challenge_app_ddl" >> secrets/rw-database
	@@echo "DB_PASS=$(call generate_secret, 16)" >> secrets/rw-database

clean:
	@@rm -f .env secrets/ro-database secrets/rw-database
	@@../supplier-service/gradlew -p ../supplier-service clean

generate_secret = $(shell tr -dc A-Za-z0-9_@^!+- < /dev/urandom | head -c $(1) | xargs)
