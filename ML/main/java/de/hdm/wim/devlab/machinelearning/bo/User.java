package de.hdm.wim.devlab.machinelearning.bo;


public class User extends BObject{

	


	  private String vorname = "";


	  private String nachname = "";


	  private String email = "";


	  public String getVorname() {
	    return this.vorname;
	  }


	  public void setVorname(String name) {
	    this.vorname = name;
	  }


	  public String getNachname() {
	    return this.nachname;
	  }


	  public void setNachname(String name) {
	    this.nachname = name;
	  }



	public User() {

	}

	  public User(String vname, String nname, String niname, String mail) {
		this.vorname = vname;
		this.nachname = nname;
		this.email = mail;

	}


	  public String toString() {
	    return super.toString() + " " + this.vorname + " " + this.nachname + " " +  this.email;
	  }


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


}

