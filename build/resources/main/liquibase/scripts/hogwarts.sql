-- liquibase formatted sql

-- changeset neo:1
create table faculty (
                         id bigserial primary key,
                         name varchar(255),
                         color varchar(255)
);

-- changeset neo:2
create table student (
                         id bigserial primary key,
                         name varchar(255),
                         age integer not null,
                         faculty_id bigint,
                         constraint fk_student_faculty
                             foreign key (faculty_id)
                                 references faculty(id)
                                 on delete set null
);

-- changeset neo:3
create index students_name_index on student (name);

-- changeset neo:4
create index faculty_name_and_color on faculty (name, color);

-- changeset neo:5
create table avatar (
                        id bigserial primary key,
                        file_path varchar(255),
                        file_size bigint,
                        media_type varchar(255),
                        data bytea,
                        student_id bigint unique,
                        constraint fk_avatar_student
                            foreign key (student_id)
                                references student(id)
                                on delete cascade
);

-- changeset neo:6 add-unique-faculty-name
alter table faculty add constraint unique_faculty_name unique (name);
