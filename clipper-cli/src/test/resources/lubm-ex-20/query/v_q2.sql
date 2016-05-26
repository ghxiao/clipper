CREATE OR REPLACE VIEW v_q2 AS 
(
SELECT 
v_takesCourse_2.att1 AS att1 , v_Subj4Student_1.att1 AS att2 
FROM 
v_takesCourse v_takesCourse_1,
v_takesCourse v_takesCourse_2,
v_Subj4Student v_Subj4Student_1,
v_Subj3Student v_Subj3Student_1
WHERE 
v_Subj4Student_1.att1 = v_takesCourse_1.att1
 AND v_takesCourse_1.att1 = v_Subj3Student_1.att1
 AND v_takesCourse_1.att2 = v_takesCourse_1.att2
)
UNION
(
SELECT 
v_Subj3Student_1.att1 AS att1 , v_Subj3Student_1.att1 AS att2 
FROM 
v_Subj4Student v_Subj4Student_1,
v_GraduateStudent v_GraduateStudent_1,
v_Subj3Student v_Subj3Student_1
WHERE 
v_Subj3Student_1.att1 = v_Subj4Student_1.att1
 AND v_Subj3Student_1.att1 = v_GraduateStudent_1.att1
)
UNION
(
SELECT 
v_Subj3Student_1.att1 AS att1 , v_Subj3Student_1.att1 AS att2 
FROM 
v_Student v_Student_1,
v_Subj4Student v_Subj4Student_1,
v_Subj3Student v_Subj3Student_1
WHERE 
v_Subj3Student_1.att1 = v_Subj4Student_1.att1
 AND v_Subj3Student_1.att1 = v_Student_1.att1
)
