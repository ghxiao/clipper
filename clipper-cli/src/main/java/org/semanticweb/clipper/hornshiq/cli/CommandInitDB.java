package org.semanticweb.clipper.hornshiq.cli;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.semanticweb.owlapi.model.OWLObjectProperty;
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
@Parameters(commandNames = { "init" }, separators = "=", commandDescription = "Load ABox facts to Database")
public class CommandInitDB extends ReasoningCommandBase {

	public CommandInitDB(JCommander jc) {
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
		//long t1 = System.currentTimeMillis();
		// String url = "jdbc:postgresql://localhost/dlvdb_university";
		Properties props = new Properties();
//		props.setProperty("user", "xiao");
//		props.setProperty("password", "");
		props.setProperty("user", this.getUser());
		props.setProperty("pasword", this.getPassword());
		
		// props.setProperty("ssl", "true");
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, props);
			stmt = conn.createStatement();

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
			stmt.close();
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
