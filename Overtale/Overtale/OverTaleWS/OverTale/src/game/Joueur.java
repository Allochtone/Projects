package game;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Joueur {
	private static final double MIN_X = 4;
	private static final double MAX_X = 576;
	private static final double MIN_Y = 4;
	private static final double MAX_Y = 576;
	private SimpleDoubleProperty pointVie = new SimpleDoubleProperty(1);
	private boolean[] directions = new boolean[]{false,false,false,false};
	private int direction;
	private SimpleDoubleProperty posX = new SimpleDoubleProperty(288);
	private SimpleDoubleProperty posY = new SimpleDoubleProperty(288);
	private ImageView heart = new ImageView(new Image("/images/CoeurP1.png"));
	private boolean vulnerable;
	private boolean mort=false;
	
	public Joueur(){
		heart.translateXProperty().bind(posX);
		heart.translateYProperty().bind(posY);
		vulnerable = true;
	}
	
	public boolean isMort() {
		if(pointVie.get() <= 0.01){
		mort= true;
		}else{
			mort= false;
		}
		return mort;
	}
	
	public void setMort(boolean mort) {
		this.mort = mort;
	}
	
	
	public double getPosX() {
		return posX.get();
	}

	public double getPosY() {
		return posY.get();
	}

	public void setPosX(double posX) {
		if (verifierX(posX)) {
			this.posX.set(posX);
		}
	}

	private boolean verifierX(double posX) {
		boolean result = false;
		if ((posX >= MIN_X) && (posX <= MAX_X)) {
			result = true;
		}
		return result;
	}

	public void setPosY(double posY) {
		if (verifierY(posY)) {
			this.posY.set(posY);
		}
	}

	private boolean verifierY(double posY) {
		boolean result = false;
		if ((posY >= MIN_Y) && (posY <= MAX_Y)) {
			result = true;
		}
		return result;
	}
	public SimpleDoubleProperty PosX() {
		return posX;

	};

	public SimpleDoubleProperty PosY() {
		return posY;

	}
	
	public ImageView getImage(){
		return heart;
	}

	public int getDirection() {
		direction();
		return direction;
	}
	
	private void direction(){
		if(directions[0]){
			if(directions[1]){
				setDirection(2);
			}else if(directions[2]){
				setDirection(0);
			}else if(directions[3]){
				setDirection(8);
			}else{
				setDirection(1);
			}
		}else if(directions[1]){
			if(directions[2]){
				setDirection(4);
			}else if(directions[0]){
				setDirection(2);
			}else if(directions[3]){
				setDirection(0);
			}else{
				setDirection(3);
			}
		}else if(directions[2]){
			if(directions[0]){
				setDirection(0);
			}else if(directions[1]){
				setDirection(4);
			}else if(directions[3]){
				setDirection(6);
			}else{
				setDirection(5);
			}
		}else if(directions[3]){
			if(directions[0]){
				setDirection(8);
			}else if(directions[1]){
				setDirection(0);
			}else if(directions[2]){
				setDirection(6);
			}else{
				setDirection(7);
			}
		}
		int x=0;
		for (int i = 0; i < directions.length; i++) {
			if(!directions[i]){
				x++;
			}
		}
		if(x==directions.length){
			setDirection(0);
		}
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void setVulnerable(boolean vulerabiliter){
		vulnerable = vulerabiliter;
	}
	
	public boolean getVulnerable(){
		return vulnerable;
	}
	

	public SimpleDoubleProperty pointVie() {
		return pointVie;
	}

	public double getPointVie() {
		return pointVie().get();
	}
	
	public void setDirections(boolean direction,int position) {
		directions[position] = direction;
	}
	
	
	
	public void setPointVie(double pointVie) {
		if (pointVie <= 0) {
			this.pointVie.set(0);
		} else if(pointVie >= 1){
			this.pointVie.set(1);
		}else{
			this.pointVie.set(pointVie);
		}
	}
	
	public void mort(){
		ScaleTransition st = new ScaleTransition(Duration.millis(300), heart);
		st.setFromX(1.0);
		st.setFromY(1.0);
		st.setToX(2.0);
		st.setToY(2.0);
		st.setDelay(Duration.millis(1500));
		st.play();
		FadeTransition ft = new FadeTransition(Duration.millis(300), heart);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setDelay(Duration.millis(1500));
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						heart.setVisible(false);
					}
				});
	}
}
