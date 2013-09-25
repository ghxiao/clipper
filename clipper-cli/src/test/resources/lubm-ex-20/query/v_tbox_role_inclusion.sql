CREATE OR REPLACE VIEW v_tbox_role_inclusion AS
SELECT 
  tbox_name_sub.name sub_role, 
  tbox_name_sub.auxiliary aux1, 
  tbox_concept_inclusion.positive, 
  tbox_name_super.name super_role, 
  tbox_name_super.auxiliary aux2
FROM 
  public.tbox_concept_inclusion, 
  public.tbox_name tbox_name_super, 
  public.tbox_name tbox_name_sub
WHERE 
  tbox_concept_inclusion.sub = tbox_name_sub.id AND
  tbox_concept_inclusion.super = tbox_name_super.id;