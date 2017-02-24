CREATE TABLE log_entry (
  id BIGSERIAL PRIMARY KEY,
  action VARCHAR NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by BIGINT REFERENCES person(id) NOT NULL,
  notification BIGINT REFERENCES notification(id) NOT NULL
);
