ALTER TABLE sfmp.ride_requests
    ADD COLUMN IF NOT EXISTS passenger_phone text;
