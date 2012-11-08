CREATE OR REPLACE VIEW v_Department AS 
SELECT name_0.id AS att1, name_0.name AS name
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=85
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=37
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=88
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=36
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=16
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=115
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=2
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=32
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=159
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=94
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=77
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=28
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=122
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=21
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=22
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=23
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=160
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=42
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=45
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=63
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=105
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=136
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=66
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=106
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=135
) as innerRel , individual_name name_0
WHERE  innerRel.x1=name_0.id 
