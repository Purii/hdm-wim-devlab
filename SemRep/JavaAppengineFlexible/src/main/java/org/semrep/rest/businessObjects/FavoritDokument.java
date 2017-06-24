package org.semrep.rest.businessObjects;

public class FavoritDokument {
	
	public String favoritDokNameStr;

	//Konstruktor
	public FavoritDokument(String favoritDokNameStr) {

		this.favoritDokNameStr = favoritDokNameStr;
	}

	//Get- und Set-Methode für Parameter: favoritDokNameStr
	public String getFavoritDokNameStr() {

		return favoritDokNameStr;
	}

	public void setFavoritDokNameStr(String favoritDokNameStr) {

		this.favoritDokNameStr = favoritDokNameStr;
	}

}
