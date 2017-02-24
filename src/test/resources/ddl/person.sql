CREATE TABLE person (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR NOT NULL,
  last_name VARCHAR NOT NULL,
  company BIGINT REFERENCES company(id),
  contact_information_email VARCHAR,
  contact_information_phone_number VARCHAR,
  key_code VARCHAR,
  confirmed BOOLEAN,
  token VARCHAR,
  password VARCHAR
);
