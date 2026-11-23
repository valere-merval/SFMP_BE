create table if not exists sfmp.auth_accounts (
    id varchar(64) primary key,
    email varchar(255) not null unique,
    full_name varchar(255) not null,
    role varchar(64) not null,
    organization varchar(255) not null,
    roles_csv varchar(512) not null,
    password_hash varchar(255) not null
);
