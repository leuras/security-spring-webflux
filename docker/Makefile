.PHONY: start stop clean

start: .env
	@@echo "Starting local environment"
	docker-compose down && docker-compose up -d

stop: .env
	@@echo "Stopping local environment"
	docker-compose down

.env:
	@@echo "Generating .env file"
	@@echo "TOKEN=$(call generate_secret, 64)" > .env
	@@echo "POSTGRES_PASSWORD=$(call generate_secret, 16)" >> .env
	@@echo "RO_DB_USER=challenge_app_ro" >> .env
	@@echo "RO_DB_PASS=$(call generate_secret, 16)" >> .env
	@@echo "DDL_DB_USER=challenge_app_ddl" >> .env
	@@echo "DDL_DB_PASS=$(call generate_secret, 16)" >> .env

clean:
	@@rm -f .env

generate_secret = $(shell tr -dc A-Za-z0-9_@^!+- < /dev/urandom | head -c $(1) | xargs)
