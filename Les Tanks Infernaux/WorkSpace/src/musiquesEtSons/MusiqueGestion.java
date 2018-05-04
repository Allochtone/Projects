package musiquesEtSons;

import java.io.File;

import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Classe qui s'occupe de la gestion de la musique
 * 
 * @author 1535974
 *
 */
public class MusiqueGestion {
	private static final String MUSIQUE_MENU = "res/musiques/musique1Menu.mp3";
	private static final String MUSIQUE_OPTIONS = "res/musiques/musique2Options.mp3";
	private static final String MUSIQUE_BOSS = "res/musiques/ghostDivision.m4a";
	private static MediaPlayer musiqueMenu = new MediaPlayer(new Media(new File(MUSIQUE_MENU).toURI().toString()));
	private static double volume = 1;
	private static Musiques[] musiques = Musiques.values();

	public MusiqueGestion() {

	}

	/**
	 * MÃ©thode qui s'occupe de changer la musique
	 * 
	 * @param musique
	 *            : le path de la nouvelle musique
	 */
	public static void gestionMusique(String musique) {

		musiqueMenu.stop();
		musiqueMenu = new MediaPlayer(new Media(new File(musique).toURI().toString()));
		musiqueMenu.setVolume(volume);
		musiqueMenu.play();
		musiqueMenu.setAutoPlay(false);

	}

	public static String getMusiqueOptions() {
		return MUSIQUE_OPTIONS;
	}

	public static String getMusiqueMenuPrincipal() {
		return MUSIQUE_MENU;
	}

	public static MediaPlayer getMusiqueMenu() {
		return musiqueMenu;
	}

	public static double getVolume() {
		return volume;
	}

	public static void setVolume(double volume) {
		MusiqueGestion.volume = volume;
	}

	/**
	 * Méthode qui s'occupe de choisir un path aléaatoire selon l'enum de
	 * musique. On ne fait pas jouer la musique du niveau difficile, la musique
	 * du "boss" final
	 * 
	 * @return : retourne le path
	 */
	public static String[] musiqueRandom() {
		String retourPath = "";
		String retourNom = "";
		do {
			Random rand = new Random();

			int random = rand.nextInt(musiques.length);

			retourPath = musiques[random].getPath();
			retourNom = musiques[random].getNom();
		} while (retourPath.equals(MUSIQUE_BOSS));

		return new String[] { retourNom, retourPath };

	}

	/**
	 * Méthode qui s'occupe de trouver la musique voulue selon le path
	 * 
	 * @param media
	 *            : le média qui contient la musique dont on veut avoir le nom
	 * @return : retourne la Musique, dans l'enum
	 */
	public static Musiques getNomMusique(Media media) {

		Musiques retour = null;
		for (Musiques musiques2 : musiques) {
			if ((musiques2 != null) && (media.getSource().endsWith(musiques2.getPath()))) {
				retour = musiques2;
			}
		}

		return retour;
	}

	/**
	 * Méthode qui s'occupe de faire jouer une nouvelle musique, différente de
	 * celle jouée précédemment
	 * 
	 * @param musique
	 *            : la musique qui vient de jouer
	 */
	public static void nouvelleMusique(Musiques musique) {
		Musiques retour = null;
		int random = 0;
		do {
			Random rand = new Random();
			random = rand.nextInt(musiques.length);
			retour = musiques[random];
		} while (musique.getNom().equals(musique) && retour.equals(Musiques.GHOST_DIVISION));
		gestionMusique(retour.getPath());

	}

	/**
	 * Méthode qui refait jouer la musique du "boss" final
	 */
	public static void nouvellMusiqueBoss() {
		Musiques retour = Musiques.GHOST_DIVISION;
		gestionMusique(retour.getPath());

	}

	public static MediaPlayer musiqueDifficile() {
		return null;
	}

	public static String getMusiqueDifficile() {

		return MUSIQUE_BOSS;
	}

}
