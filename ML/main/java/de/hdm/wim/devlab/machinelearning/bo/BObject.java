package de.hdm.wim.devlab.machinelearning.bo;

/**
 * Superklasse aller für die Umsetzung relevanter Klassen. Sie legt Attribute
 * fest, die für alle Projekt-Objekt gelten, wie der UserID und der
 * DokumentenID.
 * 
 * @author Daniel Lepiorz
 */

public class BObject {

	/**
	 * Die ID des Objektes.
	 */
	private String uId;
	private String dId;

	/**
	 * Methode zum auslesen der ID.
	 */

	public String getUId() {
		return uId;
	}

	/**
	 * Methode zum setzen der ID.
	 */

	public void setUId(String uId) {
		this.uId = uId;
	}

	/**
	 * Methode zum auslesen der ID.
	 */

	public String getdId() {
		return dId;
	}

	/**
	 * Methode zum setzen der ID.
	 */
	public void setdId(String dId) {
		this.dId = dId;
	}

}
