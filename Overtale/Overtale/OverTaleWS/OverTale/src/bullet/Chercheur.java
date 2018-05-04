package bullet;

import game.Joueur;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Chercheur {
	private Polygon chercheur;
	private final double MAX = 605;
	private final double tempsAnimation;
	private int viser = 0;
	private Joueur joueur;

	public Chercheur(double posX, double posY, final double tempsAnimation,Joueur joueur) {
		this.joueur = joueur;
		this.tempsAnimation = tempsAnimation;
		chercheur = new Polygon();
		chercheur.getPoints().addAll(new Double[] { 0.0, 0.0, 30.0, 0.0, 15.0, 30.0 });
		chercheur.setFill(Color.GOLD);
		chercheur.setTranslateX(posX);
		chercheur.setTranslateY(posY);
		FadeTransition ft = new FadeTransition(Duration.millis(300), chercheur);
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

	public Polygon bullet() {
		return chercheur;
	}

	private void transitionLoop(TranslateTransition translateTransition) {
		translateTransition.setDuration(Duration.millis(50));
		translateTransition.setNode(chercheur);
		translateTransition.setCycleCount(1);
		if(joueur.getPosX()-chercheur.getTranslateX() <= 0){
			translateTransition.setByX(-4);
		}else{
			translateTransition.setByX(4);
		}
		if(joueur.getPosY()-chercheur.getTranslateY() <= 0){
			translateTransition.setByY(-4);
		}else{
			translateTransition.setByY(4);
		}
		translateTransition.setInterpolator(Interpolator.EASE_OUT);
		translateTransition.play();
		viser++;
		if (viser <= 200000/tempsAnimation) {
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
					FadeTransition ft = new FadeTransition(Duration.millis(300), chercheur);
					ft.setFromValue(1.0);
					ft.setToValue(0);
					ft.play();
					ft.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							chercheur.setTranslateX(-100);
							chercheur.setTranslateY(-100);
						}
					});
				}
			});
		}
	}

}
