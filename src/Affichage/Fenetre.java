package Affichage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame{

	private static Fenetre fenetre;

	public Fenetre()
	{
		// parametrage de la fenetre
		initFenetre();

		fenetre = this;
		
		setAffichage(new Menu()); // definie la vue apres la création
	}

	private void initFenetre() 
	{
		this.setSize(400, 400); // taille
		this.setTitle("Morpion !"); // titre
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // action lors de la fermeture
		this.setLocationRelativeTo(null); // position sur l'ecran
		this.setResizable(false);
		
		this.setVisible(true); // Affiche la fenetre
	}

	// fonction statique pouvant etre appelée depuis tout le programme
	public static void setAffichage(JPanel panel)
	{
		fenetre.setContentPane(panel);
		fenetre.revalidate(); // rafraichissement de a fenetre
	}
	
	public static Fenetre getFenetre()
	{
		return fenetre;
	}

	
	
	// Fonction appelée au demarrage du programe
	public static void main(String[] args)
	{	
		new Fenetre(); // Création de la fenetre
	}
}
