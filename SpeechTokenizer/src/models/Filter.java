package models;


public class Filter {
	private Synonym my_synonym;
	private Stemmer my_stemmer;
	private Stop my_stop;
	public Synonym getMy_synonym() {
		return my_synonym;
	}
	public void setMy_synonym(Synonym my_synonym) {
		this.my_synonym = my_synonym;
	}
	public Stemmer getMy_stemmer() {
		return my_stemmer;
	}
	public void setMy_stemmer(Stemmer my_stemmer) {
		this.my_stemmer = my_stemmer;
	}
	public Stop getMy_stop() {
		return my_stop;
	}
	public void setMy_stop(Stop my_stop) {
		this.my_stop = my_stop;
	}
	
	

}
