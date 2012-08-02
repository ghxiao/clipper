package org.semanticweb.clipper.eval;

import java.io.File;

import org.oxford.comlab.compass.SystemInterface;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.InternalCQParser;

public class ClipperSygenia implements SystemInterface {

	private QAHornSHIQ qaHornSHIQ;

	@Override
	public void initializeSystem() throws Exception {
		qaHornSHIQ = new QAHornSHIQ();
	}

	@Override
	public void shutdown() {

	}

	@Override
	public void loadTestToSystem(String ontologyFile, File aBox) throws Exception {
		qaHornSHIQ.setOntologyName(ontologyFile);

	}

	@Override
	public void loadQuery(int queryIndex) throws Exception {
		String queryFile = String.format("%02d", queryIndex);
		CQParser parser = new CQParser(new File(queryFile));
		
		
		InternalCQParser parser = new InternalCQParser();
		
	}

	@Override
	public void loadQuery(String atomSymbol, int symbolType) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public long runLoadedQuery() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearRepository() throws Exception {
		// TODO Auto-generated method stub

	}

}
