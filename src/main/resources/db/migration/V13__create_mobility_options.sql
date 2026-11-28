create table if not exists sfmp.mobility_options (
    id varchar(128) primary key,
    mode varchar(64) not null,
    duration varchar(64) not null,
    price varchar(64) not null,
    status varchar(80) not null,
    highlight varchar(255) not null,
    station_address varchar(2048) not null,
    latitude double precision,
    longitude double precision,
    asset_type varchar(64) not null,
    created_at timestamp not null default current_timestamp
);

create index if not exists idx_mobility_options_mode on sfmp.mobility_options (mode);
create index if not exists idx_mobility_options_asset_type on sfmp.mobility_options (asset_type);
