package bullet;

import java.util.PriorityQueue;
import java.util.Random;

import game.Joueur;
import javafx.scene.Group;

public class BulletFactory {
	public static Group vague = new Group();

	public synchronized void squareAttack(int quantité, int difficulté) {
		Group groupe = new Group();
		for (int i = 0; i < quantité; i++) {
			Square square = null;
			int random = ((int) (Math.random() * 4));
			switch (random) {
			case 0:
				square = new Square(-50, ((605 + 25 * difficulté) / quantité) * i, 25, 25, 0, 1000 + 2000 / difficulté,
						90 * (difficulté - 1));
				break;
			case 1:
				square = new Square(((605 + 25 * difficulté) / quantité) * i, -50, 25, 25, 1, 1000 + 2000 / difficulté,
						90 * (difficulté - 1));
				break;
			case 2:
				square = new Square(((605 + 25 * difficulté) / quantité) * i, 605 + 50, 25, 25, 2,
						1000 + 2000 / difficulté, 90 * (difficulté - 1));
				break;
			case 3:
				square = new Square(605 + 50, ((605 + 25 * difficulté) / quantité) * i, 25, 25, 3,
						1000 + 2000 / difficulté, 90 * (difficulté - 1));
				break;
			}
			groupe.getChildren().add(square.bullet());
		}
		vague.getChildren().addAll(groupe.getChildren());
	}

	public synchronized void wallAttack(int quantité, int difficulté) {
		Group groupe = new Group();
		Wall wall;
		Random r = new Random();
		int[] ar1 = new int[quantité];
		for (int i = 0; i < ar1.length; i++) {
			ar1[i] = r.nextInt(quantité);
		}
		int random = ((int) (Math.random() * 4));
		switch (random) {
		case 0:
			for (int i = 0; i < quantité; i++) {
				wall = new Wall(((605 + 25 * difficulté) / quantité) * i, -25.0,
						50 + ((605 + 25 * difficulté) / quantité) * i, -25.0, ar1[i], 0, 1000 + 2500 / difficulté);
				groupe.getChildren().add(wall.bullet());
			}
			break;
		case 1:
			for (int i = 0; i < quantité; i++) {
				wall = new Wall(((605 + 25 * difficulté) / quantité) * i, 605 + 25.0,
						50 + ((605 + 25 * difficulté) / quantité) * i, 605 + 25.0, ar1[i], 1, 1000 + 2500 / difficulté);
				groupe.getChildren().add(wall.bullet());
			}
			break;
		case 2:
			for (int i = 0; i < quantité; i++) {
				wall = new Wall(-25.0, ((605 + 25 * difficulté) / quantité) * i, -25.0,
						50.0 + ((605 + 25 * difficulté) / quantité) * i, ar1[i], 2, 1000 + 2500 / difficulté);
				groupe.getChildren().add(wall.bullet());
			}
			break;
		case 3:
			for (int i = 0; i < quantité; i++) {
				wall = new Wall(605 + 25.0, ((605 + 25 * difficulté) / quantité) * i, 605 + 25.0,
						50.0 + ((605 + 25 * difficulté) / quantité) * i, ar1[i], 3, 1000 + 2500 / difficulté);
				groupe.getChildren().add(wall.bullet());
			}
			break;
		}
		vague.getChildren().addAll(groupe.getChildren());

	}

	public synchronized void rainAttack(int quantité, int difficulté) {
		Group groupe = new Group();
		Rain rain;
		Random r = new Random();
		int[] ar1 = new int[quantité];
		for (int i = 0; i < ar1.length; i++) {
			ar1[i] = r.nextInt(quantité);
		}
		int random = ((int) (Math.random() * 4));
		switch (random) {
		case 0:
			for (int i = 0; i < quantité; i++) {
				rain = new Rain(r.nextInt(605), -25.0, 5 * difficulté, 0, 1000 + 1500 / difficulté);
				groupe.getChildren().add(rain.bullet());
			}
			break;
		case 1:
			for (int i = 0; i < quantité; i++) {
				rain = new Rain(r.nextInt(605), 605 + 25.0, 5 * difficulté, 1, 1000 + 1500 / difficulté);
				groupe.getChildren().add(rain.bullet());
			}
			break;
		case 2:
			for (int i = 0; i < quantité; i++) {
				rain = new Rain(-25.0, r.nextInt(605), 5 * difficulté, 2, 1000 + 1500 / difficulté);
				groupe.getChildren().add(rain.bullet());
			}
			break;
		case 3:
			for (int i = 0; i < quantité; i++) {
				rain = new Rain(605 + 25.0, r.nextInt(605), 5 * difficulté, 3, 1000 + 1500 / difficulté);
				groupe.getChildren().add(rain.bullet());
			}
			break;
		}
		vague.getChildren().addAll(groupe.getChildren());
	}

