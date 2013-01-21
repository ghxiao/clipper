CREATE OR REPLACE VIEW v_Student AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=72
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=58
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=74
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=83
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=89
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=86
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=78
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=24
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=29
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=81
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=104
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=102
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=20
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=43
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=60
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=132
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=50
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=109
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=7
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=31
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=107
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=67
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=137
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=53
) as innerRel
