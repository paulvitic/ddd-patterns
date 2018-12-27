CREATE TABLE customer (
  id                  BIGINT         PRIMARY KEY,
  version             INTEGER        NOT NULL,
  status              VARCHAR(15)    NOT NULL,
  first_name          VARCHAR(63)    NOT NULL,
  last_name           VARCHAR(63)    NOT NULL,
  date_of_birth       TIMESTAMP      NOT NULL
);

CREATE INDEX customer_status_index
  ON customer(status);

-- indexes?