-- View: v_publicationauthor

-- DROP VIEW v_publicationauthor;

CREATE OR REPLACE VIEW v_publicationauthor AS 
 SELECT name_0.id AS att1, name_1.id AS x2, name_0.name AS name1, name_1.name AS name2
--   FROM ( SELECT ora_0.b AS x1, ora_0.a AS x2
   FROM ( SELECT ora_0.a AS x1, ora_0.b AS x2
           FROM object_role_assertion ora_0
          WHERE ora_0.object_role = 162) innerrel, individual_name name_0, individual_name name_1
  WHERE innerrel.x1 = name_0.id AND innerrel.x2 = name_1.id;

ALTER TABLE v_publicationauthor
  OWNER TO xiao;

