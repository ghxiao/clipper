package org.semanticweb.clipper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Generate ABox data for hornshiq benchmark of DL-12 paper
 * 
 * @author kient
 * 
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

//import at.ac.tuwien.kr.kaos.QueryDataForHornSHIQABox;

/**
 * Generate ABox data for hornshiq benchmark of DL-12 paper
 * 
 * @author kient
 * 
 */
//public class ABoxDataGenerator {
//
//	public static String prefix = "http://semantics.crl.ibm.com/univ-bench-dl.owl#";
//
//	public static String ontologyIRI = "http://www.kr.tuwien.ac.at/hornshiq/abox.owl";
//
//	public static OWLOntology ontology;
//	static OWLOntologyManager manager;
//	static OWLDataFactory factory;
//
//	/**
//	 * <pre>
//	 * Q1(x) ← worksFor(x, y), isAffiliatedOrganizationOf(y, z), College(z).
//	 * Q2(x) ← Postdoc(x), worksFor(x,y), University(y), hasAlumnus(y,x).
//	 * Q3(x) ← Person(x), like(x,y), Chair(z), isHeadOf(z,w), like(z,y).
//	 * Q4(x) ← takeCourse(x,y), GraduateCourse(y), isTaughtBy(y,z), Professor(z).
//	 * Q5(x, z) ← LeisureStudent(x), takesCourse(x,y), CSCourse(y), isStudentOf(x, z), University(z).
//	 * Q6(x, y) ← enrollIn(x,y), hasDegreeFrom(x, y), University(y).
//	 * Q7(x,y) ← PeopleWithManyHobbies(x),isMemberOf(x,z),like(x,w), TennisClass(w), hasMember(z,y), like(y,w).
//	 * Q8(x,z) ← TennisFan(x), like(x,y) Sport(y), isHeadOf(x,z), ReserachGroup(z).
//	 * Q9(x,y,z) ← Student(x), hasDegreeFrom(x, y), Professor(z), worksFor(z,y), isAdvisorOf(z,x).
//	 * Q10(x,y,w) ← Professor(x), Dean(y),hasPublication(x,z), isPublicationOf(z,y),isMemberOf(y,w), worksFor(x,w).
//	 * 
//	 * </pre>
//	 * 
//	 * @param args
//	 * @throws OWLOntologyCreationException
//	 * @throws FileNotFoundException
//	 * @throws OWLOntologyStorageException
//	 */
//	public static void main(String[] args) throws OWLOntologyCreationException,
//			OWLOntologyStorageException, FileNotFoundException {
//		QueryDataForHornSHIQABox generator = new QueryDataForHornSHIQABox();
//		generator.createAbox();
//	}
//
//	/**
//	 * @throws OWLOntologyCreationException
//	 * @throws OWLOntologyStorageException
//	 * @throws FileNotFoundException
//	 */
//	public void createAbox() throws OWLOntologyCreationException,
//			OWLOntologyStorageException, FileNotFoundException {
//		manager = OWLManager.createOWLOntologyManager();
//		ontology = manager.createOntology(IRI.create(ontologyIRI));
//		factory = manager.getOWLDataFactory();
//		addAssertions();
//		manager.saveOntology(ontology, new FileOutputStream(
//				"ontology/hornshiq-myabox.owl"));
//
//		System.out.println("DONE!");
//
//	}
//
//	private void addAssertions() {
//		addAssertionsForQuery1();
//		addAssertionsForQuery2();
//		addAssertionsForQuery3();
//		addAssertionsForQuery4();
//		addAssertionsForQuery5();
//		addAssertionsForQuery6();
//		addAssertionsForQuery7();
//		addAssertionsForQuery8();
//		addAssertionsForQuery9();
//		addAssertionsForQuery10();
//
//	}
//
//	private void addAssertionsForQuery1() {
//		// PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>
//		// SELECT ?x
//		// WHERE{
//		// ?x ub:worksFor ?y .
//		// ?z a ub:College .
//		// ?y ub:isAffiliatedOrganizationOf ?z .
//		// }
//
//		// Q1(x) ← worksFor(x, y), isAffiliatedOrganizationOf(y, z), College(z).
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q1_" + "x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q1_" + "y" + i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q1_" + "college_z" + i);
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx / 2; i++) {
//			triple(xs.get(i), "worksFor", ys.get(i));
//			triple(ys.get(i), "isAffiliatedOrganizationOf", zs.get(i));
//			isa(zs.get(i), "College");
//		}
//
//		// indirect answers:
//		// college(X) :- womancollege(X).
//		// worksfor(X,Y) :- isheadof(X,Y).
//		for (int i = numberOfx / 2; i < numberOfx; i++) {
//			triple(xs.get(i), "isHeadOf", ys.get(i));
//			triple(ys.get(i), "isAffiliatedOrganizationOf", zs.get(i));
//			isa(zs.get(i), "WomanCollege");
//		}
//
//	}
//
//	private void addAssertionsForQuery2() {
//		// SELECT ?x
//		// WHERE {
//		// ?x a ub:Postdoc ;
//		// ub:worksFor ?y .
//		// ?y a ub:University ;
//		// ub:hasAlumnus ?x .
//		// }
//
//		// Q2(x) ← Postdoc(x), worksFor(x,y), University(y), hasAlumnus(y,x).
//		int numberOfx = 100;
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q2_" + "postdoc_x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q2_" + "university_y"
//					+ i);
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx; i++) {
//			isa(xs.get(i), "Postdoc");
//			triple(xs.get(i), "worksFor", ys.get(i));
//			isa(ys.get(i), "University");
//			triple(ys.get(i), "hasAlumnus", xs.get(i));
//		}
//
//	}
//
//	private void addAssertionsForQuery3() {
//		// SELECT ?x
//		// WHERE {
//		// ?x a ub:Person ;
//		// ub:like ?y .
//		// ?z a ub:Chair ;
//		// ub:isHeadOf ?w ;
//		// ub:like ?y .
//		// }
//		// Q3(x) ← Person(x), like(x,y), Chair(z), isHeadOf(z,w), like(z,y).
//
//		int numberOfx = 100;
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//		ArrayList<String> ws = new ArrayList<String>();
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q3_" + "person_x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q3_" + "y" + i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q3_" + "chair_z" + i);
//			ws.add("http://www.kr.tuwien.ac.at/" + "indv_q3_" + "w" + i);
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx / 2; i++) {
//			isa(xs.get(i), "Person");
//			triple(xs.get(i), "like", ys.get(i));
//			isa(zs.get(i), "Chair");
//			triple(zs.get(i), "isHeadOf", ws.get(i));
//			triple(zs.get(i), "like", ys.get(i));
//		}
//
//		// indirect answers:
//		// Q3(x) ← Person(x), like(x,y), Chair(z), Dean(z),like(z,y).
//		for (int i = numberOfx / 2; i < numberOfx * 3 / 4; i++) {
//			isa(xs.get(i), "Person");
//			triple(xs.get(i), "like", ys.get(i));
//			isa(zs.get(i), "Chair");
//			isa(zs.get(i), "Dean");
//			triple(zs.get(i), "like", ys.get(i));
//		}
//
//		// indirect answers:
//		// Q3(x) ← Person(x), Chair(x), Dean(x), SportLover(x).
//		for (int i = numberOfx * 3 / 4; i < numberOfx; i++) {
//			isa(xs.get(i), "Person");
//			isa(xs.get(i), "Chair");
//			isa(xs.get(i), "Dean");
//			isa(xs.get(i), "SporstLover");
//
//		}
//
//	}
//
//	private void addAssertionsForQuery4() {
//		// Q4(x):- takeCourse(x,y), GraduateCourse(y), isTaughtBy(y, z),
//		// Professor(z).
//		// Will be the replacement of Q8 in the aaai12 paper
//		// Test: Inference rule on Value Restriction.
//		// Query rewriting (2 times)
//		ArrayList<String> gradStudents = new ArrayList<String>();
//		int numberOfGradStudent = 100;
//		for (int i = 0; i < numberOfGradStudent; i++) {
//			gradStudents.add("http://www.kr.tuwien.ac.at/" + "indv_q4_"
//					+ "gradstudent" + i);
//		}
//
//		ArrayList<String> gradCourses = new ArrayList<String>();
//		for (int i = 0; i < numberOfGradStudent; i++) {
//			gradCourses.add("http://www.kr.tuwien.ac.at/" + "indv_q4_"
//					+ "gradcourse" + i);
//		}
//
//		// We do not state explicitly that those course are GradCourse
//		// We only state that grad student take this course
//		for (int i = 0; i < numberOfGradStudent; i++) {
//			isa(gradStudents.get(i), "GraduateStudent");
//			triple(gradStudents.get(i), "takesCourse", gradCourses.get(i));
//		}
//
//		// One of the rewritten query is: Q4(x):-
//		// GraduateStudent(x),Student(x).
//		// So, we will have 100 answers.
//
//	}
//
//	private void addAssertionsForQuery5() {
//		// SELECT ?x ?z
//		// WHERE {
//		// ?x a ub:LeisureStudent ;
//		// ub:takesCourse ?y ;
//		// ub:isStudentOf ?z .
//		// ?y a ub:CSCourse .
//		// ?z a ub:University .
//		// }
//
//		// Q5(x, z) ← LeisureStudent(x), takesCourse(x,y), CSCourse(y),
//		// isStudentOf(x, z), University(z).
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q5_"
//					+ "leisure_student_x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q5_" + "cscourse_y"
//					+ i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q5_" + "univerisity_z"
//					+ i);
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx / 2; i++) {
//			isa(xs.get(i), "LeisureStudent");
//			triple(xs.get(i), "takesCourse", ys.get(i));
//			isa(ys.get(i), "CSCourse");
//			triple(xs.get(i), "isStudentOf", zs.get(i));
//			isa(zs.get(i), "University");
//		}
//
//		// indirect answers:
//		// q0(X2,X1) :- leisurestudent(X2), csstudent(X2), isstudentof(X2,X1),
//		// university(X1).
//		for (int i = numberOfx / 2; i < numberOfx; i++) {
//			isa(xs.get(i), "LeisureStudent");
//			isa(xs.get(i), "CSStudent");
//			triple(xs.get(i), "isStudentOf", zs.get(i));
//			isa(zs.get(i), "University");
//		}
//
//	}
//
//	private void addAssertionsForQuery6() {
//		// SELECT ?x ?y
//		// WHERE {
//		// ?x ub:enrollIn ?y .
//		// ?x ub:hasDegreeFrom ?y .
//		// ?y a ub:University .
//		// }
//
//		// Q6(x, y) ← enrollIn(x,y), hasDegreeFrom(x, y), University(y).
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q6_" + "x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q6_" + "university_y"
//					+ i);
//
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx / 2; i++) {
//			triple(xs.get(i), "enrollIn", ys.get(i));
//			triple(xs.get(i), "hasDegreeFrom", ys.get(i));
//			isa(ys.get(i), "University");
//		}
//
//		// inderect answers:
//		// q0(X0,X1) :- hasdegreefrom(X0,X1), enrollin(X0,X1).
//		for (int i = numberOfx / 2; i < numberOfx; i++) {
//			triple(xs.get(i), "enrollIn", ys.get(i));
//			triple(xs.get(i), "hasDegreeFrom", ys.get(i));
//		}
//	}
//
//	private void addAssertionsForQuery7() {
//
//		// SELECT ?x ?y
//		// WHERE {
//		// ?x a ub:PeopleWithManyHobbies ;
//		// ub:isMemberOf ?z ;
//		// ub:like ?w .
//		// ?w a ub:TennisClass .
//		// ?z ub:hasMember ?y .
//		// ?y ub:like ?w .
//		// }
//		// * Q7(x,y) ← PeopleWithManyHobbies(x),isMemberOf(x,z),like(x,w),
//		// TennisClass(w), hasMember(z,y), like(y,w).
//
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//		ArrayList<String> ws = new ArrayList<String>();
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q7_"
//					+ "peoplewhobbies_x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q7_" + "y" + i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q7_" + "z" + i);
//			ws.add("http://www.kr.tuwien.ac.at/" + "indv_q7_" + "tennisclass_w"
//					+ i);
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx / 2; i++) {
//			isa(xs.get(i), "PeopleWithManyHobbies");
//			triple(xs.get(i), "isMemberOf", zs.get(i));
//			triple(xs.get(i), "like", ws.get(i));
//			isa(ws.get(i), "TennisClass");
//			triple(zs.get(i), "hasMember", ys.get(i));
//			triple(ys.get(i), "like", ws.get(i));
//		}
//		// indirect answers:
//		// Q7(x,y) ← PeopleWithManyHobbies(x),isMemberOf(x,z), TennisClass(w),
//		// hasMember(z,y),
//		for (int i = numberOfx / 2; i < numberOfx * 3 / 4; i++) {
//			isa(xs.get(i), "PeopleWithManyHobbies");
//			triple(xs.get(i), "isMemberOf", zs.get(i));
//			isa(ws.get(i), "TennisClass");
//			triple(zs.get(i), "hasMember", ys.get(i));
//
//		}
//
//		// indirect answers:
//		// q0(X2,X2) :- tennisfan(X2), peoplewithmanyhobbies(X2), employee(X2).
//		for (int i = numberOfx * 3 / 4; i < numberOfx; i++) {
//			isa(xs.get(i), "PeopleWithManyHobbies");
//			isa(ws.get(i), "TennisClass");
//			isa(ws.get(i), "Employee");
//		}
//
//	}
//
//	private void addAssertionsForQuery8() {
//		// SELECT ?x ?z
//		// WHERE {
//		// ?x a ub:TennisFan ;
//		// ub:like ?y ;
//		// ub:isHeadOf ?z .
//		// ?y a ub:Sports .
//		// ?z a ub:ResearchGroup .
//		// }
//		//
//
//		// Q8(x,z) ← TennisFan(x), like(x,y) Sport(y), isHeadOf(x,z),
//		// ReserachGroup(z).
//
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q8_" + "tennisfan_x"
//					+ i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q8_" + "sport_y" + i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q8_"
//					+ "reserachgroup_z" + i);
//		}
//		// direct answers:
//		for (int i = 0; i < numberOfx / 2; i++) {
//			isa(xs.get(i), "TennisFan");
//			triple(xs.get(i), "like", ys.get(i));
//			isa(ys.get(i), "Sport");
//			triple(xs.get(i), "isHeadOf", zs.get(i));
//			isa(zs.get(i), "ResearchGroup");
//		}
//		// indirect answers:
//		// // Q8(x,z) ← TennisFan(x), isHeadOf(x,z), ReserachGroup(z).
//		for (int i = numberOfx / 2; i < numberOfx; i++) {
//			isa(xs.get(i), "TennisFan");
//			triple(xs.get(i), "isHeadOf", zs.get(i));
//			isa(zs.get(i), "ResearchGroup");
//		}
//
//	}
//
//	private void addAssertionsForQuery9() {
//		// SELECT ?x ?y ?z
//		// WHERE {
//		// ?x a ub:Student .
//		// ?x ub:hasDegreeFrom ?y .
//		// ?z a ub:Professor ;
//		// ub:worksFor ?y ;
//		// ub:isAdvisorOf ?x .
//		//
//		// }
//		// Q9(x,y,z) ← Student(x), hasDegreeFrom(x, y), Professor(z),
//		// worksFor(z,y), isAdvisorOf(z,x).
//
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q9_" + "student_x" + i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q9_" + "y" + i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q9_" + "professor_z"
//					+ i);
//
//		}
//
//		// direct answers:
//		for (int i = 0; i < numberOfx / 4; i++) {
//			isa(xs.get(i), "Student");
//			triple(xs.get(i), "hasDegreeFrom", ys.get(i));
//			isa(zs.get(i), "Professor");
//			triple(zs.get(i), "worksFor", ys.get(i));
//			triple(zs.get(i), "isAdvisorOf", xs.get(i));
//		}
//
//		// Indirect answers:
//		for (int i = numberOfx / 4; i < numberOfx / 2; i++) {
//			isa(xs.get(i), "CSStudent");
//			triple(xs.get(i), "hasDegreeFrom", ys.get(i));
//			isa(zs.get(i), "Professor");
//			triple(zs.get(i), "worksFor", ys.get(i));
//			triple(zs.get(i), "isAdvisorOf", xs.get(i));
//		}
//
//		// Indirect answers:
//		for (int i = numberOfx / 2; i < numberOfx * 3 / 4; i++) {
//			isa(xs.get(i), "CSStudent");
//			triple(xs.get(i), "hasDoctoralDegreeFrom", ys.get(i));
//			isa(zs.get(i), "Professor");
//			triple(zs.get(i), "worksFor", ys.get(i));
//			triple(zs.get(i), "isAdvisorOf", xs.get(i));
//		}
//
//		// Indirect answers:
//		for (int i = numberOfx * 3 / 4; i < numberOfx; i++) {
//			isa(xs.get(i), "CSStudent");
//			triple(xs.get(i), "hasDoctoralDegreeFrom", ys.get(i));
//			isa(zs.get(i), "FullProfessor");
//			triple(zs.get(i), "worksFor", ys.get(i));
//			triple(zs.get(i), "isAdvisorOf", xs.get(i));
//		}
//
//	}
//
//	private void addAssertionsForQuery10() {
//		// SELECT ?x ?y ?w
//		// WHERE {
//		// ?x a ub:Professor ;
//		// ub:hasPublication ?z ;
//		// ub:worksFor ?w .
//		// ?y a ub:Dean ;
//		// ub:isMemberOf ?w .
//		// ?z ub:isPublicationOf ?y .
//		// }
//
//		// Q10(x,y,w) ← Professor(x), Dean(y),hasPublication(x,z),
//		// isPublicationOf(z,y),isMemberOf(y,w), worksFor(x,w).
//		int numberOfx = 100;
//
//		ArrayList<String> xs = new ArrayList<String>();
//		ArrayList<String> ys = new ArrayList<String>();
//		ArrayList<String> zs = new ArrayList<String>();
//		ArrayList<String> ws = new ArrayList<String>();
//
//		for (int i = 0; i < numberOfx; i++) {
//			xs.add("http://www.kr.tuwien.ac.at/" + "indv_q10_" + "professor_x"
//					+ i);
//			ys.add("http://www.kr.tuwien.ac.at/" + "indv_q10_" + "dean_y" + i);
//			zs.add("http://www.kr.tuwien.ac.at/" + "indv_q10_"
//					+ "publication_z" + i);
//			ws.add("http://www.kr.tuwien.ac.at/" + "indv_q10_"
//					+ "organization_w" + i);
//
//		}
//
//		// direct answers:
//		// // Q10(x,y,w) ← Professor(x), Dean(y),hasPublication(x,z),
//		// isPublicationOf(z,y),isMemberOf(y,w), worksFor(x,w).
//		for (int i = 0; i < numberOfx / 2; i++) {
//			isa(xs.get(i), "Professor");
//			isa(ys.get(i), "Dean");
//			triple(xs.get(i), "hasPublication", zs.get(i));
//			triple(zs.get(i), "isPublicationOf", ys.get(i));
//			triple(ys.get(i), "isMemberOf", ws.get(i));
//			triple(xs.get(i), "worksFor", ws.get(i));
//		}
//
//		// Indirect answers:
//		// Q10(x,y,w) ← Professor(x), Dean(y),isMemberOf(y,w), worksFor(x,w).
//		for (int i = numberOfx / 2; i < numberOfx; i++) {
//			isa(xs.get(i), "Professor");
//			isa(ys.get(i), "Dean");
//			triple(ys.get(i), "isMemberOf", ws.get(i));
//			triple(xs.get(i), "worksFor", ws.get(i));
//		}
//
//	}
//
//	private void isa(String ind, String concpet) {
//		OWLAxiom axiom = factory.getOWLClassAssertionAxiom( //
//				factory.getOWLClass(toIRI(concpet)), //
//				factory.getOWLNamedIndividual(IRI.create(ind)));
//		manager.addAxiom(ontology, axiom);
//	}
//
//	private void triple(String ind1, String role, String ind2) {
//		OWLObjectPropertyAssertionAxiom axiom = factory
//				.getOWLObjectPropertyAssertionAxiom(
//						factory.getOWLObjectProperty(toIRI(role)),
//						factory.getOWLNamedIndividual(toIRI(ind1)),
//						factory.getOWLNamedIndividual(toIRI(ind2)));
//		manager.addAxiom(ontology, axiom);
//	}
//
//	private IRI toIRI(String str) {
//		String iri;
//		if (str.contains(":")) {
//			iri = str;
//		} else {
//			iri = prefix + str;
//		}
//		return IRI.create(iri);
//
//	}
//}
//
//
