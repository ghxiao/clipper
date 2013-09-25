-- ans(X2,X2) :- subj4student(X2), subj3student(X2), graduatestudent(X2).


SELECT v_subj3student.att1, v_subj4student.att1
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
name_0.id = v_subj3student.att1

