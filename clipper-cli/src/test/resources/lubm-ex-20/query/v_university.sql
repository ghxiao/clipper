-- View: v_university

-- DROP VIEW v_university;

CREATE OR REPLACE VIEW v_university AS 
 SELECT name_0.id AS att1, name_0.name AS name
   FROM (        (        (        (        (        (        (        (        (        (        (         SELECT ora_0.b AS x1
                                                                                                   FROM object_role_assertion ora_0
                                                                                                  WHERE ora_0.object_role = 168
                                                                                        UNION 
                                                                                                 SELECT ora_0.a AS x1
                                                                                                   FROM object_role_assertion ora_0
                                                                                                  WHERE ora_0.object_role = 131)
                                                                                UNION 
                                                                                         SELECT ca_0.individual AS x1
                                                                                           FROM concept_assertion ca_0
                                                                                          WHERE ca_0.concept = 114)
                                                                        UNION 
                                                                                 SELECT ora_0.b AS x1
                                                                                   FROM object_role_assertion ora_0
                                                                                  WHERE ora_0.object_role = 166)
                                                                UNION 
                                                                         SELECT ora_0.b AS x1
                                                                           FROM object_role_assertion ora_0
                                                                          WHERE ora_0.object_role = 167)
                                                        UNION 
                                                                 SELECT ora_0.b AS x1
                                                                   FROM object_role_assertion ora_0
                                                                  WHERE ora_0.object_role = 154)
                                                UNION 
                                                         SELECT ora_0.a AS x1
                                                           FROM object_role_assertion ora_0
                                                          WHERE ora_0.object_role = 141)
                                        UNION 
                                                 SELECT ora_0.b AS x1
                                                   FROM object_role_assertion ora_0
                                                  WHERE ora_0.object_role = 133)
                                UNION 
                                         SELECT ora_0.b AS x1
                                           FROM object_role_assertion ora_0
                                          WHERE ora_0.object_role = 145)
                        UNION 
                                 SELECT ora_0.b AS x1
                                   FROM object_role_assertion ora_0
                                  WHERE ora_0.object_role = 163)
                UNION 
                         SELECT ora_0.b AS x1
                           FROM object_role_assertion ora_0
                          WHERE ora_0.object_role = 138)
        UNION 
                 SELECT ora_0.b AS x1
                   FROM object_role_assertion ora_0
                  WHERE ora_0.object_role = 149) innerrel, individual_name name_0
  WHERE innerrel.x1 = name_0.id;

ALTER TABLE v_university
  OWNER TO xiao;

