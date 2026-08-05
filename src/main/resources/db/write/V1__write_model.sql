create table orders (
  id uuid primary key,
  customer_id varchar(120) not null,
  total numeric(19,2) not null,
  item_count integer not null,
  placed_at timestamptz not null
);
create table outbox_events (
  id uuid primary key,
  aggregate_id uuid not null references orders(id),
  event_type varchar(120) not null,
  payload text not null,
  occurred_at timestamptz not null,
  published_at timestamptz null
);
create index ix_outbox_pending on outbox_events (occurred_at) where published_at is null;
