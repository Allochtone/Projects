package bullet;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Rain {
	private Circle rain;
	private final double MAX = 605;
	
	public Rain(double centerX, double centerY, double rayon, final int direction,final double tempsAnimation){
		rain = new Circle(centerX, centerY, rayon);
		rain.setFill(Color.CRIMSON);
		FadeTransition ft = new FadeTransition(Duration.millis(300), rain);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setDelay(Duration.millis((tempsAnimation*(Math.random()*20))/30));
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis(tempsAnimation));
				translateTransition.setNode(rain);
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
				translateTransition.setInterpolator(Interpolator.EASE_IN);
				translateTransition.play();
				translateTransition.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						FadeTransition ft = new FadeTransition(Duration.millis(300), rain);
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.play();
					}
				});
			}});
	}
	
	public Circle bullet() {
		return rain;
	}
}
