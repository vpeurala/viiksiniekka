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
