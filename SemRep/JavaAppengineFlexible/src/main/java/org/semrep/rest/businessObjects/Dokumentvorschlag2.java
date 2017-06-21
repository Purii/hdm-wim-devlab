package main.java.org.semrep.rest.businessObjects;

public class Dokumentvorschlag2 {
	
	private String dok_IDStr;
	private String dok_NameStr;
	private String prio;
	private String dok_TypStr;
	private String dok_URLStr;
	private String dok_folder;
	
	private String sessionID;
	private String timeStamp;
	private String objectValue;
	
	public void flushDokumentvorschlag() {
		setDok_IDStr("");
		setDok_NameStr("");
		setPrio("");
		setDok_TypStr("");
		setDok_URLStr("");
		setDok_folder("");
	}
	
	public void flushAllDokumentvorschlag() {		
		setSessionID("");
		setTimeStamp("");
		setDok_IDStr("");
		setDok_NameStr("");
		setPrio("");
		setDok_TypStr("");
		setDok_URLStr("");
		setDok_folder("");
	}
	
	public String toStringFavoritDokumentObjekt() {
		return "SessionID=" + this.sessionID + "; " + "TimeStamp=" + this.timeStamp
				+ "; " + "Dokument=" + this.objectValue;
	}
	
	public String toStringDokumentvorschlagsObjekt() {
		return "SessionID=" + this.sessionID + "; " + "TimeStamp=" + this.timeStamp
				+ "; " + "Dokument=" + this.objectValue;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
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

	public String getDok_TypStr() {
		return dok_TypStr;
	}

	public void setDok_TypStr(String dok_TypStr) {
		this.dok_TypStr = dok_TypStr;
	}

	public String getPrio() {
		return prio;
	}

	public void setPrio(String prio) {
		this.prio = prio;
	}

	public String getDok_folder() {
		return dok_folder;
	}

	public void setDok_folder(String dok_folder) {
		this.dok_folder = dok_folder;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}
	
}
