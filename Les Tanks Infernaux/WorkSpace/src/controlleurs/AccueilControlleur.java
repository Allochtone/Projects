package controlleurs;

import java.io.File;
import java.io.IOException;

import application.Infernal;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import musiquesEtSons.MusiqueGestion;
import tanks.Difficultes;

public class AccueilControlleur {
	AccueilControlleur ac;
	private OptionControlleur oc = new OptionControlleur();
	private PrincipalControlleur pc = new PrincipalControlleur();
	public static MediaPlayer musiqueMenu = new MediaPlayer(
			new Media(new File("res/musiques/musique1Menu.mp3").toURI().toString()));
	@FXML
	private ImageView MenuImage;

	@FXML
	private Button QuitterButton;

	@FXML
	private Button JouerButton;

	@FXML
	private Button OptionButton;

	@FXML
	void afficherJeu(ActionEvent event) throws Exception {
		if (OptionControlleur.getAi().equals(Difficultes.AIMBOT)) {
			MusiqueGestion.nouvellMusiqueBoss();
		} else {
			MusiqueGestion.gestionMusique(MusiqueGestion.musiqueRandom()[1]);
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/LesTanksInfernals.fxml"));
		Pane root = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.setTitle("LES TANKS INFERNAL");
		pc = loader.getController();
		MusiqueGestion.setVolume(MusiqueGestion.getVolume());

		stage.show();
	}

	@FXML
	void options(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/Options.fxml"));
		BorderPane root = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.setTitle("LES TANKS INFERNAL");
		oc = loader.getController();
		oc.getSliderMusique().setValue(MusiqueGestion.getVolume());
		MusiqueGestion.gestionMusique(MusiqueGestion.getMusiqueOptions());
		MusiqueGestion.getMusiqueMenu().volumeProperty().bindBidirectional(oc.getSliderMusique().valueProperty());
		stage.show();

	}

	@FXML
	private void initialize() {

		MusiqueGestion.getMusiqueMenu().play();

	}

	@FXML
	void quitter(ActionEvent event) {
		Platform.exit();

	}

}
