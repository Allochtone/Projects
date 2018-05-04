package controlleurs;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import armes.Armes;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.scene.layout.Pane;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import jeu.Deroulement;
import musiquesEtSons.MusiqueGestion;
import musiquesEtSons.SonsGestion;
import projectiles.ProjectilePhys;
import projectiles.Projectiles;
import projectiles.ProjectilesFactory;
import tanks.Difficultes;
import tanks.IA;
import tanks.Joueur;
import tanks.Tank;
import terrain.IniTerrain;
import utilitaires.MathUtilitaire;

public class PrincipalControlleur {
	private Difficultes difficulte = OptionControlleur.getAi();
	private AccueilControlleur ac;
	private IniTerrain iniTerrain = new IniTerrain(OptionControlleur.diff, OptionControlleur.type);
	private Deroulement deroulement = new Deroulement(difficulte);
	private ProjectilesFactory factory = new ProjectilesFactory();
	private int compteurChangement = 0;
	private Line cannon;
	private static SimpleIntegerProperty compteurVent = new SimpleIntegerProperty(0);
	private int[] changementVent = new int[]{-1*OptionControlleur.vents,1*OptionControlleur.vents,0};
	public static int ventMax = 100;
	public static int ventMin = -100;
	private Timer timerVent = new Timer(false);
	private Timer tirIA = new Timer(false);

	@FXML
	private ProgressIndicator indicateurDeRecharge;

	@FXML
	private ImageView ImageArme;

	@FXML
	private Button recommencer;

	@FXML
	private Button retourMenu;

	@FXML
	private ComboBox<Button> menuRapide;

	@FXML
	private Label LabelArme;

	@FXML
	private Label NomPlayer1;

	@FXML
	private ProgressBar BarreDeViePlayer1;

	@FXML
	private ProgressBar BarreDeGasPlayer1;

	@FXML
	private Label NomPlayer2;

	@FXML
	private ProgressBar BarreDeViePlayer2;

	@FXML
	private ProgressBar BarreDeGasPlayer2;

	@FXML
	private ImageView ImageVent;

	@FXML
	private Label LabelVent;

	@FXML
	private ImageView ImageConditionTerrain;

	@FXML
	private Label LabelConditionTerrain;

	@FXML
	private ImageView ImageSwag;

	@FXML
	private RadioButton typeTerrainAleatoire;

	@FXML
	private Pane MainPannel;
	private PrincipalControlleur pc;
	private OptionControlleur oc;
	public static boolean fin;

	@FXML
	private void initialize() {
		iniTerrain.creerTerrain();
		MainPannel.getChildren().addAll(IniTerrain.getTerrain());
		intemperie();
		ajouterTank();
		deroulement.start();
		resetBarresDeVieGaz();
		vents();
		ImageArme.setImage(new Image((deroulement.getTank()[0].getArmeSelectionner().getPath())));
		ajouterProjectileIA();
		indicateurDeRecharge.progressProperty().bind(deroulement.recharge());
		tirIA();
	}

	@FXML
	void tirer(MouseEvent event) {
		if (indicateurDeRecharge.getProgress() >= 0.99f) {
			ArrayList<Shape> projectileGraphique;
			projectileGraphique = factory.ajouterprojectiles(deroulement.getTank()[0].getPosY() - event.getSceneY(),
					event.getSceneX() - deroulement.getTank()[0].getPosX(), deroulement.getTank()[0]);
			MainPannel.getChildren().addAll(projectileGraphique);
			if (deroulement.getProjectiles().get(deroulement.getProjectiles().size() - 1).getArme().equals(Armes.FRAGAIR)||
					deroulement.getProjectiles().get(deroulement.getProjectiles().size() - 1).getArme().equals(Armes.FRAGSOL)) {
				projectileFrag(deroulement.getProjectiles().get(deroulement.getProjectiles().size() - 1));
			}
			deroulement.recharge().set(0);
		}
	}

	@FXML
	void mouvementCannon(MouseEvent event) {
		double posCannonXfinal = 0;
		double posCannonYFinal = 0;
		Tank tk = deroulement.getTank()[0];
		double phy = MathUtilitaire.pythagoras(event.getSceneX() - tk.getPosX(), event.getSceneY() - tk.getPosY());
		posCannonXfinal = (event.getSceneX() - tk.getPosX()) * 30 / phy;
		posCannonYFinal = (event.getSceneY() - tk.getPosY()) * 30 / phy;

		tk.setCannonX(posCannonXfinal);
		tk.setCannonY(posCannonYFinal);

	}

	@FXML
	void recommencer(ActionEvent event) throws Exception {
		timerVent.cancel();
		changerVue("LesTanksInfernals", event);

		timerVent = new Timer(false);
		tirIA.cancel();
		tirIA = new Timer(false);
		deroulement.cancel();
		MainPannel.getChildren().clear();
		deroulement.reset();
		compteurVent.set(0);
	}

