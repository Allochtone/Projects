package bullet;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Tempete {
	private Circle drop;
	private final double MAX = 605;

	public Tempete(final double centerX, final double centerY, double rayon, final int direction, final double tempsAnimation) {
		drop = new Circle(centerX, centerY, rayon);
		drop.setFill(Color.BLUE);
		FadeTransition ft = new FadeTransition(Duration.millis(300), drop);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setDelay(Duration.millis((tempsAnimation * (Math.random() * 20)) / 30));
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				PathTransition pathTransition = new PathTransition();
				Path path = new Path();
				pathTransition.setDuration(Duration.millis(tempsAnimation));
			     pathTransition.setNode(drop);
			     pathTransition.setPath(path);
				switch (direction) {
				case 0:
				     path.getElements().add (new MoveTo (centerX, centerY));
				     path.getElements().add (new ArcTo(1500, 630, 0,centerX, 630,false,false));
				     pathTransition.setDuration(Duration.millis(tempsAnimation));
				     pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
				     
					break;
				case 1:
					path.getElements().add (new MoveTo (centerX, centerY));
				     path.getElements().add (new ArcTo(1500, 630, 0,centerX,-25 ,false,false));
				     pathTransition.setDuration(Duration.millis(tempsAnimation));
				     pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
					break;
				case 2:
					path.getElements().add (new MoveTo (centerX, centerY));
				     path.getElements().add (new ArcTo(630, 1500, 0,630,centerY ,false,false));
				     pathTransition.setDuration(Duration.millis(tempsAnimation));
				     pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
					break;
				case 3:
					path.getElements().add (new MoveTo (centerX, centerY));
				     path.getElements().add (new ArcTo(630, 1500, 0,-25,centerY ,false,false));
				     pathTransition.setDuration(Duration.millis(tempsAnimation));
				     pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
					break;

				}
				pathTransition.play();
				pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						FadeTransition ft = new FadeTransition(Duration.millis(300), drop);
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.play();
					}
				});
			}
		});
	}

	public Circle bullet() {
		return drop;
	}
}
