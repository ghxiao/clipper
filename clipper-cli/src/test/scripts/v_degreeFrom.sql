CREATE OR REPLACE VIEW v_degreeFrom AS 
SELECT innerRel.x1 AS att1, innerRel.x0 AS att2
FROM (
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=168
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=138
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=148
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=167
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=163
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=154
UNION 
SELECT ora_0.a AS x1, ora_0.b AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=131
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=133
UNION 
SELECT ora_0.b AS x1, ora_0.a AS x2
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=145
) as innerRel
