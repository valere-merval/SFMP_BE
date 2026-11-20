create table if not exists sfmp.transport_providers (
    id varchar(128) primary key,
    match_id varchar(128) not null,
    provider_type varchar(64) not null,
    display_name varchar(200) not null,
    driver_name varchar(200) not null,
    vehicle_label varchar(200) not null,
    vehicle_plate varchar(80) not null,
    city varchar(160) not null,
    country varchar(160) not null,
    zone varchar(160) not null,
    status varchar(80) not null,
    active boolean not null,
    permit_url varchar(2048),
    registration_url varchar(2048),
    driver_license_url varchar(2048),
    vehicle_image_url varchar(2048),
    notes varchar(2048),
    public_notes varchar(2048),
    created_at timestamp not null default current_timestamp
);

create index if not exists idx_transport_providers_match_id on sfmp.transport_providers (match_id);
create index if not exists idx_transport_providers_active on sfmp.transport_providers (active);
