package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.assertTrue;

import gnu.trove.set.hash.TIntHashSet;

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
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;


public class TBoxReasoningOptTest {

	@Test
	public  void test01() throws OWLOntologyCreationException, FileNotFoundException {
		ClipperManager.getInstance().setVerboseLevel(2);
		System.out.println("START");
		// File file = new File(
		// "test-suite/ontology-horn-shiq-LUBM/terminology.owl");
		// File file = new
		// File("/Users/xiao/Desktop/full-galen-Heart_Liver_BloodPressure.owl");
		// final String owlFileName = "nf_TestData/tkien.owl";
		// final String datalogFileName = "test-suite/ontology-Galen/mytest.dl";
		//
		// final String owlFileName =
		// "test-suite/ontology-Galen/terminology.owl";
		// final String datalogFileName =
		// "test-suite/ontology-Galen/galen.dl";

		// final String owlFileName =
		// "test-suite/ontology-horn-shiq-LUBM/abox-3/abox.owl";
		// final String datalogFileName =
		// "test-suite/ontology-horn-shiq-LUBM/abox-3.dl";
		//final String owlFileName = "test-suite/ontology-Galen/mini-galen.owl";
		final String owlFileName = "test-suite/ontology-Galen/terminology-no-trans-no-func.owl";
		
		// final String owlFileName = "dist/horn-shiq-University.owl";
		// final String owlFileName = "dist/example1.owl";
		final String datalogFileName = owlFileName + ".no-some.dl";

		File file = new File(owlFileName);
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		OWLOntology normalizedOnt = normalizer.normalize(ontology);
		// System.out.println("==========After 1st Step of Nomalization==========");
		//
		// for (OWLAxiom ax : normalizedOnt.getAxioms()) {
		// System.out.println(ax);
		// }

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		// System.out.println("==========After 2nd Step of Nomalization==========");
		//
		// for (OWLAxiom ax : normalizedOnt1.getAxioms()) {
		// System.out.println(ax);
		// }

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);

		// System.out.println("==========After 3rd Step of Nomalization==========");
		//
		// for (OWLAxiom ax : normalizedOnt.getAxioms()) {
		// System.out.println(ax);
		// }

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		 System.out.println("==========Information of Encoded Ontology==========");
		 SymbolEncoder<OWLClass> owlClassEncoder =
		 ClipperManager.getInstance().getOwlClassEncoder();
		 for (int cls = 0; cls < owlClassEncoder.getMax(); cls++) {
		 System.out.println(owlClassEncoder.getSymbolByValue(cls) + " -> " +
		 cls);
		 }

		// System.out.println();
		//
		// for (Axiom ax : onto_bs.getAxioms()) {
		// System.out.println(ax);
		// }

		// System.out.println("===============END : Encoded Ontology============");
		//
		TIntHashSet aboxType = new TIntHashSet();
		// for (int i = 0; i < 10; i++) {
		// aboxType.add(i);
		// }

		TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs, aboxType);
		// tb.reasoning();

		PrintingUtilities printer = new PrintingUtilities();
		System.out.println(tb.getIndexedHornImpContainer().size()
				+ "==============information after reasoning:=============");
		// printer.printHornCoreImps(tb.getCoreImps());
		// printer.printCoreEnfs(tb.getCoreEnfs());

		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
		reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
		reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
		// reduction.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		// System.out.println("==========Datalog Program=============");
		//
		// reduction.getEncodedDataLogProgram(datalogFileName);

		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		System.out.println("==========Datalog Program=============");
		reduction.getEncodedDataLogProgram(datalogFileName);
	}

	public static void main(String[] args) throws FileNotFoundException {
		try {
			new TBoxReasoningOptTest().test01();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

}
