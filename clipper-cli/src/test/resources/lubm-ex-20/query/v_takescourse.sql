CREATE OR REPLACE VIEW v_takescourse AS 
 SELECT name_0.id AS att1, name_1.id AS att2, name_0.name AS name1, name_1.name AS name2
   FROM (         SELECT ora_0.a AS x1, ora_0.b AS x2
                   FROM object_role_assertion ora_0
                  WHERE ora_0.object_role = 147
        UNION 
                 SELECT ora_0.a AS x1, ora_0.b AS x2
                   FROM object_role_assertion ora_0
                  WHERE ora_0.object_role = 155) innerrel, individual_name name_0, individual_name name_1
  WHERE innerrel.x1 = name_0.id AND innerrel.x2 = name_1.id;

ALTER TABLE v_takescourse
  OWNER TO xiao;
