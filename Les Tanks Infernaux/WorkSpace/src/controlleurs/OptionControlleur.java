package controlleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import musiquesEtSons.MusiqueGestion;
import musiquesEtSons.SonsGestion;
import tanks.Difficultes;

public class OptionControlleur {

	public static int type = 1;
	public static int diff = 2;
	public static int vents = 2;
	public static Difficultes ai = Difficultes.FACILE;

	AccueilControlleur ac;
	@FXML
	private Slider volumeMusique;

	@FXML
	private Slider volumeSons;

	@FXML
	private Button Retour;

	@FXML
	private RadioButton VentFaible;

	@FXML
	private RadioButton VentNormal;

	@FXML
	private RadioButton VentIntense;

	@FXML
	private RadioButton VentTempete;

	@FXML
	private RadioButton RadioFacile;

	@FXML
	private RadioButton RadioMoyen;

	@FXML
	private RadioButton RadioDifficile;

	@FXML
	private RadioButton RadioAleatoire;

	@FXML
	private RadioButton FacileAI;

	@FXML
	private RadioButton MoyenAI;

	@FXML
	private RadioButton AimbotAI;

	@FXML
	private RadioButton typeTerrainGazon;

	@FXML
	private RadioButton typeTerrainBoue;

	@FXML
	private RadioButton typeTerrainAsphalt;

	@FXML
	private RadioButton typeTerrainAleatoire;

	@FXML
	public void retourMenu(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/MenuPrincipale.fxml"));
		BorderPane root = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.setTitle("LES TANKS INFERNAL");
		ac = loader.getController();
		getSliderMusique().setValue(MusiqueGestion.getMusiqueMenu().getVolume());
		MusiqueGestion.setVolume(getSliderMusique().getValue());
		MusiqueGestion.gestionMusique(MusiqueGestion.getMusiqueMenuPrincipal());
		MusiqueGestion.getMusiqueMenu().volumeProperty().bindBidirectional(getSliderMusique().valueProperty());
		stage.show();

	}

	public void setVent() {
		if (vents == 1) {
			VentFaible.setSelected(true);
		} else if (vents == 2) {
			VentNormal.setSelected(true);
		} else if (vents == 4) {
			VentIntense.setSelected(true);
		} else if (vents == 8) {
			VentTempete.setSelected(true);
		}
	}

	@FXML
	public void handleButtonAction(ActionEvent event) {
		if (VentFaible.isSelected()) {
			PrincipalControlleur.setVents(50);
			vents = 1;
		} else if (VentNormal.isSelected()) {
			PrincipalControlleur.setVents(100);
			vents = 2;
		} else if (VentIntense.isSelected()) {
			PrincipalControlleur.setVents(150);
			vents = 4;
		} else if (VentTempete.isSelected()) {
			PrincipalControlleur.setVents(200);
			vents = 8;
		}
	}

	@FXML
	public void handleRadioAction(ActionEvent event) {
		if (RadioAleatoire.isSelected()) {
			diff = 1;
		} else if (RadioFacile.isSelected()) {
			diff = 2;
		} else if (RadioMoyen.isSelected()) {
			diff = 3;
		} else if (RadioDifficile.isSelected()) {
			diff = 4;
		}
	}

	@FXML
	void handleTypeterrain(ActionEvent event) {
		if (typeTerrainAleatoire.isSelected()) {
			type = 1;
		} else if (typeTerrainGazon.isSelected()) {
			type = 2;
		} else if (typeTerrainBoue.isSelected()) {
			type = 3;
		} else if (typeTerrainAsphalt.isSelected()) {
			type = 4;
		}
	}

	public void setRadio() {
		if (diff == 1) {
			RadioAleatoire.setSelected(true);
		} else if (diff == 2) {
			RadioFacile.setSelected(true);
		} else if (diff == 3) {
			RadioMoyen.setSelected(true);
		} else if (diff == 4) {
			RadioDifficile.setSelected(true);
		}
	}

	public void setAI() {
		if (ai.equals(Difficultes.FACILE)) {
			FacileAI.setSelected(true);
		} else if (ai.equals(Difficultes.MOYEN)) {
			MoyenAI.setSelected(true);
		} else if (ai.equals(Difficultes.AIMBOT)) {
			AimbotAI.setSelected(true);
		}
	}

	@FXML
	public void handleAIAction(ActionEvent event) {
		if (FacileAI.isSelected()) {
			ai = Difficultes.FACILE;

		} else if (MoyenAI.isSelected()) {
			ai = Difficultes.MOYEN;

		} else if (AimbotAI.isSelected()) {
			ai = Difficultes.AIMBOT;

		}
	}

	@FXML
	public void initialize() {
		setVent();
		setRadio();
		setAI();
		volumeSons.valueProperty().bindBidirectional(SonsGestion.getVolumeProp());
	}

	public Slider getSliderMusique() {
		return volumeMusique;
	}

	public Slider getSliderSons() {
		return volumeSons;
	}

	public static Difficultes getAi() {
		return ai;
	}
}
