-- View: v_subj3student

-- DROP VIEW v_subj3student;

CREATE OR REPLACE VIEW v_subj3student AS 
 SELECT name_0.id AS att1, name_0.name AS name
   FROM ( SELECT ca_0.individual AS x1
           FROM concept_assertion ca_0
          WHERE ca_0.concept = 24) innerrel, individual_name name_0
  WHERE innerrel.x1 = name_0.id;

ALTER TABLE v_subj3student
  OWNER TO xiao;
