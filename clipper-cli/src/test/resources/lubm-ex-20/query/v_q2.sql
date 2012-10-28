CREATE OR REPLACE VIEW v_q2 AS 
(
SELECT v_subj3student_1.att1 AS att1, v_subj3student_2.att1 AS att2
FROM
v_subj3student v_subj3student_1,	
v_subj3student v_subj3student_2,	
v_takescourse v_takescourse_1,
v_takescourse v_takescourse_2
WHERE
v_subj3student_1.att1 = v_takescourse_1.att1
AND v_subj3student_2.att1 = v_takescourse_1.att2
AND v_takescourse_1.att1 = v_takescourse_2.att2
)
UNION
(SELECT v_subj3student.att1 AS att1, v_subj4student.att1 AS att2
FROM
v_subj3student,
v_subj4student,	
v_graduatestudent,
individual_name name_0
WHERE
v_subj3student.att1 = v_graduatestudent.att1
AND
v_subj4student.att1 = v_graduatestudent.att1
AND
name_0.id = v_subj3student.att1)
UNION
(
SELECT v_subj3student.att1 AS att1, v_subj4student.att1 AS att2
FROM
v_subj3student,
v_subj4student,	
v_student,
individual_name name_0
WHERE
v_subj3student.att1 = v_student.att1
AND
v_subj4student.att1 = v_student.att1
AND
name_0.id = v_subj3student.att1)

 
