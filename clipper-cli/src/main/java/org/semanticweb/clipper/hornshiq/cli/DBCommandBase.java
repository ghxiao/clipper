package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBCommandBase extends ReasoningCommandBase {

	public DBCommandBase(JCommander jc) {
		super(jc);
	}

	protected Connection createConnection() {
		Properties props = new Properties();
		props.setProperty("user", this.getUser());
		props.setProperty("password", this.getPassword());
		// props.setProperty("ssl", "true");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(getJdbcUrl(), props);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return conn;
	}

	@Parameter(names = "-jdbcUrl", description = "JDBC URL")
	private String jdbcUrl;

	@Parameter(names = "-user", description = "User")
	private String user;

	@Parameter(names = "-password", description = "Password")
	private String password = "";

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
}
