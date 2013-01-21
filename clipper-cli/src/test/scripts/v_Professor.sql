CREATE OR REPLACE VIEW v_Professor AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=57
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=13
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=17
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=112
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=12
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=52
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=34
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=8
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=51
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=56
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=116
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=30
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=55
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=118
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=73
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=93
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=76
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=95
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=152
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=70
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=103
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=101
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=125
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=126
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=161
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=61
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=127
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=65
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=41
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=40
) as innerRel
