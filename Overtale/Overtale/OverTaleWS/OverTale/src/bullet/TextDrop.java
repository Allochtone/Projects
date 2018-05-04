package bullet;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TextDrop {
	private Text text;
	private final double MAX = 605;
	
	public TextDrop(double posX,double posY,final int direction,final double tempsAnimation){
		int random = ((int) (Math.random() * 3));
		switch(random){
		case 0:
			text = new Text(posX, posY, "Such speed");
			break;
		case 1:
			text = new Text(posX, posY, "Much fast");
			break;
		case 2:
			text = new Text(posX, posY, "Wow U bad");
			break;
		}
		text.setStyle("-fx-background-color: #ffffff;");
		text.setStroke(Color.SILVER);
		text.setFont(new Font(100));
		FadeTransition ft = new FadeTransition(Duration.millis(300), text);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RotateTransition rt = new RotateTransition(Duration.millis(tempsAnimation), text);
				rt.setFromAngle(0);
				rt.setToAngle(360);
				rt.play();
				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis(tempsAnimation));
				translateTransition.setNode(text);
				switch(direction){
				case 0:
					translateTransition.setByY(MAX + 50);
					break;
				case 1:
					translateTransition.setByY(-MAX - 50);
					break;
				case 2:
					translateTransition.setByX(MAX+50);
					break;
				case 3:
					translateTransition.setByX(-MAX-50);
					break;
					
				}
				translateTransition.setCycleCount(1);
				translateTransition.setAutoReverse(true);
				translateTransition.play();
				translateTransition.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						FadeTransition ft = new FadeTransition(Duration.millis(300), text);
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.play();
					}
				});
			}});
	}
	
	public Text bullet() {
		return text;
	}
}
