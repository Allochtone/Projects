package projectiles;

import armes.Armes;
import controlleurs.PrincipalControlleur;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import tanks.Tank;

public abstract class Projectiles {
	private SimpleDoubleProperty rayonExplosion = new SimpleDoubleProperty();
	private double rayonexplosionMax;
	protected double dommages;
	protected Armes arme;
	protected SimpleDoubleProperty posX = new SimpleDoubleProperty();
	protected SimpleDoubleProperty posY = new SimpleDoubleProperty();
	protected int temps;
	protected double hauteur;
	protected double longueur;
	protected SimpleBooleanProperty exploser = new SimpleBooleanProperty();
	protected boolean contact;
	protected SimpleDoubleProperty vent = new SimpleDoubleProperty();

	public Projectiles(double hauteur, double longueur, Tank lanceur){
		setHauteur(hauteur);
		setLongueur(longueur);
		arme = lanceur.getArmeSelectionner();
		posX.set(lanceur.getPosX());
		posY.set(lanceur.getPosY());
		temps = 0;
		exploser.set(false);
		contact = false;
		rayonExplosion.set(2);
		setDommages(lanceur.getArmeSelectionner().getDommages());
		setVent(PrincipalControlleur.getCompteurVent().get());
		setRayonexplosionMax(lanceur.getArmeSelectionner().getRayonExplosion());
	}
	protected double getRayonexplosionMax(){
		return rayonexplosionMax;
	}

	protected void setRayonexplosionMax(double rayonexplosionMax){
		this.rayonexplosionMax=rayonexplosionMax;
	}

	public double getRayonExplosion() {
		return rayonExplosion.get();
	}

	protected void setDommages(double dommage) {
		dommages = dommage;
	}

	public SimpleDoubleProperty rayonExplosion() {
		return rayonExplosion;
	}

	public void setRayonExplosion(double rayonExplosion) {
		this.rayonExplosion.set(rayonExplosion);
	}

	public boolean isContact() {
		return contact;
	}

	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	public void setContact(boolean contact) {
		this.contact = contact;
	}

	public SimpleBooleanProperty exploser(){
		return exploser;
	}

	public boolean isExploser() {
		return exploser.get();
	}

	public void setExploser(boolean exploser) {
		this.exploser.set(exploser);
	}

	public abstract void explosion();

	public double getDommages(){
		return dommages;
	}

	public Object getArme(){
		return arme;
	}

	public double getPosX() {
		return posX.get();
	}

	public double getPosY() {
		return posY.get();
	}

	public void setPosX(double posX) {
		this.posX.set(posX);
	}

	public void setPosY(double posY) {
		this.posY.set(posY);
	}

	public SimpleDoubleProperty PosX() {
		return posX;

	};

	public SimpleDoubleProperty PosY() {
		return posY;

	};

	public double getTemps(){
		return temps;
	}

	public double getHauteur(){
		return hauteur;
	}

	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	public double getLongueur(){
		return longueur;
	}

	public void setArme(Armes arme){
		this.arme = arme;
	}

	public double getVent() {
		return vent.get();
	}

	public void setVent(double vent) {
		this.vent.set(vent);
	}

	public SimpleDoubleProperty Vent() {
		return vent;

	};

	public abstract void mouvement();
}
