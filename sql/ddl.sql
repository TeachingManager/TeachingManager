drop table if exists schedule CASCADE;
drop table if exists teacher CASCADE;

create table schedule
(
 schedule_id bigint generated by default as identity,
 title varchar(255),
start_time TIMESTAMP,
end_time TIMESTAMP,
memo varchar(255),
 primary key (schedule_id)
 );

 create table teacher