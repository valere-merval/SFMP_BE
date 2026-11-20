create table if not exists sfmp.user_profiles (
    email varchar(255) primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    country varchar(255) not null,
    city varchar(255) not null,
    phone_prefix varchar(32) not null,
    phone varchar(64) not null,
    preferred_language varchar(32) not null,
    photo_url text,
    created_at timestamp not null,
    updated_at timestamp not null
);