	@FXML
	void retourMenu(ActionEvent event) throws Exception {
		tirIA.cancel();
		timerVent.cancel();
		changerVue("MenuPrincipale", event);
		deroulement.cancel();
		MainPannel.getChildren().clear();
	}

	/**
	 * La fonction qui s'occupe de placer un tank sur l'écran et le cannon
	 */
	private void ajouterTank() {
		ImageView tank;
		for (int x = 0; x < deroulement.getTank().length; x++) {
			Tank tk = deroulement.getTank()[x];
			cannon = new Line(0.0, 0.0, 30.0, 0.0);
			tank = new ImageView(new Image("TANK.png"));
			cannon.translateXProperty().bind(tk.PosX().add(19.0));
			cannon.translateYProperty().bind(tk.PosY().add(2.0));
			cannon.endXProperty().bind(tk.cannonX());
			cannon.endYProperty().bind(tk.cannonY());
			tank.rotateProperty().bind(tk.rotation());
			tank.translateXProperty().bind(tk.PosX());
			tank.translateYProperty().bind(tk.PosY());
			MainPannel.getChildren().add(cannon);
			MainPannel.getChildren().add(tank);
		}
	}

	/**
	 * S'occupe d'initialiser les barres de vie pour le player et l'AI
	 */

	private void resetBarresDeVieGaz() {
		BarreDeViePlayer1.progressProperty().bind(deroulement.getTank()[0].pointVie());
		deroulement.getTank()[0].setPointVie(1);
		BarreDeGasPlayer1.progressProperty().bind(deroulement.getTank()[0].gaz());
		deroulement.getTank()[0].setGaz(1);
		BarreDeViePlayer2.progressProperty().bind(deroulement.getTank()[1].pointVie());
		deroulement.getTank()[1].setPointVie(1);
		BarreDeGasPlayer2.progressProperty().bind(deroulement.getTank()[1].gaz());
		deroulement.getTank()[1].setGaz(1);
	}

	/**
	 * S'occupe du changment d'arme et scrollant la souris
	 *
	 * @param event
	 */
	@FXML
	void changementArme(ScrollEvent event) {
		if ((compteurChangement + 1) < deroulement.getTank()[0].getArmes().length) {
			compteurChangement++;
			deroulement.getTank()[0].setArmeSelectionner(compteurChangement);
		} else {
			compteurChangement = 0;
			deroulement.getTank()[0].setArmeSelectionner(compteurChangement);
		}
		ImageArme.setImage(new Image((deroulement.getTank()[0].getArmeSelectionner().getPath())));

	}

	public void setCannonX(double position) {
		this.cannon.setEndX(position);
	}

	public void setCannonY(double position) {
		this.cannon.setEndY(position);
	}

	public void deplacementCannon() {

		deroulement.getTank()[0].setCannonX(deroulement.cursorPosition().getX() - deroulement.getTank()[0].getPosX());
		deroulement.getTank()[0].setCannonY(deroulement.cursorPosition().getY() - deroulement.getTank()[0].getPosY());

	}

	@FXML
	void arretTank(KeyEvent event) {
		deroulement.getTank()[0].setDirection('S');
		SonsGestion.arretSonDeplacementJoueur();
	}

	@FXML
	void mouvementTank(KeyEvent event) {
		if (event.getCode().equals(KeyCode.LEFT)) {
			deroulement.getTank()[0].setDirection('L');
			SonsGestion.jouerSonsDeplacementJoueur();
		} else if (event.getCode().equals(KeyCode.RIGHT)) {
			deroulement.getTank()[0].setDirection('R');
			SonsGestion.jouerSonsDeplacementJoueur();
		}

	}

	/**
	 * S'occupe de changer le vent
	 */

