create table if not exists sfmp.behavior_events (
    id varchar(64) primary key,
    user_id varchar(255) not null,
    event_type varchar(255) not null,
    screen varchar(255) not null,
    payload_json text not null,
    created_at timestamp not null default current_timestamp
);

create index if not exists idx_behavior_events_created_at on sfmp.behavior_events(created_at desc);
create index if not exists idx_behavior_events_user_id on sfmp.behavior_events(user_id);
