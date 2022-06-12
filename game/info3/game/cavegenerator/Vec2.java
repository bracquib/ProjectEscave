package info3.game.cavegenerator;

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
public class Vec2 extends Object {

	float x;
	float y;

	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2(Vec2 copy) {
		this.x = copy.x;
		this.y = copy.y;
	}

	public Vec2(float a) {
		this.x = a;
		this.y = a;
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

	/**
	 * Calcule le produit scalaire entre deux vecteurs
	 * 
	 * @param other autre vecteur
	 * @return Le produit scalaire de ce vecteur et de l'autre
	 */
	public float dot(Vec2 other) {
		return this.x * other.x + this.y * other.y;
	}

	/**
	 * On fait le floor de deux vecteurs
	 * 
	 * @return a new vector with each element floor()ed.
	 */
	public Vec2 floor() {
		return new Vec2((float) Math.floor(this.x), (float) Math.floor(this.y));
	}

	/**
	 * Add 2 vectors
	 * 
	 * @return a new vector with sum of each vectors.
	 */
	public Vec2 add(Vec2 other) {
		return new Vec2(other.x + this.x, other.y + this.y);
	}

	public Vec2 add(int a) {
		return new Vec2(this.x + a, a + this.y);
	}

	public Vec2 sub(Vec2 other) {
		return new Vec2(other.x - this.x, other.y - this.y);
	}

	public Vec2 multiply(int a) {
		return new Vec2(this.x * a, this.y * a);
	}

	public Vec2 pow(int a) {
		return new Vec2((float) Math.pow(this.x, a), (float) Math.pow(this.y, a));
	}

	public float product() {
		return this.x * this.y;
	}

	public Vec2 abs() {
		return new Vec2(Math.abs(this.x), Math.abs(this.y));
	}

	public float length() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public Vec2 normalized() {
		return new Vec2(this.x / this.length(), this.y / this.length());
	}

	
}
