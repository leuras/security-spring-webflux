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

secrets/ro-database secrets/rw-database secrets/authorization-server:
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

	@@echo "DB_HOST=db" > secrets/authorization-server
	@@echo "DB_NAME=postgres" >> secrets/authorization-server
	@@echo "DB_USER=sts_app_ddl" >> secrets/authorization-server
	@@echo "DB_PASS=$(call generate_secret, 16)" >> secrets/authorization-server
	@@echo "ACCESS_TOKEN_SECRET=$(call generate_secret, 16)" >> secrets/authorization-server
	@@echo "REFRESH_TOKEN_SECRET=$(call generate_secret, 16)" >> secrets/authorization-server

clean:
	@@rm -f .env \
		secrets/ro-database \
		secrets/rw-database \
		secrets/authorization-server
		
	@@../supplier-service/gradlew -p ../supplier-service clean

generate_secret = $(shell tr -dc A-Za-z0-9_@^!+- < /dev/urandom | head -c $(1) | xargs)
