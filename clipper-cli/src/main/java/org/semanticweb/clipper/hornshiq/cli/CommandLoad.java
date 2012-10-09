package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Set;

import lombok.Getter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

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

	@Parameter(names = "-user", description = "User")
	private String user;

	@Parameter(names = "-password", description = "Password")
	private String password = "";

	@Override
	boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	void exec() {
		long t1 = System.currentTimeMillis();
		// String url = "jdbc:postgresql://localhost/dlvdb_university";
		Properties props = new Properties();
		props.setProperty("user", this.getUser());
		props.setProperty("password", this.getPassword());
		// props.setProperty("ssl", "true");
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, props);
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

				insertConcepts(stmt, sfp, ontology);

				insertObjectRoles(stmt, sfp, ontology);

				insertIndividuals(stmt, ontology);

				insertConceptAssertions(stmt, sfp, ontology);

				insertObjectRoleAssertions(stmt, sfp, ontology);

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

	private void insertIndividuals(Statement stmt, OWLOntology ontology)
			throws SQLException {
		Set<OWLNamedIndividual> individuals = ontology
				.getIndividualsInSignature();

		for (OWLNamedIndividual ind : individuals) {
			String sql = String.format(
					"INSERT INTO individual_name (name) VALUES ('%s')",
					ind.toString());
			stmt.execute(sql);
		}
	}

	private void insertConcepts(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {
		Set<OWLClass> classes = ontology.getClassesInSignature(true);

		for (OWLClass cls : classes) {
			String clsName = sfp.getShortForm(cls).toLowerCase();
			String sql = String.format(
					"INSERT INTO predicate_name (name) VALUES ('%s')", clsName);
			stmt.execute(sql);

			sql = String.format("DROP TABLE IF EXISTS %s CASCADE", clsName);

			stmt.execute(sql);

			sql = String.format("CREATE TABLE %s ("
					+ "individual integer NOT NULL )", clsName);

			stmt.execute(sql);
		}
	}

	private void insertObjectRoles(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {

		Set<OWLObjectProperty> objectProperties = ontology
				.getObjectPropertiesInSignature(true);

		for (OWLObjectProperty property : objectProperties) {
			String propertyName = sfp.getShortForm(property).toLowerCase();
			String sql = String.format(
					"INSERT INTO predicate_name (name) VALUES ('%s')",
					propertyName);
			stmt.execute(sql);

			sql = String
					.format("DROP TABLE IF EXISTS %s CASCADE", propertyName);

			stmt.execute(sql);

			sql = String.format("CREATE TABLE %s (" //
					+ "a integer NOT NULL, " //
					+ "b integer NOT NULL" + " )", propertyName);

			stmt.execute(sql);
		}
	}

	private void insertConceptAssertions(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {
		Set<OWLClassAssertionAxiom> classAssertionAxioms = ontology.getAxioms(
				AxiomType.CLASS_ASSERTION, false);

		for (OWLClassAssertionAxiom axiom : classAssertionAxioms) {
			OWLClassExpression classExpression = axiom.getClassExpression();
			String tableName = sfp.getShortForm(classExpression.asOWLClass())
					.toLowerCase();
			String sql = String
					.format("INSERT INTO concept_assertion (concept , individual)            "
							+ "SELECT predicate_name.id, individual_name.id                  "
							+ "FROM predicate_name, individual_name                          "
							+ "WHERE predicate_name.name = '%s' and individual_name.name = '%s'",
							tableName, axiom.getIndividual());

			stmt.executeUpdate(sql);
		}
	}

	private void insertObjectRoleAssertions(Statement stmt,
			ShortFormProvider sfp, OWLOntology ontology) throws SQLException {
		Set<OWLObjectPropertyAssertionAxiom> objectRoleAssertionAxioms = ontology
				.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION, false);

		for (OWLObjectPropertyAssertionAxiom axiom : objectRoleAssertionAxioms) {
			OWLObjectProperty property = axiom.getProperty()
					.asOWLObjectProperty();

			String tableName = sfp.getShortForm(property).toLowerCase();
			String sql = String
					.format("INSERT INTO object_role_assertion (object_role, a, b)            "
							+ "SELECT predicate_name.id, ind1.id, ind2.id                  "
							+ "FROM predicate_name, individual_name as ind1, individual_name as ind2 "
							+ "WHERE predicate_name.name = '%s' and ind1.name = '%s' and ind2.name = '%s'",
							tableName, axiom.getSubject(), axiom.getObject());

			stmt.executeUpdate(sql);
		}
	}
}
