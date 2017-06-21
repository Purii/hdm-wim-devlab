package main.java.org.semrep.rest.businessObjects;

public class Dokument {

	public String dok_NameStr;
	public String dok_IDStr;
	public String dok_URLStr;
	public String dok_erstelldatumStr;
	public String dok_VersionStr;
	public String dok_TypStr;
	public String dokument_verfasst_von_Person;
	public String dokument_gehoert_zu_Projekt;
	public String dokument_favorisiert_von_Person;
	public String dokument_hat_Keyword;
	public String dok_folder;
	
	private String sessionID; 
	public String timeStamp;
	private String identity; 
	private String objectValue;
	
	public Dokument(String sessionID, String timeStamp, String identity, String objectValue) {

		this.sessionID = sessionID;
		this.timeStamp = timeStamp;
		this.identity = identity;
		this.objectValue = objectValue;

	}
	
	public Dokument(String dok_NameStr, String dok_IDStr, String dok_URLStr,
			String dok_erstelldatumStr, String dok_VersionStr, String dok_TypStr, 
			String dok_folder, String dokument_verfasst_von_Person, String dokument_gehoert_zu_Projekt,
			String dokument_favorisiert_von_Person, String dokument_hat_Keyword) {

		this.dokument_verfasst_von_Person = dokument_verfasst_von_Person;
		this.dokument_gehoert_zu_Projekt = dokument_gehoert_zu_Projekt;
		this.dok_NameStr = dok_NameStr;
		this.dok_IDStr = dok_IDStr;
		this.dok_URLStr = dok_URLStr;
		this.dok_erstelldatumStr = dok_erstelldatumStr;
		this.dok_VersionStr = dok_VersionStr;
		this.dok_TypStr = dok_TypStr;
		this.dok_folder = dok_folder;
		this.dokument_favorisiert_von_Person = dokument_favorisiert_von_Person;
		this.dokument_hat_Keyword = dokument_hat_Keyword;
	}
	
	public String toStringAlleDokumentObjekt() {
		return "SessionID=" + this.sessionID 
				+ ", " + "TimeStamp=" + this.timeStamp
				+ ", " + "TokenID=" + this.identity
				+ ", " + this.objectValue;
	}
	
	public String toStringDokumentObjekt() {
		return "SessionID=" + this.sessionID 
				+ ", " + "TimeStamp=" + this.timeStamp
				+ ", " + "TokenID=" + this.identity
				+ ", " + this.objectValue;
	}

	public void flushDokumentObjekt() {
		setDok_erstelldatumStr("");
		setDok_IDStr("");
		setDok_NameStr("");
		setDok_TypStr("");
		setDok_folder("");
		setDok_URLStr("");
		setDok_VersionStr("");
		setDokument_gehoert_zu_Projekt("");
		setDokument_verfasst_von_Person("");
		setDokument_favorisiert_von_Person("");
		setDokument_hat_Keyword("");
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
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

	public String getDok_folder() {
		return dok_folder;
	}

	public void setDok_folder(String dok_folder) {
		this.dok_folder = dok_folder;
	}

	public String getDokument_hat_Keyword() {
		return dokument_hat_Keyword;
	}

	public void setDokument_hat_Keyword(String dokument_hat_Keyword) {
		this.dokument_hat_Keyword = dokument_hat_Keyword;
	}

	public String getDok_NameStr() {
		return dok_NameStr;
	}

	public void setDok_NameStr(String dok_NameStr) {
		this.dok_NameStr = dok_NameStr;
	}

	public String getDok_IDStr() {
		return dok_IDStr;
	}

	public void setDok_IDStr(String dok_IDStr) {
		this.dok_IDStr = dok_IDStr;
	}

	public String getDok_URLStr() {
		return dok_URLStr;
	}

	public void setDok_URLStr(String dok_URLStr) {
		this.dok_URLStr = dok_URLStr;
	}

	public String getDok_erstelldatumStr() {
		return dok_erstelldatumStr;
	}

	public void setDok_erstelldatumStr(String dok_erstelldatumStr) {
		this.dok_erstelldatumStr = dok_erstelldatumStr;
	}

	public String getDok_VersionStr() {
		return dok_VersionStr;
	}

	public void setDok_VersionStr(String dok_VersionStr) {
		this.dok_VersionStr = dok_VersionStr;
	}

	public String getDok_TypStr() {
		return dok_TypStr;
	}

	public void setDok_TypStr(String dok_TypStr) {
		this.dok_TypStr = dok_TypStr;
	}

	public String getDokument_verfasst_von_Person() {
		return dokument_verfasst_von_Person;
	}

	public void setDokument_verfasst_von_Person(String dokument_verfasst_von_Person) {
		this.dokument_verfasst_von_Person = dokument_verfasst_von_Person;
	}

	public String getDokument_gehoert_zu_Projekt() {
		return dokument_gehoert_zu_Projekt;
	}

	public void setDokument_gehoert_zu_Projekt(String dokument_gehoert_zu_Projekt) {
		this.dokument_gehoert_zu_Projekt = dokument_gehoert_zu_Projekt;
	}

	public String getDokument_favorisiert_von_Person() {
		return dokument_favorisiert_von_Person;
	}

	public void setDokument_favorisiert_von_Person(String dokument_favorisiert_von_Person) {
		this.dokument_favorisiert_von_Person = dokument_favorisiert_von_Person;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

}
