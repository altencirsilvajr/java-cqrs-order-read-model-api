create table order_summaries (
  order_id uuid primary key,
  customer_id varchar(120) not null,
  item_count integer not null,
  total numeric(19,2) not null,
  source_event_id uuid not null,
  source_occurred_at timestamptz not null,
  projected_at timestamptz not null
);
create table processed_events (
  event_id uuid primary key,
  processed_at timestamptz not null
);
