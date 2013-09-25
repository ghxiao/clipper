CREATE OR REPLACE VIEW v_q1 AS 
(
SELECT 
v_takesCourse_1.att1 AS att1 , v_worksFor_1.att1 AS att2 
FROM 
v_teacherOf v_teacherOf_1,
v_Course v_Course_1,
v_worksFor v_worksFor_1,
v_Faculty v_Faculty_1,
v_Department v_Department_1,
v_Student v_Student_1,
v_memberOf v_memberOf_1,
v_takesCourse v_takesCourse_1
WHERE 
v_worksFor_1.att2 = v_memberOf_1.att2
 AND v_worksFor_1.att2 = v_Department_1.att1
 AND v_takesCourse_1.att2 = v_Course_1.att1
 AND v_takesCourse_1.att2 = v_teacherOf_1.att2
 AND v_worksFor_1.att1 = v_Faculty_1.att1
 AND v_worksFor_1.att1 = v_teacherOf_1.att1
 AND v_takesCourse_1.att1 = v_memberOf_1.att1
 AND v_takesCourse_1.att1 = v_Student_1.att1
)
UNION
(
SELECT 
v_takesCourse_1.att1 AS att1 , v_worksFor_1.att1 AS att2 
FROM 
v_teacherOf v_teacherOf_1,
v_worksFor v_worksFor_1,
v_Faculty v_Faculty_1,
v_Department v_Department_1,
v_Student v_Student_1,
v_memberOf v_memberOf_1,
v_takesCourse v_takesCourse_1
WHERE 
v_worksFor_1.att2 = v_memberOf_1.att2
 AND v_worksFor_1.att2 = v_Department_1.att1
 AND v_takesCourse_1.att2 = v_teacherOf_1.att2
 AND v_worksFor_1.att1 = v_Faculty_1.att1
 AND v_worksFor_1.att1 = v_teacherOf_1.att1
 AND v_takesCourse_1.att1 = v_memberOf_1.att1
 AND v_takesCourse_1.att1 = v_Student_1.att1
)
UNION
(
SELECT 
v_teacherOf_1.att1 AS att1 , v_teacherOf_1.att1 AS att2 
FROM 
v_teacherOf v_teacherOf_1,
v_Faculty v_Faculty_1,
v_Student v_Student_1,
v_takesCourse v_takesCourse_1
WHERE 
v_teacherOf_1.att2 = v_takesCourse_1.att2
 AND v_teacherOf_1.att1 = v_Faculty_1.att1
 AND v_teacherOf_1.att1 = v_Student_1.att1
 AND v_teacherOf_1.att1 = v_takesCourse_1.att1
)
UNION
(
SELECT 
v_teacherOf_1.att1 AS att1 , v_teacherOf_1.att1 AS att2 
FROM 
v_teacherOf v_teacherOf_1,
v_Faculty v_Faculty_1,
v_Student v_Student_1,
v_takesCourse v_takesCourse_1,
v_Chair v_Chair_1
WHERE 
v_teacherOf_1.att2 = v_takesCourse_1.att2
 AND v_teacherOf_1.att1 = v_Faculty_1.att1
 AND v_teacherOf_1.att1 = v_Student_1.att1
 AND v_teacherOf_1.att1 = v_Chair_1.att1
 AND v_teacherOf_1.att1 = v_takesCourse_1.att1
)
