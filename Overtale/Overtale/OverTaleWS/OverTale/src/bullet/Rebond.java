package bullet;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Rebond {
	private Circle balle;
	private final double MAX = 605;
	private int ordre;
	private int viser = 0;
	private double tempsAnimation;
	private double directionX;
	private double directionY;

	public Rebond(final double centerX, final double centerY, double rayon, final int ordre,
			final double tempsAnimation) {
		balle = new Circle(centerX, centerY, rayon);
		this.ordre = ordre;
		this.tempsAnimation = tempsAnimation;
		double angle = ordre * 360 / 12;
		directionX = -6 * Math.cos(angle);
		directionY = -6 * Math.sin(angle);
		balle.setFill(Color.SADDLEBROWN);
		balle.setStroke(Color.BURLYWOOD);
		FadeTransition ft = new FadeTransition(Duration.millis(300), balle);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				transitionLoop(new TranslateTransition());
			}
		});
	}

	private void transitionLoop(TranslateTransition translateTransition) {
		translateTransition.setDuration(Duration.millis(15));
		translateTransition.setNode(balle);
		translateTransition.setCycleCount(1);
		if (balle.getCenterX()+balle.getTranslateX()< 0) {
			directionX = -directionX;
			balle.setTranslateX(-balle.getCenterX());
		}
		if(balle.getCenterX()+balle.getTranslateX() >605){
			directionX = -directionX;
			balle.setTranslateX(605-balle.getCenterX());
		}
		if (balle.getCenterY()+balle.getTranslateY() < 0) {
			directionY = -directionY;
			balle.setTranslateY(-balle.getCenterY());
		}
		if(balle.getCenterY()+balle.getTranslateY() >605){
			directionY = -directionY;
			balle.setTranslateY(605-balle.getCenterY());
		}

		translateTransition.setByX(directionX);
		translateTransition.setByY(directionY);
		translateTransition.setInterpolator(Interpolator.EASE_OUT);
		translateTransition.play();
		viser++;
		if (viser <= 500000 / tempsAnimation) {
			translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					transitionLoop(new TranslateTransition());
				}
			});
		} else {
			translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FadeTransition ft = new FadeTransition(Duration.millis(300), balle);
					ft.setFromValue(1.0);
					ft.setToValue(0);
					ft.play();
					ft.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							balle.setTranslateX(-1000);
							balle.setTranslateY(-1000);
						}
					});
				}
			});
		}

	}

	public Circle bullet() {
		return balle;
	}
}
