package musiquesEtSons;

public enum Musiques {
	WITHGANDC("withGunAndCrucifix", "res/musiques/withGAndC.mp3", 175), CHEVAUCHEE("chavauchee",
			"res/musiques/chevauchee.mp3", 290), BATTLE("battle", "res/musiques/musiqueDeroulement1.mp3", 188), TOCCATA(
					"toccata", "res/musiques/toccataAndFugues.mp3",
					498), GHOST_DIVISION("ghost division", "res/musiques/ghostDivision.m4a", 230);

	private String path, nom;
	private int duree;

	private Musiques(String nom, String path, int duree) {
		this.nom = nom;
		this.path = path;
		this.duree = duree;
	}

	public int getDuree() {
		return duree;
	}

	public String getPath() {
		return path;
	}

	public String getNom() {
		return nom;
	}

}
