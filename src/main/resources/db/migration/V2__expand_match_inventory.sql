alter table sfmp.event_catalogs
    alter column event_count set default 0;

alter table sfmp.matches
    alter column ticket_price drop not null;

alter table sfmp.matches
    add column if not exists ticket_available boolean not null default false;

alter table sfmp.matches
    add column if not exists tickets_total integer;

alter table sfmp.matches
    add column if not exists tickets_remaining integer;