package musiquesEtSons;

import java.io.File;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SonsGestion {

	private static final Sons SON_DEPLACEMENT = Sons.DEPLACEMENT;
	private static final Sons EXPLOSION1 = Sons.EXPLOSION1CONTACTDIRECT;
	private static final Sons TIR_ENERGIE = Sons.TIR_ENERGIE;
	private static final Sons REBOND_SON = Sons.REBOND_SON;
	private static SimpleDoubleProperty volumeSons = new SimpleDoubleProperty(1);
	private static MediaPlayer sonExplosion;
	private static final MediaPlayer SON_DEPLACEMENT_JOUEUR = new MediaPlayer(
			new Media(new File(SON_DEPLACEMENT.getPath()).toURI().toString()));
	private static final MediaPlayer SON_DEPLACEMENT_IA = new MediaPlayer(
			new Media(new File(SON_DEPLACEMENT.getPath()).toURI().toString()));
	private static final MediaPlayer SON_TIR_ENERGIE_JOUEUR = new MediaPlayer(
			new Media(new File(TIR_ENERGIE.getPath()).toURI().toString()));
	private static final MediaPlayer SON_TIR_ENERGIE_IA = new MediaPlayer(
			new Media(new File(TIR_ENERGIE.getPath()).toURI().toString()));
	private static final MediaPlayer REBONDJOUEUR = new MediaPlayer(
			new Media(new File(REBOND_SON.getPath()).toURI().toString()));
	private static final MediaPlayer REBONDIA = new MediaPlayer(
			new Media(new File(REBOND_SON.getPath()).toURI().toString()));

	/**
	 * Méthode qui fait jouer le son de l'explosion des projectiles physiques
	 */
	public static void jouerSonsExplosion() {
		String path = EXPLOSION1.getPath();
		MediaPlayer mediaPlayer = sonExplosion;
		creerMediaPLayers(mediaPlayer, path);

	}

	/**
	 * Méthode qui fait jouer le son de déplacement de l'IA
	 */
	public static void jouerSonsDeplacementIA() {
		SON_DEPLACEMENT_IA.setVolume(volumeSons.get());
		SON_DEPLACEMENT_IA.play();
	}

	/**
	 * Méthode qui fait jouer le son de déplacement du joueur
	 */
	public static void jouerSonsDeplacementJoueur() {
		SON_DEPLACEMENT_JOUEUR.setVolume(volumeSons.get());
		SON_DEPLACEMENT_JOUEUR.play();
	}

	/**
	 * Méthode qui s'occupe de créer différents média players selon le chemin du
	 * média qu'on veut
	 * 
	 * @param mediaPlayer
	 *            : le média player à changer
	 * @param path
	 *            : le chemin du média
	 */
	private static void creerMediaPLayers(MediaPlayer mediaPlayer, String path) {
		mediaPlayer = new MediaPlayer(new Media(new File(path).toURI().toString()));
		mediaPlayer.setAutoPlay(false);
		mediaPlayer.setVolume(volumeSons.get());
		mediaPlayer.play();
	}

	public static void arretSonDeplacementIA() {
		SON_DEPLACEMENT_IA.stop();

	}

	public static void arretSonDeplacementJoueur() {
		SON_DEPLACEMENT_JOUEUR.stop();

	}

	public static void jouerSonTirEnergieIA() {
		SON_TIR_ENERGIE_IA.setVolume(volumeSons.get());
		SON_TIR_ENERGIE_IA.play();
	}

	public static void jouerSonTirEnergieJoueur() {
		SON_TIR_ENERGIE_JOUEUR.setVolume(volumeSons.get());
		SON_TIR_ENERGIE_JOUEUR.play();
	}

	public static void arretSonTirEnergieIA() {
		SON_TIR_ENERGIE_IA.stop();

	}

	public static void arretSonTirEnergieJoueur() {
		SON_DEPLACEMENT_JOUEUR.stop();

	}

	public static void jouerSonRebondIA() {
		REBONDIA.setVolume(volumeSons.get());
		REBONDIA.play();
	}

	public static void jouerSonRebondJoueur() {
		REBONDJOUEUR.setVolume(volumeSons.get());
		REBONDJOUEUR.play();
	}

	public static void arretSonRebondIA() {
		REBONDIA.stop();

	}

	public static void arretSonRebondJoueur() {
		REBONDJOUEUR.stop();

	}

	public static MediaPlayer getSonDeplacementIa() {
		return SON_DEPLACEMENT_IA;
	}

	public static double getvolumeSons() {
		return volumeSons.get();
	}

	public static SimpleDoubleProperty getVolumeProp() {
		return volumeSons;
	}

}
