package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import lombok.Getter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Getter
@Parameters(commandNames = { "load" }, separators = "=", commandDescription = "Load ABox facts to Database")
public class CommandLoad extends ReasoningCommandBase {

	public CommandLoad(JCommander jc) {
		super(jc);
	}

	@Parameter(names = "-jdbcUrl", description = "JDBC URL")
	private String jdbcUrl;

	@Override
	boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	void exec() {
		long t1 = System.currentTimeMillis();
		String url = "jdbc:postgresql://localhost/dlvdb_university";
		Properties props = new Properties();
		props.setProperty("user", "xiao");
		props.setProperty("password", "");
		// props.setProperty("ssl", "true");
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(url, props);
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		ShortFormProvider sfp = new SimpleShortFormProvider();

		OWLOntology ontology = null;
		for (String ontologyFile : this.getOntologyFiles()) {
			try {
				ontology = manager.loadOntologyFromOntologyDocument(new File(
						ontologyFile));

				Set<OWLClass> classes = ontology.getClassesInSignature();

				for (OWLClass cls : classes) {
					String clsName = sfp.getShortForm(cls).toLowerCase();
					String sql = String.format(
							"INSERT INTO predicate_name (name) VALUES ('%s')",
							clsName);
					stmt.execute(sql);
				}

				Set<OWLNamedIndividual> individuals = ontology
						.getIndividualsInSignature();

				for (OWLNamedIndividual ind : individuals) {
					// String indName = sfp.getShortForm(ind).toLowerCase();
					String sql = String.format(
							"INSERT INTO individual_name (name) VALUES ('%s')",
							ind.toString());
					stmt.execute(sql);
				}

				Set<OWLClassAssertionAxiom> classAssertionAxioms = ontology
						.getAxioms(AxiomType.CLASS_ASSERTION, false);

				for (OWLClassAssertionAxiom axiom : classAssertionAxioms) {
					OWLClassExpression classExpression = axiom
							.getClassExpression();
					String tableName = sfp.getShortForm(classExpression
							.asOWLClass()).toLowerCase();
					String sql = String
							.format("INSERT INTO concept_assertion (concept , individual)            "
									+ "SELECT predicate_name.id, individual_name.id                  "
									+ "FROM predicate_name, individual_name                          "
									+ "WHERE predicate_name.name = '%s' and individual_name.name = '%s'",
									tableName, axiom.getIndividual());
					stmt.executeUpdate(sql);

				}

				stmt.close();
			} catch (OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("TIME: " + (t2 - t1));

	}

}
