package terrain;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import projectiles.Projectiles;
import utilitaires.MathUtilitaire;

public class IniTerrain {
	public static final double XMAX = 1224;
	public static final double YMAX = 664;
	private static ArrayList<Rectangle> terrain;
	private TypeTerrain typeTerrain;
	private static ConditionTerrain conditionTerrain;
	private double resolution = 2;
	public static int difficulté;

	public IniTerrain(int difficulté,int typeterrain){
		this.difficulté = difficulté;
		typeTerrain = TypeTerrain.getFonctionRandom();
		conditionTerrain = ConditionTerrain.getConditionTerain(typeterrain);
	}

	/**
	 * Cette méthode utilise l'enum si dessous pour choisir un type de terrain,
	 * soit la fonction associé pour la construction de celui-ci.
	 * @param difficulté
	 *
	 */

	public void creerTerrain() {
		terrain = new ArrayList<Rectangle>();
		String fonction = typeTerrain.getNom();
		switch (fonction) {
		case "sin":
			for (int x = 0; x < XMAX / resolution; x++) {
				double hauteur = typeTerrain.getAmplitude(difficulté) * Math.sin(x * 2 * Math.PI / typeTerrain.getLongueurOnde())
						+ (YMAX / 3);
				creationRectangles(hauteur, x);
			}
			break;
		case "sin^3":
			for (int x = 0; x < XMAX / resolution; x++) {
				double hauteur = typeTerrain.getAmplitude(difficulté)
						* Math.pow(Math.sin(x * 2 * Math.PI / typeTerrain.getLongueurOnde()), 3) + (YMAX / 3);
				creationRectangles(hauteur, x);
			}
			break;
		case "sin(x)*(x + cos(x))":
			for (int x = 0; x < XMAX / resolution; x++) {
				double hauteur = typeTerrain.getAmplitude(difficulté) * Math.sin(x * 2 * Math.PI / typeTerrain.getLongueurOnde())
						* (x * 2 * Math.PI / typeTerrain.getLongueurOnde()
								+ Math.cos(x * 2 * Math.PI / typeTerrain.getLongueurOnde()))
						/ 10 + (YMAX / 3);
				creationRectangles(hauteur, x);
			}
			break;
		}
	}

	/**
	 * Cette méthode est la finalisation graphique du terrain avec les
	 * rectangles hors de la fonction.
	 *
	 * @param hauteur
	 * @param numeroRectangle
	 */
	private void creationRectangles(double hauteur, int numeroRectangle) {
		Rectangle rectangle = new Rectangle(resolution, hauteur);
		rectangle.setTranslateX(resolution * numeroRectangle);
		String condition = conditionTerrain.getNom();
		switch (condition) {
		case "gazon":
			Stop[] stops = new Stop[] { new Stop(0, Color.GREEN), new Stop(1, Color.SADDLEBROWN) };
			LinearGradient lg = new LinearGradient(0.50f, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
			rectangle.setFill(lg);
			break;
		case "boue":
			Stop[] stops1 = new Stop[] { new Stop(0, Color.SADDLEBROWN), new Stop(1, Color.rgb(80, 43, 6)) };
			LinearGradient lg1 = new LinearGradient(0.20f, 0, 0, 0.25f, true, CycleMethod.NO_CYCLE, stops1);
			rectangle.setFill(lg1);
			break;
		case "asphalt":
			Stop[] stops2 = new Stop[] { new Stop(0.99f, Color.DIMGRAY), new Stop(1, Color.rgb(80, 43, 6)) };
			LinearGradient lg2 = new LinearGradient(0.10f, 0, 0, 0.10f, true, CycleMethod.NO_CYCLE, stops2);
			rectangle.setFill(lg2);
			break;
		}
		rectangle.setTranslateY(YMAX - hauteur);
		rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
		terrain.add(rectangle);
	}
	/**
	 * Méthode qui vérifie si la position du projectile explosé et qui diminue la hauteur des rectanlges
	 * autour de son rayon par rapport à un rayon d'explosion.
	 *
	 * @param projectile
	 */
	public static void destructionTerrain(Projectiles projectile){
		double hauteur;
		for(int x =0; x<terrain.size();x++){
			Rectangle terrainbidon = terrain.get(x);
			if(terrainbidon.getTranslateX() <= projectile.getPosX()+projectile.getRayonExplosion() &&
					terrainbidon.getTranslateX() >= projectile.getPosX()-projectile.getRayonExplosion()){
				hauteur = MathUtilitaire.pythagorasInverse(projectile.getRayonExplosion(),terrainbidon.getTranslateX() - projectile.getPosX());
				terrainbidon.setTranslateY(terrainbidon.getTranslateY() + hauteur );
				terrainbidon.setHeight(terrainbidon.getHeight() - hauteur );
			}

		}
	}

	public static double getYmax() {
		return YMAX;
	}

	public static double getXmax() {
		return XMAX;
	}

	public static ArrayList<Rectangle> getTerrain() {
		return terrain;
	}

	public static double getConstanteRebondTerrain() {
		return conditionTerrain.getConstanteRebond();
	}

	public static String getConditionTerrain() {
		return conditionTerrain.getNom();
	}

}

/**
 * Cet enum est la banque de donné de fonctions. Ces fonctions sont créer avec
 * des amplitudes et des longueurs d'ondes aléatoires dans un range contrôlé.
 *
 * @author 1662573
 *
 */
enum TypeTerrain {
	SIN("sin"), SIN3("sin^3"), SINCOS("sin(x)*(x + cos(x))");

