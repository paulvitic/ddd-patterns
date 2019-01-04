DROP sequence if EXISTS public.event_seq;

CREATE sequence public.event_seq start with 1 increment by 1;

DROP TABLE if EXISTS event_log;

CREATE TABLE event_log (
  sequence        BIGINT        PRIMARY KEY,
  event           VARCHAR(63)   NOT NULL,
  aggregate       VARCHAR(63)   NOT NULL,
  aggregate_id    VARCHAR(19)   NOT NULL,
  data            VARCHAR(2048) NOT NULL,
  timestamp       TIMESTAMP     NOT NULL
);

CREATE INDEX event_log_aggregate_index ON event_log(aggregate, aggregate_id);
