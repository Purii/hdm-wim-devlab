package de.hdm.wim.devlab.machinelearning.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	//To Do: Kommentierung
	//Info: Name und PW wurde nachträglich entfernt.
	private static Connection con = null;

	private static String url = "";

	public static Connection connection() {

		if (con == null) {
			try {

				con = DriverManager.getConnection(url);

			} catch (SQLException e1) {
				con = null;
				e1.printStackTrace();
			}

		}

		return con;

	}
}
