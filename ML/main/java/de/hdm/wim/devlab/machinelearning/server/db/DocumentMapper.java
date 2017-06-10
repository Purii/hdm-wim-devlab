package de.hdm.wim.devlab.machinelearning.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.wim.devlab.machinelearning.bo.Document;
import de.hdm.wim.devlab.machinelearning.bo.User;


/**
 * Dies ist die Mapperklasse f�r die Dokumente. Sie stellt die Verbindung zur
 * Abo-Tabelle in der Datenbank her.
 * 
 * @mapper Document
 * @author Daniel Lepiorz
 * 
 */
public class DocumentMapper {

	// Variablendeklaration
	private static DocumentMapper documentMapper = null;

	/**
	 * Konstruktor des DocumentMappers
	 */

	protected DocumentMapper() {
	}

	/**
	 * erweiterter Konstruktor der eine neue Instanz anlegt, sofern es noch
	 * keine gibt
	 * 
	 * @return BeitragMapper
	 * @author Social Media Team
	 */

	public static DocumentMapper documentMapper() {
		if (documentMapper == null) {
			documentMapper = new DocumentMapper();
		}

		return documentMapper;
	}


	public Document insert(Document d) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM beitrag ");

			if (rs.next()) {

				d.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO beitrag (id, beitrag, sourceUser) "
						+ "VALUES ("
						+ d.getId()
						+ ",'"
						+ d.getDocument()
						+ "','" + d.getSourceUserID() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return d;
	}

	
	/**
	 * Diese Methode l�scht einn Dokuemnt aus der Datenbank.
	 * 
	 * @return void
	 * @author Daniel Lepiorz
	 * @param Document
	 */
	public void delete(Document d) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM document " + "WHERE id=" + d.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese gibt das komplette User-Objekt jenes Beitrags zurück, welcher der
	 * Methode übergeben wird.
	 * 
	 * @return User
	 * @author Social Media Team
	 * @param Beitrag
	 */
	public User getSourceID(Document d) throws SQLException {

		return UserMapper.userMapper().findByKey(d.getId());

	}

	

}
