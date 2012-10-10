package org.semanticweb.clipper.hornshiq.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import lombok.Getter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

@Getter
@Parameters(commandNames = { "init" }, separators = "=", commandDescription = "Load ABox facts to Database")
public class CommandInitDB extends DBCommandBase {

	public CommandInitDB(JCommander jc) {
		super(jc);
	}

	@Override
	boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	void exec() {
		
		Connection conn = createConnection();

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			InputStream stream = this.getClass().getResourceAsStream(
					"/sql/dlvdb_schema.sql");
			// BufferedInputStream b = new BufferedInputStream(stream);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}

			String sql = sb.toString();

			stmt.execute(sql);
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

			ShortFormProvider sfp = new SimpleShortFormProvider();

			OWLOntology ontology = null;
			for (String ontologyFile : this.getOntologyFiles()) {
				try {
					ontology = manager
							.loadOntologyFromOntologyDocument(new File(
									ontologyFile));
					insertConcepts(stmt, sfp, ontology);
					insertObjectRoles(stmt, sfp, ontology);
				} catch (OWLOntologyCreationException e) {
					e.printStackTrace();
				}
			}

			stmt.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void insertConcepts(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {
		Set<OWLClass> classes = ontology.getClassesInSignature(true);

		for (OWLClass cls : classes) {
			String clsName = sfp.getShortForm(cls).toLowerCase();

			String sql = String.format("DROP TABLE IF EXISTS %s CASCADE",
					clsName);

			stmt.execute(sql);

			sql = String.format("CREATE TABLE %s ("
					+ "individual integer NOT NULL )", clsName);

			// System.err.println(sql);

			stmt.execute(sql);
		}
	}

	private void insertObjectRoles(Statement stmt, ShortFormProvider sfp,
			OWLOntology ontology) throws SQLException {

		Set<OWLObjectProperty> objectProperties = ontology
				.getObjectPropertiesInSignature(true);

		for (OWLObjectProperty property : objectProperties) {
			String propertyName = sfp.getShortForm(property).toLowerCase();

			String sql = String.format("DROP TABLE IF EXISTS %s CASCADE",
					propertyName);

			stmt.execute(sql);

			sql = String.format("CREATE TABLE %s (" //
					+ "a integer NOT NULL, " //
					+ "b integer NOT NULL" + " )", propertyName);

			// System.err.println(sql);

			stmt.execute(sql);
		}
	}

}
