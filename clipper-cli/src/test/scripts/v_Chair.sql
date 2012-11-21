CREATE OR REPLACE VIEW v_Chair AS 
SELECT name_0.id AS att1, name_0.name AS name
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=56
) as innerRel , individual_name name_0
WHERE  innerRel.x1=name_0.id 
