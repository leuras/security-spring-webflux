CREATE TABLE client_credential (
    client_id varchar NOT NULL,
    client_secret varchar NOT NULL,
    description varchar NOT NULL,
    roles jsonb NOT NULL,
    status varchar NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NULL,
    CONSTRAINT client_credential_pk PRIMARY KEY (client_id)
)