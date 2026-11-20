create table if not exists sfmp.transport_profiles (
    id varchar(128) primary key,
    owner_email varchar(255) not null unique,
    owner_name varchar(255) not null,
    provider_type varchar(64) not null,
    display_name varchar(255) not null,
    driver_name varchar(255) not null,
    vehicle_label varchar(255) not null,
    vehicle_plate varchar(128) not null,
    city varchar(255) not null,
    country varchar(255) not null,
    zone varchar(255) not null,
    permit_url varchar(2048),
    registration_url varchar(2048),
    driver_license_url varchar(2048),
    vehicle_image_url varchar(2048),
    notes varchar(2048),
    public_notes varchar(2048),
    active boolean not null default true,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists sfmp.transport_profile_availabilities (
    id varchar(128) primary key,
    profile_id varchar(128) not null references sfmp.transport_profiles(id) on delete cascade,
    match_id varchar(128) not null,
    available boolean not null default true,
    ready boolean not null default false,
    status varchar(64) not null,
    notes varchar(2048),
    created_at timestamp not null,
    updated_at timestamp not null,
    unique(profile_id, match_id)
);
