DROP  TABLE  contacts;
DROP TABLE sections;
DROP TABLE resumes;
--CREATE RESUME
CREATE TABLE IF NOT EXISTS resumes
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL

);
--CREATE CONTACTS
CREATE TABLE IF NOT EXISTS contacts
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL
       CONSTRAINT contacts_resume_uuid_fk
            REFERENCES resumes (uuid)
                ON UPDATE RESTRICT
                ON DELETE CASCADE

);
--CREATE SECTIONS
CREATE TABLE IF NOT EXISTS sections
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL
       CONSTRAINT contacts_resume_uuid_fk
            REFERENCES resumes (uuid)
                ON UPDATE RESTRICT
                ON DELETE CASCADE

);


