create table phone_number_process (
  id                      varchar(255)    not null,
  matched_phone_numbers   integer         not null,
  total_phone_numbers     integer         not null,
  primary key (id)
);
