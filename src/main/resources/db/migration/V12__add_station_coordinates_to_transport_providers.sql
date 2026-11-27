ALTER TABLE sfmp.transport_providers ADD COLUMN IF NOT EXISTS station_address varchar(2048) NOT NULL DEFAULT '';
ALTER TABLE sfmp.transport_providers ADD COLUMN IF NOT EXISTS station_latitude double precision;
ALTER TABLE sfmp.transport_providers ADD COLUMN IF NOT EXISTS station_longitude double precision;
