SELECT v_subj3student_1.att1, v_subj3student_2.att1
FROM
v_subj3student v_subj3student_1,	
v_subj3student v_subj3student_2,	
v_takescourse v_takescourse_1,
v_takescourse v_takescourse_2
WHERE
v_subj3student_1.att1 = v_takescourse_1.att1
AND v_subj3student_2.att1 = v_takescourse_1.att2
AND v_takescourse_1.att1 = v_takescourse_2.att2
