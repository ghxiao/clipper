package org.semanticweb.clipper.hornshiq.queryanswering;
//package at.ac.tuwien.kr.hornshiq.queryanswering;
//
//import static org.junit.Assert.assertTrue;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//
//import org.junit.Test;
//import org.semanticweb.owlapi.apibinding.OWLManager;
//import org.semanticweb.owlapi.model.OWLOntology;
//import org.semanticweb.owlapi.model.OWLOntologyCreationException;
//import org.semanticweb.owlapi.model.OWLOntologyManager;
//import org.semanticweb.owlapi.profiles.OWLProfileReport;
//
//import at.ac.tuwien.kr.hornshiq.ontology.Axiom;
//import at.ac.tuwien.kr.hornshiq.ontology.BitSetNormalHornALCHIQOntology;
//import at.ac.tuwien.kr.hornshiq.profile.BitSetNormalHornALCHIQOntologyConverter;
//import at.ac.tuwien.kr.hornshiq.profile.HornALCHIQNormalizer;
//import at.ac.tuwien.kr.hornshiq.profile.HornALCHIQTransNormalizer;
//import at.ac.tuwien.kr.hornshiq.profile.HornSHIQNormalizer;
//import at.ac.tuwien.kr.hornshiq.profile.HornSHIQProfile;
//import at.ac.tuwien.kr.util.PrintingUtilities;
//
//public class ReductionToDatalogTest {
//	@Test
//	public void testRuleR1() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR1Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR1DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//			
//			reduction.ruleR1(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR2() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR2Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR2DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR2(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR3() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR3Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR3DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR3(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR4() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR4Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR4DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR4(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR5() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR5Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR5DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR5(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR6() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR6Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR6DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR6(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR7() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR7Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		PrintingUtilities printer = new PrintingUtilities();
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleR7DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR7(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testRuleR8R9() throws OWLOntologyCreationException {
//		File file = new File("TestCaseOntologies/ruleR8R9Test.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		PrintingUtilities printer = new PrintingUtilities();
//		// printer.printReachBottom(tb.getReachBottoms());
//		System.out.println("==========Datalog Program=============");
//		try {
//			PrintStream program = new PrintStream(new FileOutputStream(
//					"TestCaseOntologies/ruleE89DataLog.dl"));
//			// System.out.println("======================================== ");
//			// System.out.println("Facts from Role assertions: ");
//
//			reduction.ruleR8R9(program);
//			program.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void test01() throws OWLOntologyCreationException {
//		// File file = new File("TestCaseOntologies/test01.owl");
//		 File file = new File("TestCaseOntologies/inc.owl");
//	//	File file = new File("TestData/horn-univ-bench.owl");
//
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
//
//		System.out.println(ontology);
//
//		HornSHIQProfile profile = new HornSHIQProfile();
//
//		OWLProfileReport report = profile.checkOntology(ontology);
//		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
//		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
//
//		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
//		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
//
//		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
//		BitSetNormalHornALCHIQOntology onto_bs = converter
//				.convert(normalizedOnt3);
//
//		for (Axiom ax : onto_bs.getAxioms()) {
//			System.out.println(ax);
//		}
//
//		TBoxReasoning tb = new TBoxReasoning(onto_bs);
//		tb.reasoning();
//		ReductionToDatalog reduction = new ReductionToDatalog(onto_bs);
//		reduction.setCoreImps(tb.getCoreImps());
//		reduction.setCoreEnfs(tb.getCoreEnfs());
//		reduction.setReachBottoms(tb.getReachBottoms());
//		PrintingUtilities printer = new PrintingUtilities();
//		// printer.printReachBottom(tb.getReachBottoms());
//		reduction.getDataLogcProgram("TestCaseOntologies/tes01Datalog.dl");
//		reduction.getBitSetDataLogProgram("TestCaseOntologies/tes01BitSetDatalog.dl");
//
//	}
//}
