CREATE OR REPLACE VIEW v_q4 AS 
(
SELECT 
v_memberOf_2.att2 AS att1 , v_Subj4Department_1.att1 AS att2 
FROM 
v_Subj4Department v_Subj4Department_1,
v_Subj3Department v_Subj3Department_1,
v_Professor v_Professor_1,
v_Professor v_Professor_2,
v_memberOf v_memberOf_1,
v_memberOf v_memberOf_2,
v_publicationAuthor v_publicationAuthor_1,
v_publicationAuthor v_publicationAuthor_2
WHERE 
v_Subj4Department_1.att1 = v_memberOf_1.att2
 AND v_memberOf_1.att1 = v_Professor_2.att1
 AND v_memberOf_1.att1 = v_publicationAuthor_2.att2
 AND v_memberOf_1.att2 = v_Subj3Department_1.att1
 AND v_publicationAuthor_1.att1 = v_publicationAuthor_2.att1
 AND v_Professor_1.att1 = v_publicationAuthor_1.att2
 AND v_Professor_1.att1 = v_memberOf_2.att1
)
UNION
(
SELECT 
v_Subj3Department_1.att1 AS att1 , v_Subj4Department_1.att1 AS att2 
FROM 
v_Subj4Department v_Subj4Department_1,
v_Subj3Department v_Subj3Department_1,
v_Professor v_Professor_1,
v_Faculty v_Faculty_1,
v_memberOf v_memberOf_1,
v_memberOf v_memberOf_2
WHERE 
v_Subj4Department_1.att1 = v_memberOf_1.att2
 AND v_Subj3Department_1.att1 = v_memberOf_2.att2
 AND v_memberOf_1.att1 = v_memberOf_2.att1
 AND v_memberOf_1.att1 = v_Faculty_1.att1
 AND v_memberOf_1.att1 = v_Professor_1.att1
)