	public void vents() {

		timerVent.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						changeVent();
					}
				});
			}
		}, 0, 1000/OptionControlleur.vents);

	}

	/**
	 * S'occupe de changer le vent en lui ajoutant une valeur aléatoire entre -1
	 * et 1
	 */

	private void changeVent() {
		boolean isUp = false;
		boolean isDown = false;

		if (compteurVent.get() >= 0) {
			isUp = true;
		}
		if (compteurVent.get() <= 0) {
			isDown = true;
		}

		Random rand = new Random();
		int n = rand.nextInt(3);
		int tempChange = changementVent[n];
		compteurVent.set(compteurVent.get() + tempChange);
		if ((compteurVent.get() <= ventMax) && (compteurVent.get() >= ventMin)) {
			LabelVent.setText(String.valueOf(compteurVent.get()));
		} else {
			compteurVent.set(compteurVent.get() - tempChange);
		}

		if (isUp && (compteurVent.get() <= 0)) {
			ImageVent.setRotate(180);
		}
		if (isDown && (compteurVent.get() >= 0)) {
			ImageVent.setRotate(0);
		}

	}

	public static SimpleIntegerProperty getCompteurVent() {
		return compteurVent;
	}

	private void intemperie() {
		String condition = IniTerrain.getConditionTerrain();
		switch (condition) {
		case "gazon":
			MainPannel.setStyle("-fx-background-color: #CCFFFF;");
			ImageConditionTerrain.setImage(new Image("/ImageTerrain/Gazon.png"));
			break;
		case "boue":
			MainPannel.setStyle("-fx-background-color: #FFCC99;");
			ImageConditionTerrain.setImage(new Image("/ImageTerrain/Boue.jpg"));
			break;
		case "asphalt":
			MainPannel.setStyle("-fx-background-color: #CCFFFF;");
			ImageConditionTerrain.setImage(new Image("/ImageTerrain/Asphalt.jpg"));
			break;
		}
	}

	/**
	 * Méthode qui permet de changer de vue selon les entrées en paramètres
	 *
	 * @param vueVoulue:
	 *            Le nom du fichier FXML qui contient la vue voulue
	 * @param event:
	 *            l'évènement qui est activé
	 * @throws Exception:
	 *             lance une exception si besoin
	 */
	private void changerVue(String vueVoulue, Event event) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/" + vueVoulue + ".fxml"));
		Pane root = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.setTitle("LES TANKS INFERNAL");
		MusiqueGestion.setVolume(MusiqueGestion.getVolume());
		if (vueVoulue.equals("LesTanksInfernals")) {
			pc = loader.getController();
			if (difficulte.equals(Difficultes.AIMBOT)) {
				MusiqueGestion.nouvellMusiqueBoss();
			} else {
				MusiqueGestion.gestionMusique(MusiqueGestion.musiqueRandom()[1]);
			}
		} else if (vueVoulue.equals("MenuPrincipale")) {
			ac = loader.getController();
			MusiqueGestion.gestionMusique(MusiqueGestion.getMusiqueMenuPrincipal());
		} else {
			oc = loader.getController();
			MusiqueGestion.gestionMusique(MusiqueGestion.getMusiqueOptions());
		}
		stage.show();
	}

	/**
	 * Méthode qui permet à l'IA de tirer. On ajoute une Listener sur un atribut
	 * de l'IA. Lorsqu'il change, on tire.
	 */
	private void ajouterProjectileIA() {
		final IA tank = (IA) deroulement.getTank()[1];
		tank.getTirOuPas().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				tank.tir(deroulement.getTank()[0]);
				if (oldValue == false && newValue == true) {
					ArrayList<Shape> projectileGraphique;
					projectileGraphique = factory.ajouterprojectiles(
							tank.getPosY() - tank.getRetourPositionFictiveCurseur().getY(),
							tank.getRetourPositionFictiveCurseur().getX(), tank);
					MainPannel.getChildren().addAll(projectileGraphique);
				}

			}
		});
	}

	/**
	 * Méthode qui permet de changer l'attribut du tir de l'IA. À chaque X
	 * secondes, il tire.
	 */
	private void tirIA() {

		tirIA.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						((IA) deroulement.getTank()[1]).setTirOuPas(true);
						((IA) deroulement.getTank()[1]).setTirOuPas(false);
					}
				});
			}
		}, 7000, 5000);

	}

	public void projectileFrag(final Projectiles projectile) {
		final double longueur = ((ProjectilePhys) projectile).getLongueur();
		final double Hauteur = ((ProjectilePhys) projectile).getHauteur();
		((ProjectilePhys) projectile).frag().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false && newValue == true) {
					ArrayList<Shape> projectileGraphique;
					if(deroulement.getProjectiles().get(deroulement.getProjectiles().size() - 1).getArme().equals(Armes.FRAGAIR)){
					projectileGraphique = factory.ajouterprojectiles(Hauteur/10,longueur/1.25, new Joueur(((ProjectilePhys) projectile).getPosX(),((ProjectilePhys) projectile).getPosY()));
					//projectileGraphique.addAll(factory.ajouterprojectiles(Hauteur/5,longueur, new Joueur(((ProjectilePhys) projectile).getPosX(),((ProjectilePhys) projectile).getPosY())));
					}else{
						projectileGraphique = factory.ajouterprojectiles(Hauteur,longueur/5, new Joueur(((ProjectilePhys) projectile).getPosX(),((ProjectilePhys) projectile).getPosY()-25));
						projectileGraphique.addAll(factory.ajouterprojectiles(Hauteur,longueur/-5, new Joueur(((ProjectilePhys) projectile).getPosX(),((ProjectilePhys) projectile).getPosY()-25)));
					}
					MainPannel.getChildren().addAll(projectileGraphique);
				}
			}
		});
	}
	public static void setVents(int vent) {
		ventMax = vent;
		ventMin = -vent;
	}

}
