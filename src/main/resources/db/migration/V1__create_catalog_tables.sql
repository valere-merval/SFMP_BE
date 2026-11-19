create table if not exists event_catalogs (
    id varchar(120) primary key,
    title varchar(255) not null,
    description text not null,
    image_url text not null,
    sport varchar(120) not null,
    country varchar(120) not null,
    badge varchar(64) not null,
    event_count integer not null default 0,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);

create table if not exists matches (
    id varchar(120) primary key,
    catalog_id varchar(120) not null references event_catalogs(id) on delete cascade,
    home_team varchar(255) not null,
    away_team varchar(255) not null,
    home_flag text not null,
    away_flag text not null,
    city varchar(120) not null,
    country varchar(120) not null,
    venue varchar(255) not null,
    stage varchar(120) not null,
    match_date date not null,
    time varchar(32) not null,
    status varchar(64) not null,
    gate varchar(64) not null,
    sector varchar(64) not null,
    countdown varchar(32) not null,
    weather varchar(64) not null,
    temperature varchar(64) not null,
    transit_summary text not null,
    ticket_price varchar(64) not null,
    image_url text not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);

create index if not exists idx_matches_catalog_id on matches(catalog_id);
