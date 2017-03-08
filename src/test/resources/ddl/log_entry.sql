CREATE TABLE log_entry (
  id BIGSERIAL PRIMARY KEY,
  action VARCHAR NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by BIGINT NOT NULL REFERENCES person(id),
  notification BIGINT NOT NULL REFERENCES notification(id)
);
