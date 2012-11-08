CREATE OR REPLACE VIEW v_takesCourse AS 
SELECT name_0.id AS att1, name_1.id AS att2, name_0.name AS x1, name_1.name AS x2
FROM (
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=155
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=147
) as innerRel , individual_name name_0, individual_name name_1
WHERE  innerRel.x1=name_0.id 
AND innerRel.x2=name_1.id 
