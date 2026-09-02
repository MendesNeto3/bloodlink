TRUNCATE TABLE appointment;

ALTER TABLE appointment
ALTER COLUMN center_id TYPE uuid USING center_id::uuid;