package bullet;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Square {
	private Rectangle square;
	private final double MAX = 605;

	public Square(double posX, double posY, final double Hauteur, double Longueur, final int direction, final double tempsAnimation,final double angle) {
		square = new Rectangle(posX, posY, Hauteur, Longueur);
		square.setFill(Color.AQUAMARINE);
		FadeTransition ft = new FadeTransition(Duration.millis(300), square);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis(tempsAnimation));
				translateTransition.setNode(square);
				RotateTransition rt = new RotateTransition(Duration.millis(tempsAnimation), square);
				rt.setFromAngle(0);
				rt.setToAngle(angle);
				rt.play();
				switch(direction){
				case 0:
					translateTransition.setByX(MAX + 4*Hauteur);
					break;
				case 1:
					translateTransition.setByY(MAX + 4*Hauteur);
					break;
				case 2:
					translateTransition.setByY(-MAX- 4*Hauteur);
					break;
				case 3:
					translateTransition.setByX(-MAX- 4*Hauteur);
					break;
					
				}
				translateTransition.setCycleCount(1);
				translateTransition.setAutoReverse(true);
				translateTransition.play();
				translateTransition.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						FadeTransition ft = new FadeTransition(Duration.millis(300), square);
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.play();
					}
				});
			}
		});
	}

	public Rectangle bullet() {
		return square;
	}

}
