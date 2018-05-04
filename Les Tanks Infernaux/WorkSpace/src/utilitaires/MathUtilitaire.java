package utilitaires;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MathUtilitaire {

	public static int randomEntre(int maxVal, int minVal) {
		int min = minVal;
		int max = maxVal;
		if (min > max) {
			min = maxVal;
			max = minVal;
		}
		List<Integer> array = new LinkedList<Integer>();
		Random rand = new Random();
		for (int i = min; i < max; i++) {
			array.add(i);
		}
		int retour = rand.nextInt(array.size());
		return array.get(retour);
	}

	public static int randomValeurs(int... valeurs) {
		Random rand = new Random();
		int retour = rand.nextInt(valeurs.length);
		return valeurs[retour];

	}

	public static double pythagoras(double x, double y) {

		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

	}

	public static double pythagorasInverse(double hyp, double cat) {
		return Math.sqrt(Math.pow(hyp, 2) - Math.pow(cat, 2));
	}

	public static char randomChar(char... cs) {
		Random rand = new Random();
		int retour = rand.nextInt(cs.length);
		return cs[retour];
	}

	public static char directionSelonInt(ArrayList<Integer> arrayInt) {
		char retour = ' ';
		int somme = 0;
		for (Integer j : arrayInt) {
			somme = somme + j.intValue();
		}
		if (somme > 0) {
			retour = 'L';
		} else if (somme < 0) {
			retour = 'R';
		} else {
			retour = 'S';
		}

		return retour;
	}

}
