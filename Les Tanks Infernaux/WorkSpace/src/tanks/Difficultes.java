package tanks;

public enum Difficultes {
	FACILE("Niveau de difficulté où le tank ennemi ne tire que des projectiles physiques. Le tir a peut de chance d'atteindre une cible en mouvement.",
			new float[] { 0.01f, 0.05f, 0.1f, 0.2f, 0.5f, 1f, 1.5f, 2f, 2.5f, 2.75f, 3f }), MOYEN(
					"Niveau de difficulté où le tank ennemi peut tirer tous les modèles de projectiles. Le tir risque d'atteindre une cible peu en mouvement.",
					new float[] { 1f, 1f, 1f, 1f, 0.5f, 1f, 1.5f, 2f, 2.5f, 0.80f, 0.75f }), AIMBOT(
							"Niveau de difficulté où le tank ennemi risque de toucher une cible en mouvement.",
							new float[] { 1.5f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1.2f, 0.80f, 0.75f });

	private String description = null;
	private float[] modificateursTrajectoire = null;

	private Difficultes(String description, float[] modificateurs) {
		this.description = description;
		this.modificateursTrajectoire = modificateurs;
	}

	public String getDescription() {
		return description;
	}

	public float[] getModificateursTrajectoire() {
		return modificateursTrajectoire;
	}

	public void setModificateursTrajectoire(float modificateursTrajectoire, int position) {
		this.modificateursTrajectoire[position] = modificateursTrajectoire;
	}
}
