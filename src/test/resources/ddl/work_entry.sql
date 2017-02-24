CREATE TABLE work_entry (
  id BIGSERIAL PRIMARY KEY,
  worker BIGINT REFERENCES person(id) NOT NULL,
  location_building BIGINT REFERENCES building(id) NOT NULL,
  location_ship BIGINT REFERENCES ship(id),
  location_ship_area BIGINT REFERENCES ship_area(id),
  occupation VARCHAR NOT NULL,
  energy_requirements_oxyacetylene BOOLEAN NOT NULL,
  energy_requirements_composite_gas BOOLEAN NOT NULL,
  energy_requirements_argon BOOLEAN NOT NULL,
  energy_requirements_compressed_air BOOLEAN NOT NULL,
  energy_requirements_hot_works BOOLEAN NOT NULL,
  notification BIGINT REFERENCES notification(id) NOT NULL
);
