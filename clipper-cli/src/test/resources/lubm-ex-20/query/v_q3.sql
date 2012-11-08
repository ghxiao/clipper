CREATE OR REPLACE v_q3 AS 
(
SELECT 
v_Faculty_1.att1 AS att1 
FROM 
v_subOrganizationOf v_subOrganizationOf_1,
v_degreeFrom v_degreeFrom_1,
v_Faculty v_Faculty_1,
v_Department v_Department_1,
v_memberOf v_memberOf_1,
v_University v_University_1
WHERE 
v_subOrganizationOf_1.att1 = v_Department_1.att1
 AND v_subOrganizationOf_1.att1 = v_memberOf_1.att2
 AND v_degreeFrom_1.att2 = v_subOrganizationOf_1.att2
 AND v_degreeFrom_1.att2 = v_University_1.att1
 AND v_Faculty_1.att1 = v_degreeFrom_1.att1
 AND v_Faculty_1.att1 = v_memberOf_1.att1
)
UNION
(
SELECT 
v_Faculty_1.att1 AS att1 
FROM 
v_subOrganizationOf v_subOrganizationOf_1,
v_degreeFrom v_degreeFrom_1,
v_Faculty v_Faculty_1,
v_Department v_Department_1,
v_memberOf v_memberOf_1
WHERE 
v_subOrganizationOf_1.att1 = v_Department_1.att1
 AND v_subOrganizationOf_1.att1 = v_memberOf_1.att2
 AND v_degreeFrom_1.att2 = v_subOrganizationOf_1.att2
 AND v_Faculty_1.att1 = v_degreeFrom_1.att1
 AND v_Faculty_1.att1 = v_memberOf_1.att1
)
