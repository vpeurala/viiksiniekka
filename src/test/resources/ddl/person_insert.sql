INSERT INTO person (
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
  'Ville',
  'Peurala',
  NULL,
  'ville.peurala@mail.com',
  '050 - 352 7878',
  NULL,
  NULL,
  NULL,
  NULL
), (
  'Tero',
  'Packalen',
  NULL,
  'tero.packalen@yard.com',
  '040 - 568 3313',
  NULL,
  NULL,
  NULL,
  NULL
), (
  'Ville',
  'Peurala',
  SELECT id FROM TODO,
  'ville.peurala@mail.com',
  '050 - 352 7878',
  'true',
  '76696c6c652e70657572616c614077756e646572646f672e6670',
  NULL,
  NULL
), (
  'Tero',
  'Packalen',
  SELECT id FROM TODO,
  'tero.packalen@yard.com',
  '040 - 568 3313',
  'true',
  '76696c6c652e70657572616c614077756e646572646f672e6671',
  NULL,
  NULL
), (
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
