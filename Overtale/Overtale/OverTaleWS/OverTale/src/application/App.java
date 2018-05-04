package application;

import controllers.GameController;
import controllers.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
	public static final String NOMAPP = "OverTale";
	private MenuController menuController;
	private Stage stage;
	private Scene scene;
	private BorderPane root;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/MenuVue.fxml"));
		stage = arg0;
		root = loader.load();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(NOMAPP);
		menuController = loader.getController();
		stage.setFullScreenExitHint("");
		stage.getIcons().add(new Image("/images/Annoying_Dog.png"));
		stage.show();

	}

	public void stop() {
		System.exit(0);
	}

}
