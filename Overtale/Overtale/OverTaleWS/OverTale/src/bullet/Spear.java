package bullet;

import game.Joueur;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Spear {
	private Line spear;

	public Spear(double point1X, double point1Y, double point2X, double point2Y, final int i, final double rayon,
			final double tempsAnimation, Joueur joueur,final int difficulte) {
		spear = new Line(point1X, point1Y, point2X, point2Y);
		spear.setStroke(Color.LIME);
		spear.setStrokeWidth(5);
		FadeTransition ft = new FadeTransition(Duration.millis(300), spear);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setDelay(Duration.millis((tempsAnimation / 16) * (i + 1)));
		ft.play();
		RotateTransition rt = new RotateTransition(Duration.millis(300), spear);
		rt.setFromAngle(0);
		rt.setToAngle(270);
		rt.setDelay(Duration.millis(200 * i));
		rt.play();
		rt.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis((tempsAnimation / 2*difficulte)-100*difficulte ));
				translateTransition.setNode(spear);
				translateTransition.setCycleCount(1);
				double pointX = -rayon * Math.cos(22.5*i);
				double pointY = -rayon * Math.sin(22.5*i);
				translateTransition.setByX((difficulte+1)*pointX);
				translateTransition.setByY((difficulte+1)*pointY);
				translateTransition.setInterpolator(Interpolator.EASE_OUT);
				translateTransition.play();
				translateTransition.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						FadeTransition ft = new FadeTransition(Duration.millis(300), spear);
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.play();
						ft.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						spear.setTranslateX(-1000);
						spear.setTranslateY(-1000);
					}});
					}
				});
			}
		});
	}

	public Line bullet() {
		return spear;
	}

}
