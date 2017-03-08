CREATE TABLE company (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE ship_area (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR NOT NULL,
  description VARCHAR NOT NULL
);

CREATE TABLE ship (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR NOT NULL,
  description VARCHAR NOT NULL
);

CREATE TABLE building (
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

CREATE TABLE notification (
  id BIGSERIAL PRIMARY KEY,
  status VARCHAR NOT NULL,
  yard_contact BIGINT NOT NULL REFERENCES person(id),
  site_foreman BIGINT NOT NULL REFERENCES person(id),
  additional_information VARCHAR NOT NULL,
  work_week_week_number INT NOT NULL,
  work_week_monday_start_time_hour INT,
  work_week_monday_start_time_minute INT,
  work_week_monday_end_time_hour INT,
  work_week_monday_end_time_minute INT,
  work_week_tuesday_start_time_hour INT,
  work_week_tuesday_start_time_minute INT,
  work_week_tuesday_end_time_hour INT,
  work_week_tuesday_end_time_minute INT,
  work_week_wednesday_start_time_hour INT,
  work_week_wednesday_start_time_minute INT,
  work_week_wednesday_end_time_hour INT,
  work_week_wednesday_end_time_minute INT,
  work_week_thursday_start_time_hour INT,
  work_week_thursday_start_time_minute INT,
  work_week_thursday_end_time_hour INT,
  work_week_thursday_end_time_minute INT,
  work_week_friday_start_time_hour INT,
  work_week_friday_start_time_minute INT,
  work_week_friday_end_time_hour INT,
  work_week_friday_end_time_minute INT,
  work_week_saturday_start_time_hour INT,
  work_week_saturday_start_time_minute INT,
  work_week_saturday_end_time_hour INT,
  work_week_saturday_end_time_minute INT,
  work_week_sunday_start_time_hour INT,
  work_week_sunday_start_time_minute INT,
  work_week_sunday_end_time_hour INT,
  work_week_sunday_end_time_minute INT
);

CREATE TABLE work_entry (
  id BIGSERIAL PRIMARY KEY,
  worker BIGINT NOT NULL REFERENCES person(id),
  location_building BIGINT NOT NULL REFERENCES building(id),
  location_ship BIGINT REFERENCES ship(id),
  location_ship_area BIGINT REFERENCES ship_area(id),
  occupation VARCHAR NOT NULL,
  energy_requirements_oxyacetylene BOOLEAN NOT NULL,
  energy_requirements_composite_gas BOOLEAN NOT NULL,
  energy_requirements_argon BOOLEAN NOT NULL,
  energy_requirements_compressed_air BOOLEAN NOT NULL,
  energy_requirements_hot_works BOOLEAN NOT NULL,
  notification BIGINT NOT NULL REFERENCES notification(id)
);

CREATE TABLE log_entry (
  id BIGSERIAL PRIMARY KEY,
  action VARCHAR NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by BIGINT NOT NULL REFERENCES person(id),
  notification BIGINT NOT NULL REFERENCES notification(id)
);
