CREATE OR REPLACE VIEW v_Subj8Student AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=74
) as innerRel
