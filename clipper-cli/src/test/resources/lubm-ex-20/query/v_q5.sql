CREATE OR REPLACE VIEW v_q5 AS 
SELECT DISTINCT v_publication.att1, name_0.name
FROM 
  public.v_publication, 
  public.v_publicationauthor v_publicationauthor_professor, 
  public.v_student, 
  public.v_publicationauthor v_publicationauthor_student, 
  public.v_professor,
  individual_name name_0
WHERE 
  v_publication.att1 = v_publicationauthor_professor.att1 AND
  v_publicationauthor_professor.x2 = v_professor.att1 AND
  v_publication.att1 = v_publicationauthor_student.att1 AND
  v_publicationauthor_student.x2 = v_student.att1 AND
  v_publication.att1 = name_0.id
  ;
 
