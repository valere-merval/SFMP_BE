ALTER TABLE sfmp.ride_requests
    ADD COLUMN IF NOT EXISTS driver_vehicle_label VARCHAR(255);

ALTER TABLE sfmp.ride_requests
    ADD COLUMN IF NOT EXISTS driver_vehicle_image_url TEXT;
