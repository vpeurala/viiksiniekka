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

<example name="Ville Peurala">
                    <fieldvalue field="firstName">Ville</fieldvalue>
                    <fieldvalue field="lastName">Peurala</fieldvalue>
                </example>
                <example name="Tero Packalen">
                    <fieldvalue field="firstName">Tero</fieldvalue>
                    <fieldvalue field="lastName">Packalen</fieldvalue>
                </example>

INSERT INTO person (first_name,
                    last_name,
                    company,
                    contact_information_email,
                    contact_information_phone_number,
                    confirmed,
                    token,
                    password,
                    key_code) VALUES ()