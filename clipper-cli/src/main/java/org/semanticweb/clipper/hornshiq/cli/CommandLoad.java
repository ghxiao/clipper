package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
public class CommandLoad extends DBCommandBase {

	private Connection conn;

	public CommandLoad(JCommander jc) {
		super(jc);
	}

	@Override
	boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	void exec() {
		long t1 = System.currentTimeMillis();
		conn = createConnection();

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		ShortFormProvider sfp = new SimpleShortFormProvider();

		OWLOntology ontology = null;
		try {
			for (String ontologyFile : this.getOntologyFiles()) {
				ontology = manager.loadOntologyFromOntologyDocument(new File(
						ontologyFile));

				insertConcepts(stmt, sfp, ontology);

				insertObjectRoles(stmt, sfp, ontology);

				insertIndividuals(stmt, ontology);

				insertConceptAssertions(stmt, sfp, ontology);

				insertObjectRoleAssertions(stmt, sfp, ontology);

				manager.removeOntology(ontology);

				stmt.executeBatch();

				System.err.println(ontologyFile + " loaded!");
			}
			stmt.close();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println("TIME: " + (t2 - t1));

	}

	private void insertIndividuals(Statement stmt, OWLOntology ontology) {
		Set<OWLNamedIndividual> individuals = ontology
				.getIndividualsInSignature();

		String sql = "insert INTO individual_name (name)"
				+ "SELECT (?)"
				+ "WHERE NOT EXISTS (SELECT * FROM individual_name WHERE name=?)  ";

		try {
			conn.setAutoCommit(false);

			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			for (OWLNamedIndividual ind : individuals) {
				preparedStatement.setString(1, ind.toString());
				preparedStatement.setString(2, ind.toString());
				preparedStatement.executeUpdate();
				// String sql = String.format(
				// "INSERT INTO individual_name (name) VALUES ('%s')",
				// ind.toString());

				// String sql = String
				// .format("insert INTO individual_name (name)"
				// + "SELECT ('%s')"
				// +
				// "WHERE NOT EXISTS (SELECT * FROM individual_name WHERE name='%s')  ",
				// ind.toString(), ind.toString());

				// try {
				// stmt.addBatch(sql);
				// // stmt.execute(sql);
				// } catch (SQLException e) {
				// e.printStackTrace();
				// }

				conn.commit();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void insertConcepts(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {
		Set<OWLClass> classes = ontology.getClassesInSignature(true);

		for (OWLClass cls : classes) {
			String clsName = sfp.getShortForm(cls).toLowerCase();
			String sql = String
					.format("INSERT INTO predicate_name (name)"
							+ "SELECT ('%s')"
							+ "WHERE NOT EXISTS (SELECT * FROM predicate_name WHERE name='%s')  ",
							clsName, clsName);
			stmt.addBatch(sql);
			// stmt.execute(sql);

			sql = String.format("DROP TABLE IF EXISTS %s CASCADE", clsName);

			stmt.addBatch(sql);
			// stmt.execute(sql);

			sql = String.format("CREATE TABLE %s ("
					+ "individual integer NOT NULL )", clsName);

			stmt.addBatch(sql);
			// stmt.execute(sql);
		}
	}

	private void insertObjectRoles(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {

		Set<OWLObjectProperty> objectProperties = ontology
				.getObjectPropertiesInSignature(true);

		for (OWLObjectProperty property : objectProperties) {
			String propertyName = sfp.getShortForm(property).toLowerCase();
			String sql = String
					.format("INSERT INTO predicate_name (name)"
							+ "SELECT ('%s')"
							+ "WHERE NOT EXISTS (SELECT * FROM predicate_name WHERE name='%s')  ",
							propertyName, propertyName);
			stmt.addBatch(sql);
			// stmt.execute(sql);

			sql = String
					.format("DROP TABLE IF EXISTS %s CASCADE", propertyName);

			stmt.addBatch(sql);
			// stmt.execute(sql);

			sql = String.format("CREATE TABLE %s (" //
					+ "a integer NOT NULL, " //
					+ "b integer NOT NULL" + " )", propertyName);

			stmt.addBatch(sql);
			// stmt.execute(sql);
		}
	}

	private void insertConceptAssertions(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {
		Set<OWLClassAssertionAxiom> classAssertionAxioms = ontology.getAxioms(
				AxiomType.CLASS_ASSERTION, false);

		String sql = "INSERT INTO concept_assertion (concept , individual)            "
				+ "SELECT predicate_name.id, individual_name.id                  "
				+ "FROM predicate_name, individual_name                          "
				+ "WHERE predicate_name.name = ? and individual_name.name = ?"
				+ "AND NOT EXISTS (SELECT * FROM concept_assertion "
				+ "WHERE predicate_name.id = concept_assertion.concept "
				+ "   AND individual_name.id = concept_assertion.individual)";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		for (OWLClassAssertionAxiom axiom : classAssertionAxioms) {
			OWLClassExpression classExpression = axiom.getClassExpression();
			String tableName = sfp.getShortForm(classExpression.asOWLClass())
					.toLowerCase();
			
			preparedStatement.setString(1, tableName);
			preparedStatement.setString(2, axiom.getIndividual().toString());
			preparedStatement.executeUpdate();
			conn.commit();
			// String sql = String
			// .format("INSERT INTO concept_assertion (concept , individual)            "
			// +
			// "SELECT predicate_name.id, individual_name.id                  "
			// +
			// "FROM predicate_name, individual_name                          "
			// +
			// "WHERE predicate_name.name = '%s' and individual_name.name = '%s'"
			// + "AND NOT EXISTS (SELECT * FROM concept_assertion "
			// + "WHERE predicate_name.id = concept_assertion.concept "
			// + "   AND individual_name.id = concept_assertion.individual)",
			// tableName, axiom.getIndividual());

			//stmt.addBatch(sql);
			// stmt.execute(sql);
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
							+ "WHERE predicate_name.name = '%s' and ind1.name = '%s' and ind2.name = '%s'"
							+ "AND NOT EXISTS (SELECT * FROM object_role_assertion "
							+ "WHERE predicate_name.id = object_role_assertion.object_role "
							+ "   AND ind1.id = object_role_assertion.a"
							+ "   AND ind2.id = object_role_assertion.b)",
							tableName, axiom.getSubject(), axiom.getObject());

			stmt.addBatch(sql);
			// stmt.execute(sql);
		}
	}
}
