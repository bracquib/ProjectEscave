package info3.game;

/**
 * Un vecteur à deux éléments.
 * 
 * Peut servir à représenter une position, une vitesse, une taille, ou autre.
 * 
 * La position d'une entité correspond à son coin en haut à gauche. L'axe X va
 * vers la droite et l'axe Y vers le bas.
 * 
 * # Le double système de coordonnées
 * 
 * Pour les positions, on a un double système : - des coordonnées globales qui
 * indiquent où se trouve une entité dans l'ensemble du monde - des coordonnées
 * à l'écran qui indiquent où elle se trouve par rapport à la caméra
 * 
 * Des fonctions sont fournies dans cette classe pour faire la conversion entre
 * les deux.
 */
public class Vec2 {
	/**
	 * Le vecteur nul
	 */
	public static final Vec2 ZERO = new Vec2(0.0f, 0.0f);

	/**
	 * Le vecteur qui ne contient que des 1
	 */
	public static final Vec2 ONE = new Vec2(1.0f, 1.0f);

	float x;
	float y;

	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Prend une position dans l'espace global et la renvoie dans l'espace de
	 * l'écran
	 * 
	 * @param cameraPosition La position de la caméra
	 * @return La position dans l'espace de l'écran
	 */
	public Vec2 globalToScreen(Vec2 cameraPosition) {
		return this.minus(cameraPosition);
	}

	/**
	 * Calcule la différence entre deux vecteurs
	 * 
	 * @param other L'autre vecteur
	 * @return La différence de ce vecteur et de l'autre
	 */
	public Vec2 minus(Vec2 other) {
		return new Vec2(this.x - other.x, this.y - other.y);
	}
}
