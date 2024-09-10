SELECT s.name, s.age, f.name
FROM student s
         INNER JOIN faculty f ON s.faculty_id = f.id;


SELECT s.name, s.age
FROM student s
INNER JOIN avatar a on a.id = s.avatar_id