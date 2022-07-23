CREATE TABLE customer (
	customer_reference varchar NOT NULL,
	external_id varchar NOT NULL,
	avatar_url varchar NOT NULL,
	birthday date NOT NULL,
	metadata jsonb NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT customer_pk PRIMARY KEY (customer_reference),
	CONSTRAINT customer_uk UNIQUE (external_id)
);
