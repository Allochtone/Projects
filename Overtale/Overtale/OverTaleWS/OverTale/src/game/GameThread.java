package game;

import java.io.IOException;
import java.util.ArrayList;

import bullet.BulletFactory;
import controllers.GameController;
import controllers.MenuController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameThread extends Service<Void> {
	private static final double MOUVEMENT = 4;
	public static boolean findujeu = false;
	public ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	public BulletFactory bulletFactory;
	public int tempsAttaque = 0;
	public SimpleIntegerProperty vague = new SimpleIntegerProperty(0);
	private int tempsfin=0;
	private static int difficulte;

	public GameThread(int nrbjoueur) {
		bulletFactory = new BulletFactory();
		for (int i = 0; i < nrbjoueur; i++) {
			joueurs.add(new Joueur());	
		}
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				while (!findujeu) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							tempsAttaque++;
							prochaineVague();
							mouvementJoueur();
							dommageJoueur();
							if(joueursMorts()){
								findujeu=true;

							}
						}

					});
					
					Thread.sleep(15);
				}
					Thread.sleep(2000);

					cancel();
				
				
				
				
				return null;
			}

		};
	}
	
	private void prochaineVague(){
		switch(difficulte){
		case 0:
			if (tempsAttaque >= 250) {
				listeAttaque();
				tempsAttaque = 0;
				vague.set(vague.get()+1);;
			}
			break;
		case 1:
			if (tempsAttaque >= -25 * Math.atan(vague.get() / 8 - 5) + 230) {
				listeAttaque();
				tempsAttaque = 0;
				vague.set(vague.get()+1);;
			}
			break;
		case 2:
			if (tempsAttaque >= -50 * Math.atan(vague.get() / 4 - 5) + 170) {
				listeAttaque();
				tempsAttaque = 0;
				vague.set(vague.get()+1);;
			}
			break;
		case 3:
			if (tempsAttaque >= 200/(vague.get()+1)+100) {
				listeAttaque();
				tempsAttaque = 0;
				vague.set(vague.get()+1);;
			}
			break;
		}
		
	}
	
	private boolean joueursMorts(){
		int nombreMorts=0;
		for (int i = 0; i < joueurs.size(); i++) {
			if(joueurs.get(i).isMort()){
				joueurs.get(i).mort();
				nombreMorts++;
			}
		}
		if(nombreMorts>=joueurs.size()){
			return true;
		}else{
			return false;	
		}
	}
	
	private void listeAttaque() {
		if(vague.get()%10 == 0){
			bulletFactory.getGroup().getChildren().clear();
			for (int i = 0; i < joueurs.size(); i++) {
				joueurs.get(i).setPointVie(joueurs.get(i).getPointVie()+0.1);
			}
		}
		switch(difficulte){
		case 0:
			if (vague.get() < 10) {
				int random = ((int) (Math.random() * 3));
				switch(random){
				case 0:
					bulletFactory.wallAttack(5+vague.get(), 1);
					break;
				case 1:
					bulletFactory.squareAttack(5+vague.get(), 1);
					break;
				case 2:
					bulletFactory.rainAttack(5+vague.get(), 1);
					break;
				}
			} else if(vague.get() < 20) {
				int random = ((int) (Math.random() * 3));
				switch(random){
				case 0:
					bulletFactory.rebondAttack(6, 1);
					break;
				case 1:
					bulletFactory.squareAttack(vague.get(), 1);
					break;
				case 2:
					bulletFactory.tempeteAttack(vague.get(), 1);
					break;
				}
			}else
				if(vague.get() < 30){
					int random = ((int) (Math.random() * 3));
					switch(random){
					case 0:
						bulletFactory.rebondAttack(6, 2);
						break;
					case 1:
						bulletFactory.wallAttack(vague.get()/2, 1);
						break;
					case 2:
						bulletFactory.rainAttack(vague.get()/2, 2);
						break;
					}
			}else
				if(vague.get() < 40){
					int random = ((int) (Math.random() * 3));
					switch(random){
					case 0:
						bulletFactory.squareAttack(vague.get()/2, 1);
						break;
					case 1:
						bulletFactory.spearAttack(1, joueurs.get(0));
						break;
					case 2:
						bulletFactory.chercheurAttack(4, 1, joueurs.get(0));
						break;
					}
			}else
				if(vague.get() < 50){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.spearAttack(1, joueurs.get(0));
					break;
				case 1:
					bulletFactory.rebondAttack(12, 1);
					break;
				}
				}else
					if(vague.get() < 60){
					int random = (vague.get()%2);
					switch(random){
					case 0:
						bulletFactory.chercheurAttack(5, 2, joueurs.get(0));
						break;
					case 1:
						bulletFactory.tempeteAttack(vague.get()/3, 1);
						break;
					}
				}
			break;
		case 1:
			if (vague.get() < 10) {
				int random = ((int) (Math.random() * 3));
				switch(random){
				case 0:
					bulletFactory.wallAttack(5+vague.get(), 1);
					break;
				case 1:
					bulletFactory.squareAttack(5+vague.get(), 1);
					break;
				case 2:
					bulletFactory.rainAttack(10+vague.get(), 1);
					break;
				}
			} else if(vague.get() < 20) {
				int random = ((int) (Math.random() * 3));
				if(random == 0){
					bulletFactory.rebondAttack(6, 1);
				}else
				if(random == 1){
					bulletFactory.squareAttack(vague.get()/2, 2);
					
				}else{
					bulletFactory.spearAttack(1, joueurs.get(0));
				}
			}else
				if(vague.get() < 30){
				int random = ((int) (Math.random() * 2));
				if(random == 0){
					bulletFactory.rainAttack(15, 2);
				}else{
					bulletFactory.chercheurAttack(4, 3, joueurs.get(0));
				
				}
			}else
				if(vague.get() < 40){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.tempeteAttack(100, 1);
					break;
				case 1:
					bulletFactory.rebondAttack(6, 2);
					break;
				}
			}else
				if(vague.get() < 50){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.spearAttack(2, joueurs.get(0));
					break;
				case 1:
					bulletFactory.rainAttack((int)vague.get()/3, 3);
					break;
				}
				}else
					if(vague.get() < 60){
					int random = (vague.get()%2);
					switch(random){
					case 0:
						bulletFactory.chercheurAttack(5, 3, joueurs.get(0));
						break;
					case 1:
						bulletFactory.rebondAttack(12, 2);
						break;
					}
				}
			break;
		case 2:
			if (vague.get() < 10) {
				int random = ((int) (Math.random() * 3));
				switch(random){
				case 0:
					bulletFactory.wallAttack(10+vague.get(), 1);
					break;
				case 1:
					bulletFactory.squareAttack(10+vague.get(), 1);
					break;
				case 2:
					bulletFactory.rainAttack(15+vague.get(), 1);
					break;
				}
			} else if(vague.get() < 20) {
				int random = ((int) (Math.random() * 3));
				if(random == 0){
					bulletFactory.rebondAttack(12, 1);
				}else
				if(random == 1){
					bulletFactory.squareAttack(vague.get(), 2);
					
				}else{
					bulletFactory.spearAttack(2, joueurs.get(0));
				}
			}else
				if(vague.get() < 30){
				int random = ((int) (Math.random() * 2));
				if(random == 0){
					bulletFactory.rainAttack(vague.get()/2, 2);
				}else{
					bulletFactory.chercheurAttack(4, 4, joueurs.get(0));
				
				}
			}else
				if(vague.get() < 40){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.tempeteAttack(100, 2);
					break;
				case 1:
					bulletFactory.rebondAttack(12, 1);
					break;
				}
			}else
				if(vague.get() < 50){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.spearAttack(3, joueurs.get(0));
					break;
				case 1:
					bulletFactory.rainAttack((int)vague.get()/3, 3);
					break;
				}
				}else
					if(vague.get() < 60){
					int random = (vague.get()%2);
					switch(random){
					case 0:
						bulletFactory.chercheurAttack(5, 3, joueurs.get(0));
						break;
					case 1:
						bulletFactory.rebondAttack(12, 2);
						break;
					}
				}
			break;
		case 3:
			if (vague.get() < 10) {
				int random = ((int) (Math.random() * 3));
				switch(random){
				case 0:
					bulletFactory.wallAttack(15+vague.get(), 1);
					break;
				case 1:
					bulletFactory.squareAttack(15+vague.get(), 1);
					break;
				case 2:
					bulletFactory.rainAttack(20+vague.get(), 1);
					break;
				}
			} else if(vague.get() < 20) {
				int random = ((int) (Math.random() * 3));
				if(random == 0){
					bulletFactory.rebondAttack(12, 2);
				}else
				if(random == 1){
					bulletFactory.squareAttack(vague.get(), 2);
					
				}else{
					bulletFactory.spearAttack(3, joueurs.get(0));
				}
			}else
				if(vague.get() < 30){
				int random = ((int) (Math.random() * 2));
				if(random == 0){
					bulletFactory.rainAttack(vague.get()/2, 2);
				}else{
					bulletFactory.chercheurAttack(8, 4, joueurs.get(0));
				
				}
			}else
				if(vague.get() < 40){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.tempeteAttack(100, 2);
					break;
				case 1:
					bulletFactory.rebondAttack(12, 1);
					break;
				}
			}else
				if(vague.get() < 50){
				int random = (vague.get()%2);
				switch(random){
				case 0:
					bulletFactory.spearAttack(4, joueurs.get(0));
					break;
				case 1:
					bulletFactory.rainAttack((int)vague.get()/3, 3);
					break;
				}
				}else
					if(vague.get() < 60){
					int random = (vague.get()%2);
					switch(random){
					case 0:
						bulletFactory.chercheurAttack(10, 3, joueurs.get(0));
						break;
					case 1:
						bulletFactory.rebondAttack(12, 2);
						break;
					}
				}
			break;
		}
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	private void mouvementJoueur() {
		for (Joueur joueur : joueurs) {
			switch (joueur.getDirection()) {
			case 0:
				break;
			case 1:
				joueur.setPosX(joueur.getPosX() - MOUVEMENT);
				break;
			case 2:
				joueur.setPosX(joueur.getPosX() - 3.4);
				joueur.setPosY(joueur.getPosY() - 3.4);
				break;
			case 3:
				joueur.setPosY(joueur.getPosY() - MOUVEMENT);
				break;
			case 4:
				joueur.setPosX(joueur.getPosX() + 3.4);
				joueur.setPosY(joueur.getPosY() - 3.4);
				break;
			case 5:
				joueur.setPosX(joueur.getPosX() + MOUVEMENT);
				break;
			case 6:
				joueur.setPosX(joueur.getPosX() + 3.4);
				joueur.setPosY(joueur.getPosY() + 3.4);
				break;
			case 7:
				joueur.setPosY(joueur.getPosY() + MOUVEMENT);
				break;
			case 8:
				joueur.setPosX(joueur.getPosX() - 3.4);
				joueur.setPosY(joueur.getPosY() + 3.4);
				break;
			}
		}
	}

	public Group setGroupe() {
		return bulletFactory.getGroup();
	}

	private void dommageJoueur() {
		for (int i = 0; i < bulletFactory.getGroup().getChildren().size(); i++) {
			for (int j = 0; j < joueurs.size(); j++) {
				Joueur joueur = joueurs.get(j);
				if (joueur.getVulnerable()) {
					if (bulletFactory.getGroup().getChildren().get(i).getBoundsInParent().intersects(joueur.getImage().getBoundsInParent())) {
						joueur.setPointVie(joueur.getPointVie() - 0.1);
						joueur.setVulnerable(false);
						FadeTransition ft = new FadeTransition(Duration.millis(150), joueur.getImage());
						ft.setFromValue(1.0);
						ft.setToValue(0);
						ft.setCycleCount(4);
						ft.setAutoReverse(true);
						ft.play();
						switch (j) {
						case 0:
							ft.setOnFinished(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									FadeTransition ft = new FadeTransition(Duration.millis(200), joueurs.get(0).getImage());
									ft.setFromValue(0);
									ft.setToValue(1);
									ft.play();
									joueurs.get(0).setVulnerable(true);
								}});
							break;
						case 1:
							ft.setOnFinished(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									FadeTransition ft = new FadeTransition(Duration.millis(200), joueurs.get(1).getImage());
									ft.setFromValue(0);
									ft.setToValue(1);
									ft.play();
									joueurs.get(1).setVulnerable(true);
								}});
							break;
						case 2:
							ft.setOnFinished(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									FadeTransition ft = new FadeTransition(Duration.millis(200), joueurs.get(2).getImage());
									ft.setFromValue(0);
									ft.setToValue(1);
									ft.play();
									joueurs.get(2).setVulnerable(true);
								}});
							break;
						case 3:
							ft.setOnFinished(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									FadeTransition ft = new FadeTransition(Duration.millis(200), joueurs.get(3).getImage());
									ft.setFromValue(0);
									ft.setToValue(1);
									ft.play();
									joueurs.get(3).setVulnerable(true);
								}});
							break;
						}
					}
				}	
			}
		}
	}

	public void initilaze() {
		findujeu = false;
		joueurs = new ArrayList<Joueur>();
		tempsAttaque = 0;
		vague = new SimpleIntegerProperty(0);
		tempsfin=0;
		
	}

	public static void setdifficulte(int difficulte2) {

		difficulte = difficulte2;
		
	}
}