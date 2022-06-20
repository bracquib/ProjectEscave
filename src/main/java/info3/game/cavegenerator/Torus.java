package info3.game.cavegenerator;

/**
 * Une classe permettant d'accéder à une matrice d'entier comme si c'était un
 * tore.
 * 
 * Si on demande un indice hors de la matrice, on reboucle de l'autre côté.
 * 
 * Cette classe sert à manipuler la carte de notre monde qui est torique, chaque
 * entier correspondant à un type de bloc différent.
 */
public class Torus {
	int[] array;

	int width;
	int height;

	public Torus(int w, int h) {
		this.width = w;
		this.height = h;
		this.array = new int[h * w];
	}

	public Torus(int[][] array) {
		this.width = array.length;
		this.height = array[0].length;
		this.array = new int[this.width * this.height];
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				this.array[y * this.height + x] = array[x][y];
			}
		}
	}

	public int[][] toArray() {
		int[][] arr = new int[this.width][this.height];
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				arr[x][y] = this.array[y * this.height + x];
			}
		}
		return arr;
	}

	public int get(int x, int y) {
		return this.getNoWrap(this.wrap(x, this.width), this.wrap(y, this.height));
	}

	private int getNoWrap(int x, int y) {
		return this.array[y * this.height + x];
	}

	public void set(int x, int y, int value) {
		this.setNoWrap(this.wrap(x, this.width), this.wrap(y, this.height), value);
	}

	private void setNoWrap(int x, int y, int value) {
		this.array[y * this.height + x] = value;
	}

	private int wrap(int x, int max) {
		x = x % max;
		if (x < 0) {
			return max + x;
		} else {
			return x;
		}
	}
}
