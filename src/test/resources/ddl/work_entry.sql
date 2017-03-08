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
