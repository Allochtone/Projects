package projectiles;

import java.util.ArrayList;

import controlleurs.PrincipalControlleur;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import musiquesEtSons.SonsGestion;

import tanks.Tank;
import terrain.IniTerrain;

public class ProjectilePhys extends Projectiles {

	private int rebonds;
	private Point2D pointfinal = new Point2D(0, 0);
	private SimpleBooleanProperty frag = new SimpleBooleanProperty(false);

	private static final double GRAVITE = 98 / 10;

	public ProjectilePhys(double hauteur, double longueur, Tank lanceur) {
		super(hauteur, longueur, lanceur);
		pointfinal = getpointFinal();
		setPosX(lanceur.getPosX());
		setPosY(lanceur.getPosY());
	}

	private Point2D getpointFinal() {
		try {
			ArrayList<Rectangle> terrain = IniTerrain.getTerrain();
			for (int y = 0; y < IniTerrain.getTerrain().size(); y++) {
				if ((int) getPosX() == (int) terrain.get(y).getTranslateX()) {
					if (getPosY() >= terrain.get(y).getTranslateY()) {
						temps = 0;
						return new Point2D(getPosX(), getPosY());
					}
				}
			}

			mouvement();

			return getpointFinal();
		} catch (StackOverflowError e) {
			return new Point2D(0, 0);
		}
	}

	private Point2D getpointfinal() {
		return pointfinal;

	}

	/**
	 * La methode qui dicte le déplacement des projectiles et ses comportements.
	 */
	@Override
	public void mouvement() {
		String nomArme = arme.getNom();
		switch (nomArme) {
		case "normal":
			posX.set(posX.get() + longueur / 80 + getVent() / 10 * temps / 100);
			posY.set(posY.get() - hauteur / 20 + GRAVITE * temps / 20);
			break;
		case "rebond":
			posX.set(posX.get() + longueur / 80 + getVent() / 10 * temps / 100);
			posY.set(posY.get() - hauteur / 20 + GRAVITE * temps / 20);
			break;
		case "fragsol":
			posX.set(posX.get() + longueur / 80 + getVent() / 10 * temps / 100);
			posY.set(posY.get() - hauteur / 20 + GRAVITE * temps / 20);
			if (pointfinal.getX() != 0.0) {
				if (getLongueur() >= 0) {
					if (getPosX() >= getpointfinal().getX()) {
						if (frag.get() == false) {
							frag.set(true);
						}
					}
				}
			}
			break;
		case "fragair":
			posX.set(posX.get() + longueur / 80 + getVent() / 10 * temps / 100);
			posY.set(posY.get() - hauteur / 20 + GRAVITE * temps / 20);
			if (pointfinal.getX() != 0.0) {
				if (getLongueur() >= 0) {
					if (getPosX() >= getpointfinal().getX() / 2) {
						if (frag.get() == false) {
							frag.set(true);
							}
					}
				} else if (getPosX()/2 <= getpointfinal().getX()) {
					if (frag.get() == false) {
						frag.set(true);
					}
				}
			}
		}

		temps = temps + 1;
	}

	public SimpleBooleanProperty frag() {
		return frag;
	}

	public boolean getfrag() {
		return frag.get();
	}

	public void setfrag(boolean frag) {
		frag().set(frag);
	}

	@Override
	public void setContact(boolean contact) {
		if (arme.getNom() == "rebond") {
			if (contact == true) {
				rebonds++;
				setHauteur(getHauteur() - getHauteur() * IniTerrain.getConstanteRebondTerrain());
				this.temps = 0;
				if (rebonds >= 3) {
					this.contact = true;
					SonsGestion.jouerSonRebondJoueur();
				} else {
					this.contact = false;
					SonsGestion.arretSonRebondJoueur();
				}
			}
		} else if (arme.getNom() == "fragsol") {
			if (frag.get() == false) {
				frag.set(true);
			}
			this.contact = contact;
		} else
			this.contact = contact;
	}

	@Override
	public void explosion() {
		if (getRayonExplosion() + 1 <= this.getRayonexplosionMax()) {
			setRayonExplosion(getRayonExplosion() + 1);
			if (getRayonExplosion() == this.getRayonexplosionMax()) {
				IniTerrain.destructionTerrain(this);
				setExploser(true);
			}
			if (getRayonExplosion() == 3) {
				SonsGestion.jouerSonsExplosion();

			}
		}

	}

}
