package semRepServices.businessObjects;

public class Projekt {

	public String projektID;
	public String projektName;
	public String projekt_gehoert_zu_Unternehmen;
	public String projekt_gehoert_zu_Abteilung;
	public String projekt_hat_Projektmitglied;
	public String projekt_hat_Dokument;

	private String sessionID;
	private String identity;
	private String objectValue;

	public Projekt(String sessionID, String identity, String objectValue) {
		
		this.sessionID = sessionID;
		this.identity = identity;
		this.objectValue = objectValue;
		
	}

	public Projekt(String projektID, String projektName, String projekt_gehoert_zu_Unternehmen,
			String projekt_gehoert_zu_Abteilung, String projekt_hat_Projektmitglied, String projekt_hat_Dokument) {
		this.projektID = projektID;
		this.projektName = projektName;
		this.projekt_gehoert_zu_Unternehmen = projekt_gehoert_zu_Unternehmen;
		this.projekt_gehoert_zu_Abteilung = projekt_gehoert_zu_Abteilung;
		this.projekt_hat_Projektmitglied = projekt_hat_Projektmitglied;
		this.projekt_hat_Dokument = projekt_hat_Dokument;
	}
	
	public String toStringProjektObjekt() {
		return "SessionID=" + this.sessionID + "; " + "TokenID=" + this.identity
				+ "; " + this.objectValue;
	}

	public void flushProjektObjekt() {
		setProjektID("");
		setProjektName("");
		setProjekt_gehoert_zu_Unternehmen("");
		setProjekt_gehoert_zu_Abteilung("");
		setProjekt_hat_Projektmitglied("");
		setProjekt_hat_Dokument("");
	}
	
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getProjektID() {
		return projektID;
	}

	public void setProjektID(String projektID) {
		this.projektID = projektID;
	}

	public String getProjektName() {
		return projektName;
	}

	public void setProjektName(String projektName) {
		this.projektName = projektName;
	}

	public String getProjekt_gehoert_zu_Unternehmen() {
		return projekt_gehoert_zu_Unternehmen;
	}

	public void setProjekt_gehoert_zu_Unternehmen(String projekt_gehoert_zu_Unternehmen) {
		this.projekt_gehoert_zu_Unternehmen = projekt_gehoert_zu_Unternehmen;
	}

	public String getProjekt_gehoert_zu_Abteilung() {
		return projekt_gehoert_zu_Abteilung;
	}

	public void setProjekt_gehoert_zu_Abteilung(String projekt_gehoert_zu_Abteilung) {
		this.projekt_gehoert_zu_Abteilung = projekt_gehoert_zu_Abteilung;
	}

	public String getProjekt_hat_Projektmitglied() {
		return projekt_hat_Projektmitglied;
	}

	public void setProjekt_hat_Projektmitglied(String projekt_hat_Projektmitglied) {
		this.projekt_hat_Projektmitglied = projekt_hat_Projektmitglied;
	}

	public String getProjekt_hat_Dokument() {
		return projekt_hat_Dokument;
	}

	public void setProjekt_hat_Dokument(String projekt_hat_Dokument) {
		this.projekt_hat_Dokument = projekt_hat_Dokument;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

}
