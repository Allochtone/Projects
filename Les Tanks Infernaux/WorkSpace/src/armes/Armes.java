package armes;



public enum Armes {
	NORMAL("normal", "armes/normal.png", Type.PHYSIQUE,0.050f,35), SIN("sinus", "armes/sin(x).png", Type.ENERGIE,0.040f,0), COSEC("cosec","armes/cosec(x).png",
			Type.ENERGIE,0.075f,0), TAN("tan", "armes/tan(x).png", Type.ENERGIE,0.020f,0), ABS("absolue", "armes/abs(x).png",
					Type.ENERGIE,0.020f,0), RATIONNELE("rationnelle", "armes/rationelle.png", Type.ENERGIE,0.001f,0), REBOND("rebond",
							"armes/Rebond.png", Type.PHYSIQUE,0.060f,35), FRAGSOL("fragsol", "armes/fragSol.png",
									Type.PHYSIQUE,0.035f,25), FRAGAIR("fragair", "armes/fragAir.png", Type.PHYSIQUE,0.035f,30);
	private Object[] armes;
	private int selection;
	private String path;
	private String nom;
	private Type type;
	private double dommages;
	private double rayonExplosion;

	Armes(String nom, String path, Type type,double dommages,double rayonExplosion) {
		this.nom = nom;
		this.path = path;
		this.type = type;
		this.dommages = dommages;
		this.rayonExplosion = rayonExplosion;
	}

	public double getDommages() {
		return dommages;
	}

	public double getRayonExplosion() {
		return rayonExplosion;
	}

	public String getPath() {
		return path;
	}

	public String getNom() {
		return this.nom;
	}

	public String getTypenom() {
		return type.getNom();
	}

	public Object getArmeselectionner() {
		return armes[selection];
	}

}

enum Type {
	PHYSIQUE("Phys"), ENERGIE("Ener");

	private String nom;

	Type(String nom){
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

}

