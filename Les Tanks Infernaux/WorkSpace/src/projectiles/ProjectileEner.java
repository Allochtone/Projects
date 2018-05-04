package projectiles;

import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.effect.MotionBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import musiquesEtSons.SonsGestion;
import tanks.Tank;
import terrain.IniTerrain;

public class ProjectileEner extends Projectiles {
	public ArrayList<Polyline> ligne;
	public ArrayList<SimpleBooleanProperty> exploser;

	public ProjectileEner(double hauteur, double longueur, Tank lanceur) {
		super(hauteur, longueur, lanceur);
		setLongueur(longueur);
		setHauteur(hauteur);
		ligne = new ArrayList<Polyline>();
		exploser = new ArrayList<SimpleBooleanProperty>();
		dommages = 0.005f;
		for (int x = 0; x < (int) ((IniTerrain.getXmax() - getPosX()) / 20); x++) {
			exploser.add(new SimpleBooleanProperty(false));
			ligne.add(new Polyline());
			ligne.get(x).visibleProperty().bind(exploser.get(x).not());
			setExploser(true, x);
			setRayonExplosion(15);
		}
		String nomArme = arme.getNom();
		double hauteurligne;
		for (int x = 0; x < ligne.size(); x++) {
			if (temps < IniTerrain.getXmax() - getPosX()) {
				for (int y = 0; y < 20; y++) {
					switch (nomArme) {
					case "sinus":
						hauteurligne = hauteur * Math.sin(temps * 2 * Math.PI / longueur) + getPosY();
						break;
					case "cosec":
						hauteurligne = hauteur * (1 / Math.sin(temps * 2 * Math.PI / longueur)) + getPosY();
						break;
					case "tan":
						hauteurligne = (hauteur / 20) * Math.tan(temps * 2 * Math.PI / longueur) + getPosY();
						break;
					case "absolue":
						if (hauteur > 0) {
							hauteurligne = Math.abs(temps * (hauteur * 2 / Math.abs(longueur)) - hauteur) + getPosY() - hauteur;
						} else {
							hauteurligne = -Math.abs(temps * (hauteur * 2 / Math.abs(longueur)) - hauteur) + getPosY() - hauteur;
						}
						break;
					case "rationnelle":
						hauteurligne = hauteur / (temps - Math.abs(longueur)) + getPosY();
						break;
					default:
						hauteurligne = 0;
					}

					if (hauteurligne < IniTerrain.getYmax() && hauteurligne >= 0) {
						if(longueur > 0){
						ligne.get(x).getPoints().addAll(new Double[] { getPosX() + temps, hauteurligne });
						}else{
							ligne.get(x).getPoints().addAll(new Double[] { getPosX() - temps, hauteurligne });
						}
					} else {
						if (hauteurligne >= IniTerrain.getYmax()) {
							if(longueur > 0)
							ligne.get(x).getPoints().addAll(new Double[] { getPosX() + temps, IniTerrain.getYmax() });
							else{
								ligne.get(x).getPoints().addAll(new Double[] { getPosX() - temps, IniTerrain.getYmax() });
							}
						} else {
							if(longueur > 0)
								ligne.get(x).getPoints().addAll(new Double[] { getPosX() + temps, (double) 0 });
								else{
									ligne.get(x).getPoints().addAll(new Double[] { getPosX() - temps, (double) 0 });
								}
						}
					}
					temps++;
				}
				temps--;
			}
		}

		temps = 0;
	}

	public SimpleBooleanProperty exploser(int position) {
		return exploser.get(position);
	}

	public boolean isExploser(int position) {
		return exploser.get(position).get();
	}

	public void setExploser(boolean exploser, int position) {
		this.exploser.get(position).set(exploser);
	}

	public ArrayList<Polyline> getLigne() {
		return ligne;
	}

	public void setLigne(ArrayList<Polyline> ligne) {
		this.ligne = ligne;
	}

	@Override
	public void setLongueur(double longueur) {
		if (longueur > 150 || longueur <= -150) {
			this.longueur = longueur;
		} else if (longueur >= 0) {
			this.longueur = 150;
		} else {
			this.longueur = -150;
		}
	}

	@Override
	public void setHauteur(double hauteur) {
		if (hauteur > 50 || hauteur <= -50) {
			this.hauteur = hauteur;
		} else if (hauteur >= 0) {
			this.hauteur = 50;
		} else {
			this.hauteur = -50;
		}
	}

	@Override
	public void mouvement() {
		if (temps == 0) {
			SonsGestion.jouerSonTirEnergieIA();
		} else if (temps == 35) {
			SonsGestion.arretSonTirEnergieIA();
		}
		if (temps < ligne.size()) {
			setExploser(false, temps);
		}
		if (temps >= 15 && temps - 15 < ligne.size()) {
			if (ligne.get(temps - 15).getOpacity() - 0.1f >= 0)
				ligne.get(temps - 15).setOpacity(ligne.get(temps - 15).getOpacity() - 0.1f);
			ligne.get(temps - 15).setEffect(null);
			ligne.get(temps - 15).setEffect(new MotionBlur(5, 5));
		}
		if (temps >= 30 && temps - 30 < ligne.size()) {
			setExploser(true, temps - 30);
		}
		temps++;
	}

	@Override
	public void explosion() {
	}

}
