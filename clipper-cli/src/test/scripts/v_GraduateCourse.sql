DROP VIEW v_GraduateCourse CASCADE;
CREATE OR REPLACE VIEW v_GraduateCourse AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=84
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=155
) as innerRel
