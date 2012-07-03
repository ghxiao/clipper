package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.Axiom;
import org.semanticweb.clipper.hornshiq.ontology.NormalHornALCHIQOntology;
import org.semanticweb.clipper.hornshiq.profile.BitSetNormalHornALCHIQOntologyConverter;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt;
import org.semanticweb.clipper.hornshiq.queryanswering.TBoxReasoningOpt;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.util.PrintingUtilities;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

import antlr.debug.NewLineEvent;

public class ReductionToDatalogOptTest {
	@Test
	public void testImps() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR1Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR1DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

//			reduction.ruleForBottomConcept(program);
			reduction.rulesFromImps(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRulesFromImps() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR2Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR2DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

	//		reduction.ruleForBottomConcept(program);
			reduction.rulesFromImps(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRuleFromValueRestriction() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR3Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR3DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

	//		reduction.ruleForBottomConcept(program);
			reduction.rulesFromImps(program);
			reduction.rulesFromValueRestrictions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRulesFromRoleInclusions() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR4Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR4DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

		//	reduction.ruleForBottomConcept(program);
			reduction.rulesFromRoleInclusions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRulesFromInverseRoles() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR5Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR5DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

	//		reduction.ruleForBottomConcept(program);
			reduction.rulesFromInverseRoleAxioms(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRulesFromNumberRestrictions() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR6Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR6DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

		//	reduction.ruleForBottomConcept(program);
			reduction.rulesFromNumberRestrictions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRuleR7() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR7Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		PrintingUtilities printer = new PrintingUtilities();
		System.out.println(reduction.getCoreEnfs());
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleR7DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

	//		reduction.ruleForBottomConcept(program);
			reduction.rulesFromNumberRestrictionAndEnfs(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRuleR8R9() throws OWLOntologyCreationException {
		File file = new File("TestCaseOntologies/ruleR8R9Test.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		// printer.printReachBottom(tb.getReachBottoms());
		System.out.println("==========Datalog Program=============");
		try {
			PrintStream program = new PrintStream(new FileOutputStream("TestCaseOntologies/ruleE89DataLog.dl"));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

	//		reduction.ruleForBottomConcept(program);
			reduction.rulesFromABoxAssertions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test01() throws OWLOntologyCreationException {
		// File file = new File("TestCaseOntologies/test01.owl");
		File file = new File("TestCaseOntologies/inc.owl");
		// File file = new File("TestData/horn-univ-bench.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
		tb.reasoning();
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		PrintingUtilities printer = new PrintingUtilities();
		// printer.printReachBottom(tb.getReachBottoms());
		// reduction.getDataLogcProgram("TestCaseOntologies/tes01Datalog.dl");
		reduction.getEncodedDataLogProgram("TestCaseOntologies/tes01BitSetDatalog.dl");

	}
}
