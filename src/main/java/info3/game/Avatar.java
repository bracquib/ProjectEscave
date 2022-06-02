package info3.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * La réprésentation graphique d'une entité.
 * 
 * Cette représentation peut éventuellement être animée.
 */
public class Avatar {
	/**
	 * Dans le cas d'une image statique, ce tableau ne contient qu'un élément.
	 * 
	 * Si on a une animation, il contient les différentes images de l'animation.
	 */
	BufferedImage[] images;

	/**
	 * Indique à quel indice de l'animation on se trouve.
	 * 
	 * Reste à 0 dans le cas d'une image statique.
	 */
	int imageIndex = 0;

	/**
	 * Indique combien de temps s'est écoulé depuis le dernier changement d'image.
	 */
	long imageElapsed = 0;

	/**
	 * Indique le temps après lequel on change d'image dans l'animation.
	 * 
	 * Si il vaut 0, on n'a pas d'animation, même si on a plusieurs images. Le
	 * contrôle se fait alors avec la méthode `nextFrame`.
	 */
	long animationDelay;

	/**
	 * Crée un nouvel avatar statique.
	 * 
	 * @param file Le fichier dans lequel se trouve l'image à charger
	 */
	public Avatar(String file) {
		this(file, 1, 0);
	}

	/**
	 * Crée un nouvel avatar statique, mais qui a plusieurs images.
	 * 
	 * On peut contrôler quelle image s'affiche avec `nextFrame`.
	 * 
	 * @param file     Le fichier dans lequel se trouve les images à charger
	 * @param imageLen Le nombre de sous-images dans le fichier
	 */
	public Avatar(String file, int imageLen) {
		this(file, imageLen, 0);
	}

	/**
	 * Crée un nouvel avatar animé.
	 * 
	 * @param file           Le fichier dans lequel se trouve les images à charger
	 * @param imageLen       Le nombre de sous-images dans le fichier
	 * @param animationDelay Le nombre de millisecondes entre deux images de
	 *                       l'animation
	 */
	public Avatar(String file, int imageLen, long animationDelay) {
		try {
			this.images = loadSprite("resources/" + file, 1, imageLen);
			this.animationDelay = animationDelay;
		} catch (IOException ex) {
			System.out.println("[WARN] Impossible de charger " + file);
		}
	}

	/**
	 * Mets à jour le temps écoulé pour savoir si on doit passer à l'image suivante
	 * de l'animation ou non.
	 * 
	 * @param elapsed Le nombre de millisecondes qui se sont écoulées depuis le
	 *                dernier tick.
	 */
	public void tick(long elapsed) {
		this.imageElapsed += elapsed;
		if (this.animationDelay > 0 && this.imageElapsed > this.animationDelay) {
			this.imageElapsed = 0;
			this.nextFrame();
		}
	}

	/**
	 * Dessine l'image sur l'écran.
	 * 
	 * @param g            La toile sur laquelle on dessine
	 * @param screenCoords Les coordonées auxquelles dessiner l'image
	 */
	public void paint(Graphics g, Vec2 screenCoords) {
		this.paint(g, screenCoords, Vec2.ONE);
	}

	/**
	 * Dessine l'image sur l'écran.
	 * 
	 * @param g            La toile sur laquelle on dessine
	 * @param screenCoords Les coordonées auxquelles dessiner l'image
	 * @param scale        L'échelle de l'image
	 */
	public void paint(Graphics g, Vec2 screenCoords, Vec2 scale) {
		BufferedImage img = this.images[this.imageIndex];
		g.drawImage(img, (int) screenCoords.getX(), (int) screenCoords.getY(), ((int) scale.getX()) * img.getWidth(),
				((int) scale.getY()) * img.getHeight(), null);
	}

	/**
	 * Charge des images depuis le disque
	 * 
	 * @param filename Le nom du fichier à charger
	 * @param nrows    Le nombre de lignes (si on a plusieurs images)
	 * @param ncols    Le nombre de colonnes (si on a plusieurs images)
	 * @return Un tableau contenant une ou plusieurs images
	 * @throws IOException
	 */
	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			BufferedImage[] images = new BufferedImage[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		}
		throw new IOException("Fichier " + filename + " introuvable");
	}

	/**
	 * Passe à l'image suivante de l'animation
	 */
	void nextFrame() {
		this.imageIndex = (this.imageIndex + 1) % this.images.length;
	}
}
