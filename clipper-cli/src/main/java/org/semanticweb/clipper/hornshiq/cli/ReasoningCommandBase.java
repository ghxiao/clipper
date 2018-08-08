package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.io.CharStreams;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.sparql.SparqlToCQConverter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Common base class for CommandQuery and CommandRewrite
 *
 * @author xiao
 *
 */
public abstract class ReasoningCommandBase extends CommandBase {

	@Parameter(description = "<ontology1.owl> ... <ontologyk.owl>")
	protected List<String> ontologyFiles;

	@Parameter(names = "-cq", description = "<query.cq> query file in CQ format")
	protected String cqFile;

	@Parameter(names = "-sparql", description = "<query.sparql> query file in SPARQL format")
	protected String sparqlFile;

    @Parameter(names = "-sparqlString", description = "Sparql Query String")
    protected String sparqlString;

	@Parameter(names = "-name", description = "")
	protected NamingStrategy namingStrategy = NamingStrategy.LOWER_CASE_FRAGMENT;

	@Parameter(names = { "-r", "-rewriter" }, description = "rewriter", hidden = true)
	protected String rewriter = "new";

	public ReasoningCommandBase(JCommander jc) {
		super(jc);
		jc.addCommand(this);
	}

	protected CQ parseCQ(Set<OWLOntology> ontologies) {
		CQ cq = null;

        if(sparqlString!=null){
            Query query = QueryFactory.create(sparqlString);
            cq = new SparqlToCQConverter(ontologies.iterator().next()).compileQuery(query);
        } else if (sparqlFile != null) {
			try {
                FileReader reader = new FileReader(new File(sparqlFile));

                String queryString = CharStreams.toString(reader);

                Query query = QueryFactory.create(queryString);

                cq = new SparqlToCQConverter(ontologies.iterator().next()).compileQuery(query);

                reader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (cqFile != null) {
			CQParser parser;
			try {
				parser = new CQParser(new File(cqFile), ontologies);
				cq = parser.parse();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalStateException();
		}

		return cq;
	}

	protected Set<OWLOntology> loadOntologies() {
		Set<OWLOntology> ontologies = new HashSet<OWLOntology>();

		for (String ontologyFile : this.getOntologyFiles()) {
			try {
				OWLOntology ontology = OWLManager.createOWLOntologyManager()
						.loadOntologyFromOntologyDocument(
								new File(ontologyFile));

				ontologies.add(ontology);
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}
		}
		return ontologies;
	}

	protected String errorMessage;

    public List<String> getOntologyFiles() {
        return this.ontologyFiles;
    }

    public String getCqFile() {
        return this.cqFile;
    }

    public String getSparqlFile() {
        return this.sparqlFile;
    }

    public String getSparqlString() {
        return this.sparqlString;
    }

    public NamingStrategy getNamingStrategy() {
        return this.namingStrategy;
    }

    public String getRewriter() {
        return this.rewriter;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
