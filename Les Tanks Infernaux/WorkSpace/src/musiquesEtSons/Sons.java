package musiquesEtSons;

/**
 * 
 * Enum des différents sons pouvant être joués selon le contexte
 *
 */
public enum Sons {
	EXPLOSION1CONTACTDIRECT("res/musiques/sons/explosion1ArmeNormale.m4a", 1.25), DEPLACEMENT(
			"res/musiques/sons/sonTankMouvement.m4a",
			27), TIR_ENERGIE("res/musiques/sons/tirEnergie.wav", 2), REBOND_SON("res/musiques/sons/rebondSon.m4a", 1);

	private String path;
	private double duree;

	private Sons(String path, double duree) {
		this.path = path;
		this.duree = duree;
	}

	public String getPath() {
		return path;
	}

	public double getDuree() {
		return duree;

	}
}
