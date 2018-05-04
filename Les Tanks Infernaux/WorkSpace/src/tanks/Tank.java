package tanks;

import armes.Armes;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Polygon;

public abstract class Tank {
	protected Armes[] armes = Armes.values();
	protected Armes armeSelectionner = armes[0];
	protected SimpleDoubleProperty pointVie = new SimpleDoubleProperty();
	protected SimpleDoubleProperty gaz = new SimpleDoubleProperty();
	protected SimpleDoubleProperty posX = new SimpleDoubleProperty();
	protected SimpleDoubleProperty posY = new SimpleDoubleProperty();
	protected SimpleDoubleProperty rotation = new SimpleDoubleProperty();
	protected int vitesse;
	protected Polygon tank;
	protected SimpleDoubleProperty cannonX = new SimpleDoubleProperty();
	protected SimpleDoubleProperty cannonY = new SimpleDoubleProperty();
	public static final int MAX_X = 1224;
	public static final int MIN_X = 0;
	public static final int MAX_Y = 664;
	public static final int MIN_Y = 0;
	private char direction;

	public Tank(double posX, double posY) {
		setPosX(posX);
		setPosY(posY);
	}

	public double getPosX() {
		return posX.get();
	}

	public double getPosY() {
		return posY.get();
	}

	public void setPosX(double posX) {
		if (verifierX(posX)) {
			this.posX.set(posX);
		}
	}

	private boolean verifierX(double posX) {
		boolean result = false;
		if ((posX >= MIN_X) && (posX <= MAX_X)) {
			result = true;
		}
		return result;
	}

	public void setPosY(double posY) {
		if (verifierY(posY)) {
			this.posY.set(posY);
		}
	}

	private boolean verifierY(double posY) {
		boolean result = false;
		if ((posY >= MIN_Y) && (posY <= MAX_Y)) {
			result = true;
		}
		return result;
	}

	public SimpleDoubleProperty PosX() {
		return posX;

	};

	public SimpleDoubleProperty PosY() {
		return posY;

	}

	public Polygon getTank() {
		return tank;

	}

	public SimpleDoubleProperty pointVie() {
		return pointVie;
	}

	public double getPointVie() {
		return pointVie().get();
	}

	public void setPointVie(double pointVie) {
		if (pointVie <= 0) {
			this.pointVie.set(0);
		} else {
			this.pointVie.set(pointVie);
		}
	}

	public SimpleDoubleProperty gaz() {
		return gaz;
	}

	public double getGaz() {
		return gaz().get();
	}

	public void setGaz(double gaz) {
		if (gaz <= 0) {
			this.gaz.set(0);
		} else if (gaz >= 1) {
			this.gaz.set(1);
		} else {
			this.gaz.set(gaz);
		}
	}

	public Armes getArmeSelectionner() {
		return armeSelectionner;
	}

	public void setArmeSelectionner(int i) {
		if (validerInt(i)) {
			armeSelectionner = armes[i];
		} else {
			armeSelectionner = armes[0];
		}
	}

	private boolean validerInt(int i) {
		boolean retour = false;
		if (i < armes.length) {
			retour = true;
		}
		return retour;

	}

	public Armes[] getArmes() {
		return armes;
	}

	public void setCannonX(double x) {
		this.cannonX.set(x);
		;
	}

	public void setCannonY(double y) {
		this.cannonY.set(y);
		;
	}

	public double getCannonX() {
		return cannonX.get();
	}

	public double getCannonY() {
		return cannonY.get();
	}

	public SimpleDoubleProperty cannonX() {
		return this.cannonX;
	}

	public SimpleDoubleProperty cannonY() {
		return this.cannonY;
	}

	public double getRotation() {
		return rotation.get();
	}

	public void setRotation(double rotation) {
		this.rotation.set(rotation);
	}

	public SimpleDoubleProperty rotation() {
		return this.rotation;
	}

	public char getDirection() {

		return this.direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

}
