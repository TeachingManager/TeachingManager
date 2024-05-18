drop table if exists schedule CASCADE;
drop table if exists teacher CASCADE;
drop table if exists institute CASCADE;

create table schedule
(
 schedule_id bigint generated by default as identity,
 title varchar(255),
start_time TIMESTAMP,
end_time TIMESTAMP,
memo varchar(255),
 primary key (schedule_id)
 );



 create table institute
 (
  institute_id bigint generated by default as identity,
  email varchar(255) UNIQUE NOT NULL,
  institute_name varchar(255) NOT NULL,
  phoneNum varchar(255) NOT NULL,
  address varchar(255) UNIQUE,

  primary key (institute_id)
 );

create table teacher
(
 teacher_id bigint generated by default as identity,
 teacher_name varchar(255) NOT NULL,
 age tinyint NOT NULL,
 birth TIMESTAMP NOT NULL,
 phoneNum varchar(255) NOT NULL,
 gender char(1) NOT NULL,
 bank_account  varchar(255) NOT NULL,
 salary bigint NOT NULL,
 email varchar(255) UNIQUE NOT NULL,

 institute_id bigint,

 FOREIGN KEY (institute_id) REFERENCES institute (institute_id) ON DELETE SET NULL,
 primary key (teacher_id)
);




