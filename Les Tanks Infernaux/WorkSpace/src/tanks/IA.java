package tanks;

import java.util.ArrayList;

import java.util.TreeMap;

import armes.Armes;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.util.Pair;

import projectiles.ProjectileEner;
import projectiles.Projectiles;
import utilitaires.MathUtilitaire;

public class IA extends Tank {
	private ArrayList<Armes> armesPossibles = new ArrayList<>();
	private Armes[] toutesArmes = this.getArmes();
	private Difficultes diff = Difficultes.FACILE;
	private float[] modif = diff.getModificateursTrajectoire();
	private char direction = this.getDirection();
	private BooleanProperty tirOuPas = new SimpleBooleanProperty(false);
	private Point2D retourPositionFictiveCurseur = new Point2D(0, 0);

	public IA(double posX, double posY, Difficultes diff) {
		super(posX, posY);
		setDifficulte(diff);
		setDirection('S');

	}

	private void setDifficulte(Difficultes diff2) {
		switch (diff2) {
		case FACILE:
			jouerFacile();
		case MOYEN:
			jouerMoyen();
		case AIMBOT:
			jouerAimBot();
		}
		this.diff = diff2;
		this.modif = diff.getModificateursTrajectoire();

	}

	private void jouerAimBot() {

	}

	private void jouerMoyen() {

	}

	/**
	 * On set les différents projectiles possibles pour ce niveau de difficulté
	 */
	private void jouerFacile() {
		for (Armes armes : toutesArmes) {
			if (armes.getTypenom().equalsIgnoreCase("phys")) {
				armesPossibles.add(armes);
			}
		}

	}

	public Armes tirer(int positionTankJoueur) {
		return armeSelectionner;

	}

	public char modifierDirectionTank(Projectiles projectile) {

		char retour = 'S';
		switch (this.diff) {
		case FACILE:
			retour = eviterProjPhys(projectile);
			break;
		case MOYEN:
			retour = bougerMoyen(projectile);
			break;
		case AIMBOT:
			retour = bougerAimbot(projectile);
			break;

		}
		if (retour == 'S') {

		}
		return retour;
	}

	private char bougerAimbot(Projectiles projectile) {

		return 'S';
	}

	private char bougerMoyen(Projectiles projectile) {

		return 'S';
	}

	/**
	 * Méthode qui s'occupe de faire bouger le tank en cas de tir ennemi
	 * 
	 * @param projectile
	 *            : le projectile en vol
	 * @return : la nouvelle direction d'esquive
	 */
	private char eviterProjPhys(Projectiles projectile) {
		char retour = 'S';
		// ArrayList<Integer> enerOuBouger = new ArrayList<>();
		int[] mouvement = { 1, -1, 0 };
		boolean explose = true;

		if (projectile != null) {
			explose = projectile.isExploser();
		}
		if (projectile != null && ((Armes) projectile.getArme()).getTypenom() == "Ener") {
			ProjectileEner p = (ProjectileEner) projectile;
			// TODO Trouver un moyen d'avoir une list des points et trouver si
			// le point du tank va être touché. Si oui, faire bouger * facteur.
			// Sinon, faire bouger * facteur.

		} else if (projectile != null && !explose && ((Armes) projectile.getArme()).getTypenom() == "Phys") {

			double posYProjectile = projectile.getPosY();
			double posXProjectile = projectile.getPosX();
			double posYTank = this.getPosY();
			double posXTank = this.getPosX();
			double posAbsolue = MathUtilitaire.pythagoras((posXTank - posXProjectile), (posYProjectile - posYTank));
			if (posXProjectile > posXTank) {
				posAbsolue = (-1) * posAbsolue;
			}
			if (posAbsolue <= 250 && posAbsolue >= 0) {

				retour = ('R');
			} else if (posAbsolue < 0 && posAbsolue >= -250) {

				retour = ('L');
			} else {
				switch (MathUtilitaire.randomValeurs(mouvement)) {
				case -1:
					retour = ('L');
					break;
				case 1:
					retour = ('R');
					break;
				case 0:
					retour = ('S');
					break;

				}

			}

		}

		return retour;
	}

	/**
	 * Méthode qui fait changer la direction du tank IA sans qu'on ait tiré
	 * 
	 * @return : retourne la nouvelle direction de déplacement
	 */
	public char mouvementIntelligent() {

		return modifierDirectionTank(null);
	}

	public void tir(Tank tankEnnemi) {
		switch (diff) {
		case FACILE:
			tirFacile(tankEnnemi);
			break;
		case MOYEN:
			tirMoyen(tankEnnemi);
			break;
		case AIMBOT:
			tirAIMBOT();
			break;
		}
	}

	/**
	 * Méthode qui s'occupe du tir facile. On trouve un couple X et Y qui, quand
	 * on l'entre dans le calcul de tir, donne un tir qui tombe près du joueur.
	 * On le multiplie par un facteur pour ne pas avoir des tirs parfait à tout
	 * coup.
	 * 
	 * @param tankEnnemi
	 *            : le tank ennemi, pour pouvoir avoir sa position.
	 */
	private void tirFacile(Tank tankEnnemi) {
		int modifXOUY = MathUtilitaire.randomEntre(-1, 1);
		int posModif = MathUtilitaire.randomEntre(modif.length, 0);
		float modi = modif[posModif];
		double positionXImpact = tankEnnemi.getPosX();
		double positionYImpact = tankEnnemi.getPosY();

		double positionXDepart = this.getPosX();
		double positionYDepart = this.getPosY();

		double retourHauteur = (Math.abs(positionYDepart - positionYImpact)) / 90;
		double retourLongueur = (-1 * (positionXDepart - positionXImpact)) / 1.16;

		switch (modifXOUY) {
		case 1:
			retourHauteur = retourHauteur * modi;
			break;
		case -1:
			retourLongueur = retourLongueur * modi;
			break;
		default:
			retourHauteur = retourHauteur * modi;
			retourLongueur = retourLongueur * modi;
			break;
		}

		retourPositionFictiveCurseur = new Point2D(retourLongueur, retourHauteur);
		modif[posModif] = 1;

	}

	private void tirMoyen(Tank t) {
		int modifXOUY = MathUtilitaire.randomEntre(-1, 1);
		int posModif = MathUtilitaire.randomEntre(modif.length, 0);
		float modi = modif[posModif];
		double positionXImpact = t.getPosX();
		double positionYImpact = t.getPosY();

		double positionXDepart = this.getPosX();
		double positionYDepart = this.getPosY();

		double retourHauteur = (Math.abs(positionYDepart - positionYImpact)) / 90;
		double retourLongueur = (-1 * (positionXDepart - positionXImpact)) / 1.16;

		switch (modifXOUY) {
		case 1:
			retourHauteur = retourHauteur * modi;
			break;
		case -1:
			retourLongueur = retourLongueur * modi;
			break;

		}
		retourPositionFictiveCurseur = new Point2D(retourLongueur, retourHauteur);
		modif[posModif] = 1;

	}

	private void tirAIMBOT() {
		// TODO Auto-generated method stub

	}

	public BooleanProperty getTirOuPas() {
		return tirOuPas;
	}

	public void setTirOuPas(boolean bool) {
		this.tirOuPas.set(bool);
	}

	public Point2D getRetourPositionFictiveCurseur() {
		return retourPositionFictiveCurseur;
	}

}
