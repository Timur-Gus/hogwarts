ALTER TABLE student
    ADD CONSTRAINT age CHECK ( age >= 16 );

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE student
    ADD CONSTRAINT name UNIQUE (name);

ALTER TABLE faculty
    ADD CONSTRAINT name UNIQUE (name, color);

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;









