CREATE OR REPLACE VIEW v_age AS 
SELECT x1, name_0.name AS x2
FROM (
SELECT dra_0.value AS x1, dra_0.individual AS x2
FROM  data_role_assertion dra_0
WHERE dra_0.data_role=171
) as innerRel
WHERE  innerRel.x2=name_0.id 
