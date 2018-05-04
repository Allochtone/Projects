package projectiles;

import java.util.ArrayList;

import controlleurs.PrincipalControlleur;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.shape.Shape;
import jeu.Deroulement;

import tanks.Tank;

public class ProjectilesFactory {

	public ProjectilesFactory() {
	}

	public void lancerProjectile(Tank lanceur, double amplitude, double hauteur, double longueur) {

	}

	public ArrayList<Shape> ajouterprojectiles(double hauteur, double longueur, Tank lanceur) {
		ArrayList<Projectiles> projectiles = new ArrayList<Projectiles>();
		ArrayList<Shape> proGraph = new ArrayList<Shape>();
		String typearme = lanceur.getArmeSelectionner().getTypenom();
		String nomarme = lanceur.getArmeSelectionner().getNom();
		if (typearme == "Ener") {
			projectiles.add(new ProjectileEner(hauteur, longueur, lanceur));
			for (int x = 0; x < projectiles.size(); x++) {
				for (int y = 0; y < ((ProjectileEner) projectiles.get(x)).getLigne().size(); y++) {
					switch (nomarme) {
					case "sinus":
						((ProjectileEner) projectiles.get(x)).getLigne().get(y).setStroke(Color.BLUE);
						setDommages(0.050f);
						break;
					case "cosec":
						((ProjectileEner) projectiles.get(x)).getLigne().get(y).setStroke(Color.RED);
						break;
					case "tan":
						((ProjectileEner) projectiles.get(x)).getLigne().get(y).setStroke(Color.BLUEVIOLET);
						break;
					case "absolue":
						((ProjectileEner) projectiles.get(x)).getLigne().get(y).setStroke(Color.CHARTREUSE);
						break;
					case "rationnelle":
						((ProjectileEner) projectiles.get(x)).getLigne().get(y).setStroke(Color.FUCHSIA);
						break;
					}
					((ProjectileEner) projectiles.get(x)).getLigne().get(y).setEffect(new Glow(5));
					((ProjectileEner) projectiles.get(x)).getLigne().get(y).setEffect(new Glow(5));
					proGraph.add(((ProjectileEner) projectiles.get(x)).getLigne().get(y));
				}
			}
		} else {
			projectiles.add(new ProjectilePhys(hauteur, longueur, lanceur));
			for (int x = 0; x < projectiles.size(); x++) {
				Circle balle = new Circle(((ProjectilePhys) projectiles.get(x)).getRayonExplosion());
				balle.translateXProperty().bind(projectiles.get(x).PosX().add(5));
				balle.translateYProperty().bind(projectiles.get(x).PosY());
				balle.radiusProperty().bind(((ProjectilePhys) projectiles.get(x)).rayonExplosion());
				balle.visibleProperty().bind(projectiles.get(x).exploser().not());
				projectiles.get(x).Vent().bind(PrincipalControlleur.getCompteurVent());
				balle.setFill(Color.RED);
				balle.setStroke(Color.BLACK);
				Circle vision = new Circle(15);
				vision.setOpacity(0.25f);
				vision.setFill(Color.YELLOW);
				vision.translateXProperty().bind(balle.translateXProperty());
				vision.translateYProperty().bind(balle.translateYProperty());
				vision.visibleProperty().bind(balle.visibleProperty());
				proGraph.add(balle);
				proGraph.add(vision);
			}
		}
		Deroulement.ajouterProjectiles(projectiles);
		return proGraph;
	}

	private void setDommages(float f) {
		// TODO Auto-generated method stub

	}

}
