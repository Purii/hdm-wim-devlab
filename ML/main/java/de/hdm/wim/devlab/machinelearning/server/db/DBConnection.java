package de.hdm.wim.devlab.machinelearning.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;

import de.hdm.wim.devlab.machinelearning.main.ReceiveMessageServlet;

/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * @author Daniel Lepiorz
 * 
 * 
 */
public class DBConnection {

	/**
	 * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see ReceiveMessageServlet
	 */
	private static Connection con = null;

	/**
	 * Die URL, mit deren Hilfe die Datenbank angesprochen wird. Die
	 * Zeichenkette wird aus den Systemeigenschaften gelesen.
	 */

	private static String url = System.getProperty("ae-cloudsql.cloudsql-database-url");

	public static Connection connection() {

		if (con == null) {
			try {
				// Ersteinmal muss der passende DB-Treiber geladen werden
				Class.forName("com.mysql.jdbc.GoogleDriver");
			} catch (ClassNotFoundException e) {
				try {
					throw new ServletException("Error loading Google JDBC Driver", e);
				} catch (ServletException e1) {
					e1.printStackTrace();
				}
			}

		}
		try {
			con = DriverManager.getConnection(url);

		} catch (SQLException e1) {
			con = null;
			e1.printStackTrace();
		}

		// Zurückgegeben der Verbindung
		return con;

	}
}