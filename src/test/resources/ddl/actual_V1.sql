CREATE TABLE company (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE building (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR NOT NULL,
  description VARCHAR NOT NULL
);

CREATE TABLE ship_area (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR NOT NULL,
  description VARCHAR NOT NULL
);

CREATE TABLE person (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR NOT NULL,
  last_name VARCHAR NOT NULL,
  company BIGINT REFERENCES company(id),
  contact_information_email VARCHAR,
  contact_information_phone_number VARCHAR,
  confirmed BOOLEAN,
  token VARCHAR,
  password VARCHAR,
  key_code VARCHAR
);

CREATE TABLE ship (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR NOT NULL,
  description VARCHAR NOT NULL
);
