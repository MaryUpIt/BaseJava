--getAllSorted
SELECT *
FROM resumes
         JOIN contacts c on resumes.uuid = c.resume_uuid
ORDER BY full_name;
--get
SELECT *
FROM resumes r
         JOIN contacts c on r.uuid = c.resume_uuid
WHERE r.uuid = '7a2b1c25-2637-499a-8d8e-bb9201909ef4';
--delete

DELETE
FROM contacts
WHERE resume_uuid = '7a2b1c25-2637-499a-8d8e-bb9201909ef4';
DELETE
FROM resumes
WHERE uuid = '7a2b1c25-2637-499a-8d8e-bb9201909ef4';
--save
INSERT INTO resumes (uuid, full_name)
VALUES ('7a2b1c25-2637-499a-8d8e-bb9201909ef4', 'Freya');
INSERT INTO contacts (type, value, resume_uuid)
VALUES ('PHONE', '+79358489502', '7a2b1c25-2637-499a-8d8e-bb9201909ef4'),
       ('SKYPE', 'Freya@skype.com', '7a2b1c25-2637-499a-8d8e-bb9201909ef4'),
       ('EMAIL', 'Freya@gmail.com', '7a2b1c25-2637-499a-8d8e-bb9201909ef4'),
       ('GITHUB', 'Freya@gitHub.com', '7a2b1c25-2637-499a-8d8e-bb9201909ef4');
--update
UPDATE resumes
SET full_name='Igor'
WHERE uuid = '7a2b1c25-2637-499a-8d8e-bb9201909ef4';
DELETE
FROM contacts
WHERE resume_uuid = '7a2b1c25-2637-499a-8d8e-bb9201909ef4';
INSERT INTO contacts (type, value, resume_uuid)
VALUES ('PHONE', '+7656299377', '7a2b1c25-2637-499a-8d8e-bb9201909ef4'),
       ('SKYPE', 'Igor@skype.com', '7a2b1c25-2637-499a-8d8e-bb9201909ef4'),
       ('EMAIL', 'Igor@gmail.com', '7a2b1c25-2637-499a-8d8e-bb9201909ef4'),
       ('GITHUB', 'Igor@gitHub.com', '7a2b1c25-2637-499a-8d8e-bb9201909ef4');
