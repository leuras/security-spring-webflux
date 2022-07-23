CREATE TABLE bank_detail (
	customer_reference varchar NOT NULL,
	account_number varchar NOT NULL,
	credit_card_number varchar NOT NULL,
	created_at timestamp NOT NULL,
    updated_at timestamp NULL,
	CONSTRAINT bank_detail_pk PRIMARY KEY (customer_reference)
);
