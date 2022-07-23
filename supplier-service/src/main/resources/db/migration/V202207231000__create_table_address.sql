CREATE TABLE address (
	customer_reference varchar NOT NULL,
	address varchar NOT NULL,
	zip_code varchar NOT NULL,
	region point NOT NULL,
	created_at timestamp NOT NULL,
    updated_at timestamp NULL,
	CONSTRAINT address_pk PRIMARY KEY (customer_reference)
);