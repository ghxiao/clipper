DROP VIEW v_ExamRecord CASCADE;
CREATE OR REPLACE VIEW v_ExamRecord AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=108
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=137
) as innerRel
