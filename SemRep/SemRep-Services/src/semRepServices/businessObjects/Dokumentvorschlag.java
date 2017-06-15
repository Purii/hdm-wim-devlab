package semRepServices.businessObjects;

public class Dokumentvorschlag {
	
	public String sessionID;
	public String timeStamp;
	public String dok_IDStr;
	public String dok_NameStr;
	public String prio;
	public String dok_TypStr;
	public String dok_URLStr;
	public String dok_folder;
	
	public Dokumentvorschlag(){
		
	}
	
	public Dokumentvorschlag(String sessionID, String timeStamp, String dok_IDStr, String dok_NameStr, String prio,
			String dok_TypStr, String dok_URLStr, String dok_folder) {
		this.sessionID = sessionID;
		this.timeStamp = timeStamp;
		this.dok_IDStr = dok_IDStr;
		this.dok_NameStr = dok_NameStr;
		this.prio = prio;
		this.dok_TypStr = dok_TypStr;
		this.dok_URLStr = dok_URLStr;
		this.dok_folder = dok_folder;
	}

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
	
}
