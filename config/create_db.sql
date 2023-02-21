DROP TABLE contacts;
DROP TABLE resume;
--CREATE RESUME
CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL

);
--CREATE CONTACTS
CREATE TABLE contacts
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT contacts_resume_uuid_fk
            REFERENCES resume ON UPDATE RESTRICT ON DELETE CASCADE

);


CREATE TABLE sections
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,

    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT contacts_resume_uuid_fk
            REFERENCES resume ON UPDATE RESTRICT ON DELETE CASCADE
);


