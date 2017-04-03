package Affichage ;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum GestionnaireImage // énumérations des differentes images
{
	FOND("fond.png"),
	CROIX("croix.png"),
	ROND("rond.png");

	private Image img = null;

	private GestionnaireImage(String path){
		try {
			// on recupère l'image avec son nom
			img = ImageIO.read(getClass().getResource("/res/" + path));				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Image getImage() // retourne l'image
	{
		return img;
	}
}
