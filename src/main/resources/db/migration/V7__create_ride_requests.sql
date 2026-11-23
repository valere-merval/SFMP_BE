create table if not exists sfmp.ride_requests (
    id uuid primary key,
    match_id text not null,
    match_title text not null,
    passenger_email text not null,
    passenger_name text not null,
    provider_type text not null,
    pickup_label text not null,
    pickup_latitude double precision,
    pickup_longitude double precision,
    destination_label text not null,
    destination_latitude double precision,
    destination_longitude double precision,
    status text not null,
    driver_profile_id text,
    driver_display_name text,
    driver_phone text,
    response_note text,
    note text,
    created_at timestamp not null,
    updated_at timestamp not null
);

create index if not exists idx_ride_requests_passenger_email on sfmp.ride_requests (passenger_email, created_at desc);
create index if not exists idx_ride_requests_match_provider_status on sfmp.ride_requests (match_id, provider_type, status, created_at desc);
