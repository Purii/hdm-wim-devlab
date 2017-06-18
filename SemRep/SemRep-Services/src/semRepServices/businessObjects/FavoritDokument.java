package semRepServices.businessObjects;

public class FavoritDokument {
	
	public String favoritDokNameStr;
	
	public FavoritDokument(String favoritDokNameStr) {
		this.favoritDokNameStr = favoritDokNameStr;
	}

	public String getFavoritDokNameStr() {
		return favoritDokNameStr;
	}

	public void setFavoritDokNameStr(String favoritDokNameStr) {
		this.favoritDokNameStr = favoritDokNameStr;
	}

}
