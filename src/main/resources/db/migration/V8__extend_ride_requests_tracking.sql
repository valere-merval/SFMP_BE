ALTER TABLE sfmp.ride_requests
    ADD COLUMN IF NOT EXISTS driver_vehicle_plate varchar(255),
    ADD COLUMN IF NOT EXISTS driver_latitude double precision,
    ADD COLUMN IF NOT EXISTS driver_longitude double precision,
    ADD COLUMN IF NOT EXISTS driver_distance_km double precision,
    ADD COLUMN IF NOT EXISTS driver_eta_minutes integer,
    ADD COLUMN IF NOT EXISTS journey_distance_km double precision,
    ADD COLUMN IF NOT EXISTS journey_eta_minutes integer,
    ADD COLUMN IF NOT EXISTS pickup_reached_at timestamp,
    ADD COLUMN IF NOT EXISTS trip_started_at timestamp,
    ADD COLUMN IF NOT EXISTS trip_completed_at timestamp;
