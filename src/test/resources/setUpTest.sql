insert into institute
values ('bf78c61b-eb1e-46e6-be19-2fd99ee7140c', 1, 'ROLE_PRESIDENT', 'asd123@gmail.com', 1, 0, '$2a$10$p7aWpCkqZS51R0/bzl79quWDS783zQ2Lswnn.k/FGzPOsgCvJix9a', '서울시 마포구', 'asd', '01012345678');

insert into teacher
values ('0dda6019-71af-478c-bd93-0cc55517cbe0', 1, 'ROLE_TEACHER', 'qwe123@gmail.com', 1, 0, '$2a$10$buQVLPUj9r77XaJHQ/n0cOsAwr4GZk1WV9Lm4kAGdnhzAUWWqAZMy', 23, '123', '2000-02-03', 'M', 'qwe', '01012345678', 'Local', '123', 'qwe', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c');

insert into teacher
values ('f1184991-7942-411e-a2fe-6c23f996aeb8', 1, 'ROLE_TEACHER', 'zxc123@gmail.com', 1, 0, '$2a$10$buQVLPUj9r77XaJHQ/n0cOsAwr4GZk1WV9Lm4kAGdnhzAUWWqAZMy', 23, '123', '2000-02-03', 'M', 'zxc', '01012345678', 'Local', '123', 'zxc', NULL);

--insert into teacher (pk, account_non_locked, authorities, email, enabled, failed_count, password, age, bank_account, birth, gender, nickname, phone_number, provider, salary, teacher_name)
--values ('f1184991-7942-411e-a2fe-6c23f996aeb8', 1, 'ROLE_TEACHER', 'zxc123@gmail.com', 1, 0, '$2a$10$buQVLPUj9r77XaJHQ/n0cOsAwr4GZk1WV9Lm4kAGdnhzAUWWqAZMy', 23, '123', '2000-02-03', 'M', 'zxc', '01012345678', 'Local', '123', 'zxc');

alter table lecture alter column lecture_id restart with 1;
insert into lecture (category, fee, grade, name, time, institute_id, teacher_id)
values ('수학', 100000, '상', '미적', 'TUESDAY-12:30~13:20', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c', '0dda6019-71af-478c-bd93-0cc55517cbe0');
insert into lecture (category, fee, grade, name, time, institute_id, teacher_id)
values ('수학', 100000, '상', '미적11', 'TUESDAY-12:30~13:20', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c', '0dda6019-71af-478c-bd93-0cc55517cbe0');

alter table schedule alter column schedule_id restart with 1;
insert into schedule (end_date, memo, start_date, title, institute_id)
values ('2024-08-08T15:30:15', '메모', '2024-08-08T14:30:15', '타이틀', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c');
insert into schedule (end_date, memo, start_date, title, institute_id, lecture_id)
values ('2024-08-06T15:30:15', '-', '2024-08-06T14:30:15', '미적', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c', '1');

alter table student alter column id restart with 1;
insert into student (age, gender, grade, level, name, parent_name, parent_number, phone_number, institute_id)
values (20, '남', 3, '상', '이학생', '이부모', '01011111111', '01011111111', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c');
insert into student (age, gender, grade, level, name, parent_name, parent_number, phone_number, institute_id)
values (20, '남', 3, '상', '이학생11', '이부모', '01011111111', '01011111111', 'bf78c61b-eb1e-46e6-be19-2fd99ee7140c');

alter table attend alter column attend_id restart with 1;
insert into attend (attendance, schedule_id, student_id)
values (1, 1, 1);

alter table enroll alter column enroll_id restart with 1;
insert into enroll (full_payment, month, payed_fee, year, lecture_id, student_id)
values (0, 8, 0, 2024, 1, 1);