	public synchronized void chercheurAttack(int quantité, int difficulté, Joueur joueur) {
		Group groupe = new Group();
		Chercheur chercheur = null;
		for (int i = 0; i < quantité; i++) {
			/**
			 * TODO gerer le spawn des chercheurs
			 */
			int random = ((int) (Math.random() * 4));
			switch (random) {
			case 0:
				chercheur = new Chercheur(-50, ((605 + 25 * difficulté) / quantité) * i, 1000 + 2000 / difficulté,
						joueur);
				break;
			case 1:
				chercheur = new Chercheur(((605 + 25 * difficulté) / quantité) * i, -50, 1000 + 2000 / difficulté,
						joueur);
				break;
			case 2:
				chercheur = new Chercheur(((605 + 25 * difficulté) / quantité) * i, 605 + 50, 1000 + 2000 / difficulté,
						joueur);
				break;
			case 3:
				chercheur = new Chercheur(605 + 50, ((605 + 25 * difficulté) / quantité) * i, 1000 + 2000 / difficulté,
						joueur);
				break;
			}
			groupe.getChildren().add(chercheur.bullet());
		}
		vague.getChildren().addAll(groupe.getChildren());
	}

	public Group getGroup() {
		return vague;
	}

	public synchronized void spearAttack(double difficulté, Joueur joueur) {
		Group spears = new Group();
		for (int i = 0; i < 16; i++) {
			double pointcentreX = joueur.getPosX() + 250 * Math.cos(22.5 * i);
			double pointcentreY = joueur.getPosY() + 250 * Math.sin(22.5 * i);
			double point1X = pointcentreX - 25 * Math.sin(0 - 22.5 * i);
			double point1Y = pointcentreY - 25 * Math.cos(0 - 22.5 * i);
			double point2X = pointcentreX + 25 * Math.sin(0 - 22.5 * i);
			double point2Y = pointcentreY + 25 * Math.cos(0 - 22.5 * i);
			Spear spear = new Spear(point1X, point1Y, point2X, point2Y, i, 250, 1000 + 2500 / difficulté, joueur,(int)difficulté);
			spears.getChildren().add(spear.bullet());
		}
		vague.getChildren().addAll(spears.getChildren());

	}

	public synchronized void tempeteAttack(int quantité, int difficulté) {
		Group groupe = new Group();
		Tempete tempete;
		Random r = new Random();
		int[] ar1 = new int[quantité];
		for (int i = 0; i < ar1.length; i++) {
			ar1[i] = r.nextInt(quantité);
		}
		int random = ((int) (Math.random() * 4));
		switch (random) {
		case 0:
			for (int i = 0; i < quantité; i++) {
				tempete = new Tempete((i % 8) * 100 + 100, -25.0, 5, 0, 1500 / difficulté+1000);
				groupe.getChildren().add(tempete.bullet());
			}
			break;
		case 1:
			for (int i = 0; i < quantité; i++) {
				tempete = new Tempete((i % 8) * 100 - 200, 605 + 25.0, 5, 1, 1500 / difficulté+1000);
				groupe.getChildren().add(tempete.bullet());
			}
			break;
		case 2:
			for (int i = 0; i < quantité; i++) {
				tempete = new Tempete(-25.0, (i % 8) * 100 - 200, 5, 2, 1500 / difficulté+1000);
				groupe.getChildren().add(tempete.bullet());
			}
			break;
		case 3:
			for (int i = 0; i < quantité; i++) {
				tempete = new Tempete(605 + 25.0, (i % 8) * 100 + 100, 5, 3, 1500 / difficulté+1000);
				groupe.getChildren().add(tempete.bullet());
			}
			break;
		}
		vague.getChildren().addAll(groupe.getChildren());
	}

	public synchronized void texDropAttack(int quantité, int difficulté) {
		Group groupe = new Group();
		TextDrop text;
		Random r = new Random();
		int[] ar1 = new int[quantité];
		for (int i = 0; i < ar1.length; i++) {
			ar1[i] = r.nextInt(quantité);
		}
		int random = ((int) (Math.random() * 1));
		switch (random) {
		case 0:
			for (int i = 0; i < quantité; i++) {
				text = new TextDrop(302.5, -25, 0, 1000 + 2500 / difficulté);
				groupe.getChildren().add(text.bullet());
			}
			break;
		case 1:
			for (int i = 0; i < quantité; i++) {
				text = new TextDrop(302.5, 630, 1, 1000 + 2500 / difficulté);
				groupe.getChildren().add(text.bullet());
			}
			break;
		case 2:
			for (int i = 0; i < quantité; i++) {
				text = new TextDrop(-25, 302.5, 2, 1000 + 2500 / difficulté);
				groupe.getChildren().add(text.bullet());
			}
			break;
		case 3:
			for (int i = 0; i < quantité; i++) {
				text = new TextDrop(630, 302.5, 3, 1000 + 2500 / difficulté);
				groupe.getChildren().add(text.bullet());
			}
			break;
		}
		vague.getChildren().addAll(groupe.getChildren());

	}

	public synchronized void rebondAttack(int quantité, int difficulté) {
		Group groupe = new Group();
		Rebond rebond;
		double randomX = ((int) (Math.random() * 605));
		double randomY = ((int) (Math.random() * 605));
		for (int i = 0; i < quantité; i++) {
			rebond = new Rebond(randomX, randomY, 5 * difficulté, i, 1000+2500 / difficulté);
			groupe.getChildren().add(rebond.bullet());
		}
		vague.getChildren().addAll(groupe.getChildren());
	}
}
