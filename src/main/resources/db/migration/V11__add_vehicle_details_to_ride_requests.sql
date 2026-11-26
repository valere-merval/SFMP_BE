ALTER TABLE ride_requests
    ADD COLUMN IF NOT EXISTS driver_vehicle_label VARCHAR(255),
    ADD COLUMN IF NOT EXISTS driver_vehicle_image_url TEXT;
