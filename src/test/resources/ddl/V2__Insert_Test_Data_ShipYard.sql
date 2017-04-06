INSERT INTO company (
  id,
  name
) VALUES (
  1 /* Ablemans */,
  'Ablemans'
), (
  2 /* Maersk */,
  'Maersk'
), (
  3 /* STX Group */,
  'STX Group'
);

INSERT INTO ship_area (
  id,
  code,
  description
) VALUES (
  1 /* Area 56 (Cabins) */,
  '56',
  'Cabins'
);

INSERT INTO ship (
  id,
  code,
  description
) VALUES (
  1 /* Ship 2 */,
  'N 2',
  'Ship 2'
), (
  2 /* Ship 3 */,
  'R 3',
  'Ship 3'
);

INSERT INTO building (
  id,
  code,
  description
) VALUES (
  1 /* Building 43 */,
  'B 43',
  'Assembly shed'
), (
  2 /* Building 44 */,
  'B 44',
  'Factory'
), (
  3 /* Building 45 */,
  'B 45',
  'Paint shop'
);

INSERT INTO person (
  id,
  first_name,
  last_name,
  company,
  contact_information_email,
  contact_information_phone_number,
  confirmed,
  token,
  password,
  key_code
) VALUES (
  1 /* Ville Peurala */,
  'Ville',
  'Peurala',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL
), (
  2 /* Tero Packalen */,
  'Tero',
  'Packalen',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL
), (
  3 /* Ville Peurala with contact information */,
  'Ville',
  'Peurala',
  SELECT id FROM company WHERE id = 3 /* STX Group */,
  'ville.peurala@mail.com',
  '050 - 352 7878',
  NULL,
  NULL,
  NULL,
  NULL
), (
  4 /* Tero Packalen with contact information */,
  'Tero',
  'Packalen',
  SELECT id FROM company WHERE id = 2 /* Maersk */,
  'tero.packalen@yard.com',
  '040 - 568 3313',
  NULL,
  NULL,
  NULL,
  NULL
), (
  5 /* ville.peurala@gmail.com */,
  'Ville',
  'Peurala',
  SELECT id FROM company WHERE id = 3 /* STX Group */,
  'ville.peurala@mail.com',
  '050 - 352 7878',
  true,
  '76696c6c652e70657572616c614077756e646572646f672e6670',
  NULL,
  NULL
), (
  6 /* tero.packalen@yard.com */,
  'Tero',
  'Packalen',
  SELECT id FROM company WHERE id = 2 /* Maersk */,
  'tero.packalen@yard.com',
  '040 - 568 3313',
  true,
  '76696c6c652e70657572616c614077756e646572646f672e6671',
  NULL,
  NULL
), (
  7 /* Jurij Andrejev */,
  'Jurij',
  'Andrejev',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  '4060'
), (
  8 /* Genadij Bogira */,
  'Genadij',
  'Bogira',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  '2332'
);

INSERT INTO notification (
  id,
  status,
  yard_contact,
  site_foreman,
  additional_information,
  work_week_week_number,
  work_week_monday_start_time_hour,
  work_week_monday_start_time_minute,
  work_week_monday_end_time_hour,
  work_week_monday_end_time_minute,
  work_week_tuesday_start_time_hour,
  work_week_tuesday_start_time_minute,
  work_week_tuesday_end_time_hour,
  work_week_tuesday_end_time_minute,
  work_week_wednesday_start_time_hour,
  work_week_wednesday_start_time_minute,
  work_week_wednesday_end_time_hour,
  work_week_wednesday_end_time_minute,
  work_week_thursday_start_time_hour,
  work_week_thursday_start_time_minute,
  work_week_thursday_end_time_hour,
  work_week_thursday_end_time_minute,
  work_week_friday_start_time_hour,
  work_week_friday_start_time_minute,
  work_week_friday_end_time_hour,
  work_week_friday_end_time_minute,
  work_week_saturday_start_time_hour,
  work_week_saturday_start_time_minute,
  work_week_saturday_end_time_hour,
  work_week_saturday_end_time_minute,
  work_week_sunday_start_time_hour,
  work_week_sunday_start_time_minute,
  work_week_sunday_end_time_hour,
  work_week_sunday_end_time_minute
) VALUES (
  1 /* Notification 1 */,
  'Approved',
  SELECT id FROM person WHERE id = 4 /* Tero Packalen with contact information */,
  SELECT id FROM person WHERE id = 3 /* Ville Peurala with contact information */,
  'Sunday work is needed because we are behind schedule.',
  48,
  10,
  15,
  18,
  30,
  NULL,
  NULL,
  NULL,
  NULL,
  7,
  0,
  16,
  20,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  10,
  0,
  14,
  0
);

INSERT INTO work_entry (
  id,
  worker,
  location_building,
  location_ship,
  location_ship_area,
  occupation,
  energy_requirements_oxyacetylene,
  energy_requirements_composite_gas,
  energy_requirements_argon,
  energy_requirements_compressed_air,
  energy_requirements_hot_works,
  notification
) VALUES (
  1 /* Jurij as a welder in building 44 / ship 2 */,
  SELECT id FROM person WHERE id = 7 /* Jurij Andrejev */,
  SELECT id FROM building WHERE id = 2 /* Building 44 */,
  SELECT id FROM ship WHERE id = 1 /* Ship 2 */,
  NULL,
  'Welder',
  true,
  false,
  true,
  false,
  true,
  SELECT id FROM notification WHERE id = 1 /* Notification 1 */
), (
  2 /* Genadij as a fitter in building 43 */,
  SELECT id FROM person WHERE id = 8 /* Genadij Bogira */,
  SELECT id FROM building WHERE id = 1 /* Building 43 */,
  NULL,
  NULL,
  'Fitter',
  false,
  false,
  false,
  true,
  false,
  SELECT id FROM notification WHERE id = 1 /* Notification 1 */
);

INSERT INTO log_entry (
  id,
  action,
  created_at,
  created_by,
  notification
) VALUES (
  1 /* Creation of notification 1 */,
  'Create',
  '2015-01-16 21:59:00',
  SELECT id FROM person WHERE id = 6 /* tero.packalen@yard.com */,
  SELECT id FROM notification WHERE id = 1 /* Notification 1 */
), (
  2 /* Sending of notification 1 */,
  'Send',
  '2015-01-16 22:05:00',
  SELECT id FROM person WHERE id = 6 /* tero.packalen@yard.com */,
  SELECT id FROM notification WHERE id = 1 /* Notification 1 */
);
