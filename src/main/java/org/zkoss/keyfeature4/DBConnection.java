package org.zkoss.keyfeature4;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.WebApps;

public class DBConnection {

	private Connection conn;
	private static final String FOLDER_PATH = "/keyfeature4/data";

	public Connection getConnection() throws SQLException {
		try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			if (conn == null || conn.isClosed()) {
				WebApp wapp = WebApps.getCurrent();
				File f = new File(wapp.getRealPath(FOLDER_PATH));
				conn = DriverManager.getConnection("jdbc:relique:csv:" + f.getAbsolutePath());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
