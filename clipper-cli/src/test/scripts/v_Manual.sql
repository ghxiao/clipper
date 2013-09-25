DROP VIEW v_Manual CASCADE;
CREATE OR REPLACE VIEW v_Manual AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=1
) as innerRel
