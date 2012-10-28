-- View: v_graduatestudent

-- DROP VIEW v_graduatestudent;

CREATE OR REPLACE VIEW v_graduatestudent AS 
 SELECT name_0.id AS att1, name_0.name AS name
   FROM ( SELECT ca_0.individual AS x1
           FROM concept_assertion ca_0
          WHERE ca_0.concept = 119) innerrel, individual_name name_0
  WHERE innerrel.x1 = name_0.id;

ALTER TABLE v_graduatestudent
  OWNER TO xiao;

