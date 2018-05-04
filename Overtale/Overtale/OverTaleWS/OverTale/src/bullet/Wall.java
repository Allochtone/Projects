package bullet;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Wall {
	private Line wall;
	private final double MAX = 605;
	
	public Wall(double startX, double startY, double endX, double endY, int ordre,final int direction,final double tempsAnimation){
		wall = new Line(startX, startY, endX, endY);
		wall.setStroke(Color.BLUEVIOLET);
		wall.setStrokeWidth(5);
		FadeTransition ft = new FadeTransition(Duration.millis(300), wall);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setDelay(Duration.millis((tempsAnimation*ordre)/30));
		ft.play();
		RotateTransition rt = new RotateTransition(Duration.millis(300), wall);
		rt.setFromAngle(0);
		rt.setToAngle(360);
		rt.setDelay(Duration.millis(100*ordre));
		rt.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis(tempsAnimation));
				translateTransition.setNode(wall);
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
						FadeTransition ft = new FadeTransition(Duration.millis(300), wall);
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.play();
					}
				});
			}});
	}
	
	public Line bullet() {
		return wall;
	}


}
