CREATE OR REPLACE VIEW v_q6 AS 
(
SELECT 
v_University_1.att1 AS att1 , v_memberOf_2.att2 AS att2 
FROM 
v_advisor v_advisor_1,
v_Student v_Student_1,
v_memberOf v_memberOf_1,
v_memberOf v_memberOf_2,
v_University v_University_1,
v_University v_University_2
WHERE 
v_memberOf_1.att1 = v_advisor_1.att2
 AND v_Student_1.att1 = v_memberOf_1.att1
 AND v_Student_1.att1 = v_advisor_1.att1
 AND v_memberOf_1.att2 = v_University_2.att1
 AND v_University_1.att1 = v_memberOf_1.att2
)
UNION
(
SELECT 
v_University_1.att1 AS att1 , v_memberOf_2.att2 AS att2 
FROM 
v_Professor v_Professor_1,
v_advisor v_advisor_1,
v_Student v_Student_1,
v_memberOf v_memberOf_1,
v_memberOf v_memberOf_2,
v_University v_University_1,
v_University v_University_2
WHERE 
v_memberOf_1.att1 = v_Professor_1.att1
 AND v_memberOf_1.att1 = v_advisor_1.att2
 AND v_Student_1.att1 = v_memberOf_1.att1
 AND v_Student_1.att1 = v_advisor_1.att1
 AND v_memberOf_1.att2 = v_University_2.att1
 AND v_University_1.att1 = v_memberOf_1.att2
)