	private String nom;
	private double amplitude;
	private double longueurOnde;
	private final double AMPLITUDEMAX = 100;
	private final double LONGUEURONDEMAX = 150;

	TypeTerrain(String nom) {
		this.nom = nom;
		amplitude = (new Random()).nextInt((int) AMPLITUDEMAX);
		longueurOnde = (new Random()).nextInt((int) LONGUEURONDEMAX) + 100;
	}

	public double getAmplitude(int difficulté) {
		switch(difficulté){
		case 1:
			return this.amplitude*2;
		case 2:
			return this.amplitude/2;
		case 3:
			return this.amplitude/1.33 +25;
		case 4:
			return this.amplitude*2 + 100;
			default:
				return this.amplitude;
		}
	}

	public double getLongueurOnde() {
				return this.longueurOnde;

	}

	public String getNom() {
		return this.nom;
	}

	/**
	 * Méthode qui créer une fonction aléatoire et l'envoi.
	 * @param difficulté
	 *
	 * @return Un element de l'enum
	 */
	public static TypeTerrain getFonctionRandom() {
		Random r = new Random();
		int t = r.nextInt(TypeTerrain.values().length);
		TypeTerrain[] tp = TypeTerrain.values();
		return tp[t];
	}
}

/**
 * Enum qui détermine la couleur du terrain et du rebond des projectiles
 */
enum ConditionTerrain {
	BOUE("boue", 0.5f), GAZON("gazon", 0.33f), ASPHALT("asphalt", 0.15f);

	private String nom;
	private double constanteRebond;

	private ConditionTerrain(String nom, double constanteRebond) {
		this.nom = nom;
		this.constanteRebond = constanteRebond;
	}

	/**
	 * Méthode qui créer une condition aléatoire et l'envoi.
	 *
	 * @return Un element de l'enum
	 */
	public static ConditionTerrain getConditionTerain(int type){
		ConditionTerrain[] tp = ConditionTerrain.values();
		if(type==2){
			return tp[1];
		}else
			if(type==3){
				return tp[0];
			}else
			if(type==4){
				return tp[2];
			}else
				return getConditionRandom();
	}

	private static ConditionTerrain getConditionRandom() {
		Random r = new Random();
		int t = r.nextInt(ConditionTerrain.values().length);
		ConditionTerrain[] tp = ConditionTerrain.values();
		return tp[t];
	}

	public double getConstanteRebond() {
		return constanteRebond;
	}

	public String getNom() {
		return this.nom;
	}

}