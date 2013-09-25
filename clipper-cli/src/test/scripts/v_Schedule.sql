DROP VIEW v_Schedule CASCADE;
CREATE OR REPLACE VIEW v_Schedule AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=140
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=124
) as innerRel
