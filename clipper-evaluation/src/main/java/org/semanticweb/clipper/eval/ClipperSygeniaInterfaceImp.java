package org.semanticweb.clipper.eval;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import org.oxford.comlab.compass.SystemInterface;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.File;
import java.util.List;

public class ClipperSygeniaInterfaceImp implements SystemInterface {

	private QAHornSHIQ qaHornSHIQ;

	private String dataSetRoot;

	private String dlvPath;

	private OWLOntology tbox;

	private String queryFile;

	private OWLOntologyManager manager;

	private OWLOntology abox;

	@Override
	public void initializeSystem() throws Exception {

		qaHornSHIQ = new QAHornSHIQ();
		if (dlvPath != null) {
			qaHornSHIQ.setDlvPath(dlvPath);
		}
	}

	@Override
	public void shutdown() {

	}

	@Override
	public void loadTestToSystem(String ontologyFile, File aBoxFile) throws Exception {
		manager = OWLManager.createOWLOntologyManager();
		OWLOntology tboxOntology = manager.loadOntologyFromOntologyDocument(new File(ontologyFile));
		this.tbox = tboxOntology;

		qaHornSHIQ.setOntologyName(ontologyFile);

		qaHornSHIQ.addOntology(tboxOntology);

		qaHornSHIQ.setDatalogFileName("tmp.dlv");
		
		qaHornSHIQ.setQueryRewriter("new");

		// qaHornSHIQ.setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);

		ClipperManager.getInstance().setVerboseLevel(0);
		//ClipperManager.getInstance().setVerboseLevel(8);

		manager.removeOntology(tbox);

		OWLOntology aboxOntology = manager.loadOntologyFromOntologyDocument(aBoxFile);
		this.abox = aboxOntology;

		qaHornSHIQ.addOntology(aboxOntology);
		// manager.removeOntology(abox);

		CQParser parser = new CQParser(new File(queryFile), ImmutableSet.of(tbox, aboxOntology));
		CQ cq = parser.parse();
		System.out.println(cq);
		qaHornSHIQ.setQuery(cq);
	}

	@Override
	public void loadQuery(int queryIndex) throws Exception {
		String queryFile = String.format("%s/Queries/Query_%02d.txt", dataSetRoot, queryIndex + 1);
		System.out.println(queryFile);
		this.queryFile = queryFile;
	}

	@Override
	public void loadQuery(String atomSymbol, int symbolType) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long runLoadedQuery() throws Exception {
		List<List<String>> results = qaHornSHIQ.execQuery();
		Joiner.on("\n").appendTo(System.out, results);
		System.out.println();
		// System.out.println(results);
		return results.size();
	}

	@Override
	public void clearRepository() throws Exception {
		qaHornSHIQ.clearOntologies();
		tbox = null;
		abox = null;
		// manager.removeOntology(abox);	
	}

    public void setDataSetRoot(String dataSetRoot) {
        this.dataSetRoot = dataSetRoot;
    }

    public void setDlvPath(String dlvPath) {
        this.dlvPath = dlvPath;
    }

    // public static void main(String[] args) {
	// ClipperApp
	// .main("-v=1 -rewriter=new query src/main/resources/ontologies/LUBM/univ-bench_TB-C_s/Query_01/Q(X0X44)-GraduateStudent(X0)takesCourse(X0X44)/Pattern_a_0;a_1;_D_.owl src/test/resources/university-q1.sparql"
	// .split("\\ "));
	// }
}
