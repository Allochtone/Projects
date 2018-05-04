package jeu;

import java.awt.MouseInfo;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.shape.Rectangle;
import musiquesEtSons.MusiqueGestion;
import musiquesEtSons.Musiques;
import musiquesEtSons.SonsGestion;
import projectiles.ProjectileEner;
import projectiles.Projectiles;

import tanks.*;
import terrain.IniTerrain;
import utilitaires.MathUtilitaire;

public class Deroulement extends Service<Void> {
	private static ArrayList<Projectiles> projectiles;
	private Tank[] tk;
	private boolean findujeu = false;
	private long tempsDepartMusique = 0;
	private MediaPlayer musique;
	private int duree;
	private static final double MOUVEMENT = 0.5;
	private Difficultes difficulte = Difficultes.FACILE;
	private boolean mouvementTank;
	private int compteur = 0;
	private Point2D tirIA = new Point2D(0, 0);
	private SimpleDoubleProperty recharge = new SimpleDoubleProperty(0.5f);

	public Deroulement(Difficultes diff) {
		this.difficulte = diff;
		projectiles = new ArrayList<Projectiles>();
		tk = new Tank[] { new Joueur(300, 100), new IA(800, 100, difficulte) };
	}

	/**
	 * Thread servant à faire la gravité des tanks et le changement du vent
	 */
	@Override
	protected Task<Void> createTask() {

		setMusique();
		compteur = 0;
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				while (!findujeu) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							deplacementProjectiles();
							deplacementVerticalTank();
							deplacementHorizontalTank(getTank()[0]);
							deplacementHorizontalTankIA();
							detruireProjectile();
							dommagesTanks();
							changerMusique();
							tempsrecharge();
						}
					});
					Thread.sleep(15);
				}
				return null;
			}

		};
	}

	public SimpleDoubleProperty recharge() {
		return recharge;
	}

	private void tempsrecharge() {
		if (recharge.get() < 1) {
			recharge.set(recharge.get() + 0.005f);
		}
	}

	public ArrayList<Projectiles> getProjectiles() {
		return projectiles;
	}

	private void setMusique() {
		tempsDepartMusique = System.currentTimeMillis();
		musique = MusiqueGestion.getMusiqueMenu();
		musique.setVolume(MusiqueGestion.getVolume());
		duree = (MusiqueGestion.getNomMusique(musique.getMedia())).getDuree();

	}

	private void deplacementHorizontalTank(Tank tank) {

		switch (tank.getDirection()) {

		case 'L':
			tank.setGaz(tank.getGaz() - 0.0025f);
			if (tank.equals(getTank()[1])) {
				// SonsGestion.jouerSonsDeplacementIA();
			}
			if (tank.getGaz() > 0.010f) {
				tank.setPosX(tank.getPosX() - MOUVEMENT);
			}
			break;
		case 'R':
			tank.setGaz(tank.getGaz() - 0.0025f);
			if (tank.equals(getTank()[1])) {
				// SonsGestion.jouerSonsDeplacementIA();
			}
			if (tank.getGaz() > 0.010f) {
				tank.setPosX(tank.getPosX() + MOUVEMENT);
			}
			break;
		case 'S':
			tank.setGaz(tank.getGaz() + 0.01f);
			tank.setPosX(tank.getPosX());
			if (tank.equals(getTank()[1]) && SonsGestion.getSonDeplacementIa().getStatus().equals(Status.PLAYING)) {
				// SonsGestion.arretSonDeplacementIA();
			}
		}

	}

	/**
	 * Méthode complexe qui donne les dommages pour tous les projectiles
	 *
	 * Pour plus d'info et pour se souvenir du code:
	 *
	 * Pour les 2 tanks Pour tous les projectiles Si c'est un projectile énergie
	 * Pour tous les sections de 20 points de la lignes S'ils sont proche des
	 * extrémités de cette section Donne des dommages Sinon (c'est un physique)
	 * Si les tanks sont dans la région d'explosion du projectile Donne des
	 * dommages
	 *
	 */
	private void dommagesTanks() {
		for (int x = 0; x < tk.length; x++) {
			for (int y = 0; y < projectiles.size(); y++) {
				Projectiles projectile = projectiles.get(y);
				Tank tank = tk[x];
				if (!projectiles.isEmpty()) {
					if (projectile.getClass() == ProjectileEner.class) {
						for (int z = 0; z < ((ProjectileEner) projectile).getLigne().size(); z++) {
							if (!((ProjectileEner) projectile).isExploser(z)) {
								if (MathUtilitaire.pythagoras(
										tank.getPosX() - ((ProjectileEner) projectile).getLigne().get(z).getPoints()
												.get(0).doubleValue(),
										tank.getPosY() - ((ProjectileEner) projectile).getLigne().get(z).getPoints()
												.get(1).doubleValue()) <= projectile.getRayonExplosion()) {
									if (projectile.getTemps() > 20) {
										if (z != 0) {
											tank.setPointVie(tank.getPointVie() - projectile.getDommages());
										}
									}
								}
								if (MathUtilitaire.pythagoras(
										tank.getPosX() - ((ProjectileEner) projectile).getLigne().get(z).getPoints()
												.get(38).doubleValue(),
										tank.getPosY() - ((ProjectileEner) projectile).getLigne().get(z).getPoints()
												.get(39).doubleValue()) <= projectile.getRayonExplosion()) {
									if (projectile.getTemps() > 20) {
										if (!((ProjectileEner) projectile).isExploser(z)) {
											tank.setPointVie(tank.getPointVie() - projectile.getDommages());
										}
									}
								}
							}
						}
					} else {
						if (!projectile.isExploser()) {
							if (MathUtilitaire.pythagoras(tank.getPosX() - projectile.getPosX(),
									tank.getPosY() - projectile.getPosY()) <= projectile.getRayonExplosion()) {
								if (projectile.getTemps() > 20) {
									tank.setPointVie(tank.getPointVie() - projectile.getDommages());
								}
							}
						}
					}
				}
			}
		}
	}

	private void deplacementHorizontalTankIA() {
		IA tank = (IA) getTank()[1];
		boolean skip = true;

		for (Projectiles projectiles2 : projectiles) {
			tank.setDirection(tank.modifierDirectionTank(projectiles2));
			skip = false;

		}
		if (mouvementTank && skip) {
			tank.setDirection(tank.mouvementIntelligent());
		}

		deplacementHorizontalTank(tank);
	}

	/**
	 * Méthode qui dans la création des projectiles, vien le mettre dans son
	 * arraylist pour avec les données mémoires.
	 *
	 * @param hauteur
	 * @param longueur
	 * @param lanceur
	 * @return Liste de projectile
	 */
	public static synchronized void ajouterProjectiles(ArrayList<Projectiles> listeAjouter) {

		projectiles.addAll(listeAjouter);
	}

	/**
	 * Méthode qui détecte la position du projectile dans le pane, Si le
	 * projectile touche au terrain, s'il sort du pane, alors le projectile
	 * explose.
	 */
	public synchronized void detruireProjectile() {
		ArrayList<Rectangle> terrain = IniTerrain.getTerrain();
		for (int x = 0; x < projectiles.size(); x++) {
			for (int y = 0; y < IniTerrain.getTerrain().size(); y++) {
				Projectiles projectile = projectiles.get(x);
				if ((int) projectile.getPosX() == (int) terrain.get(y).getTranslateX()) {
					if (projectile.getPosY() >= terrain.get(y).getTranslateY()) {

						projectile.setPosX(terrain.get(y).getTranslateX());
						projectile.setPosY(terrain.get(y).getTranslateY());
						explosionProjectile(x);

					}
				}
				if (projectile.getPosX() >= IniTerrain.XMAX || projectile.getPosX() <= 0) {
					projectile.setPosX(1300);
					projectile.setPosY(0);
					explosionProjectile(x);
				}
				if (projectile.getPosY() >= IniTerrain.YMAX) {
					projectile.setPosX(terrain.get(y).getTranslateX());
					projectile.setPosY(terrain.get(y).getTranslateY());
					explosionProjectile(x);
				}
			}
		}

	}

	/**
	 * Fait augmenter le rayon du projectile qui rentre en collision jusqu'à un
	 * maximum avant sa disparition
	 *
	 * @param positionProjectile
	 */
	private synchronized void explosionProjectile(int positionProjectile) {
		projectiles.get(positionProjectile).setContact(true);
		if (projectiles.get(positionProjectile).isContact()) {

			projectiles.get(positionProjectile).explosion();

		}
	}

	private synchronized void deplacementProjectiles() {
		for (int x = 0; x < projectiles.size(); x++) {
			if (!projectiles.get(x).isContact())
				projectiles.get(x).mouvement();
		}
	}

	/**
	 * Méthode qui change l'orientation du tank lorsqu'il est en déplacement et
	 * qui s'assure de l'angle maximal de montée.
	 *
	 * @param tank
	 *            : le tank qu'on veut changer l'orientation
	 * @param hauteurgauche
	 *            : la hauteur du rectangle le plus à gauche sous le tank
	 * @param hauteurdroite
	 *            : la hauteur du rectangle le plus à droite sous le tank
	 */
	private void changerOrientationTank(Tank tank, double hauteurgauche, double hauteurdroite) {
		double orientation = Math.toDegrees(Math.atan(15 / (hauteurgauche - hauteurdroite)));
		if (hauteurgauche - hauteurdroite >= 0) {
			if (orientation - 90 <= -50) {
				if (tank.getDirection() == 'R') {
					if (tk[0] == tank) {
						tank.setDirection('L');
						deplacementHorizontalTank(tank);
						tank.setDirection('R');
					}
				}
			}
			tank.setRotation(orientation - 90);
		} else {
			if (orientation + 90 >= 70) {
				if (tank.getDirection() == 'L') {
					if (tk[0] == tank) {
						tank.setDirection('R');
						deplacementHorizontalTank(tank);
						tank.setDirection('L');
					}
				}
			}
			tank.setRotation(orientation + 90);
		}
	}

	public Tank[] getTank() {
		return tk;
	}

	/**
	 * Donne la position du curseur dans l'écran sous la forme d'un Point (x, y)
	 * dont nous pouvons aller chercher les parties avec des gets
	 *
	 * @return
	 */

	public java.awt.Point cursorPosition() {

		return MouseInfo.getPointerInfo().getLocation();

	}

	/**
	 * Méthode qui s'occupe de trouver le nom de la musique et qui la fait
	 * jouer. Si l'IA n'est pas difficile, on met de la musique aléatoire, sinon
	 * on fait jouer la musique du "boss"
	 *
	 * @param media
	 *            : le Media qui fait jouer la musique
	 */
	private void gestionMusique(Media media) {
		if (!difficulte.equals(Difficultes.AIMBOT)) {
			Musiques nomMusique = MusiqueGestion.getNomMusique(media);

			MusiqueGestion.nouvelleMusique(nomMusique);
		} else {
			MusiqueGestion.nouvellMusiqueBoss();
		}

	}

	/**
	 * Méthode qui regarde si la durée de la musique est dépassée. Si oui, on
	 * chnage la musique
	 */
	private void changerMusique() {
		if (((System.currentTimeMillis() - tempsDepartMusique) / 1000) >= duree) {

			gestionMusique(musique.getMedia());
			tempsDepartMusique = System.currentTimeMillis();

			musique = MusiqueGestion.getMusiqueMenu();
			musique.setVolume(MusiqueGestion.getVolume());

			duree = (MusiqueGestion.getNomMusique(musique.getMedia())).getDuree();

		}
	}

	/**
	 * Méthode non terminée qui s'occupe d'orienter la direction du tank selon
	 * les pentes
	 */
	private void deplacementVerticalTank() {
		for (int x = 0; x < tk.length; x++) {
			// double largeurtank = 33;
			double hauteurtank = 20;
			boolean colisionGauche = false;
			boolean colisionDroit = false;
			Rectangle rectangleGauche = null;
			Rectangle rectangleDroit = null;
			double hauteurGauche = 650;
			double hauteurDroite = 650;
			for (int y = 0; y < IniTerrain.getTerrain().size(); y++) {
				Rectangle terrain = IniTerrain.getTerrain().get(y);
				if (terrain.getTranslateX() <= tk[x].getPosX() + 1 && terrain.getTranslateX() >= tk[x].getPosX() - 1) {
					if (terrain.getTranslateY() <= tk[x].getPosY() + hauteurtank) {
						colisionGauche = true;
						rectangleGauche = terrain;
						hauteurGauche = terrain.getTranslateY();
					} else {
						hauteurGauche = terrain.getTranslateY();
					}
				}
				if (terrain.getTranslateX() <= tk[x].getPosX() + 16
						&& terrain.getTranslateX() >= tk[x].getPosX() + 14) {
					if (terrain.getTranslateY() <= tk[x].getPosY() + hauteurtank) {
						colisionDroit = true;
						rectangleDroit = terrain;
						hauteurDroite = terrain.getTranslateY();
					} else {
						hauteurDroite = terrain.getTranslateY();
					}
				}
			}
			if (colisionDroit && !colisionGauche) {
				changerOrientationTank(tk[x], hauteurGauche, hauteurDroite);
				if (tk[x].getPosY() + hauteurtank >= rectangleDroit.getTranslateY() - 5) {
					tk[x].setPosY(rectangleDroit.getTranslateY() - hauteurtank);
				}
			} else if (!colisionDroit && colisionGauche) {
				changerOrientationTank(tk[x], hauteurGauche, hauteurDroite);
				if (tk[x].getPosY() + hauteurtank >= rectangleGauche.getTranslateY() - 5) {
					tk[x].setPosY(rectangleGauche.getTranslateY() - hauteurtank);
				}
			} else if (colisionDroit && colisionGauche) {
				if (tk[x].getPosY() + hauteurtank >= hauteurDroite) {
					if (hauteurGauche <= hauteurDroite) {
						tk[x].setPosY(hauteurGauche - hauteurtank);
					} else {
						tk[x].setPosY(hauteurDroite - hauteurtank);
					}
				}
			} else if (!colisionDroit && !colisionGauche) {
				if (tk[x].getPosY() + hauteurtank >= hauteurGauche - 5
						|| tk[x].getPosY() + hauteurtank >= hauteurDroite - 8) {
					if (hauteurGauche <= hauteurDroite) {
						tk[x].setPosY(hauteurGauche - hauteurtank);
					} else {
						tk[x].setPosY(hauteurDroite - hauteurtank);
					}
				} else {
					tk[x].setPosY(tk[x].getPosY() + 5);
				}
			}
		}

	}
}
