package Affichage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controleur.Partie1VS1;
import Controleur.Partie1VSIA;
import Controleur.PartieMultiHote;
import Controleur.PartieMultiClient;

public class Menu extends JPanel implements ActionListener
{
	// enumeration des differentes options des boutons ainsi que leur noms
	private enum Option
	{
		JOUEUR_CONTRE_JOUEUR("1 vs 1"),
		JOUEUR_CONTRE_IA("1 vs IA"),
		CREER_PARTIE_MULTI("Créer partie multijoueur"),
		REJOINDRE_PARTIE_MULTI("Rejoindre partie multijoueur"),
		QUITTER("Quitter");

		private String nom = "";

		private Option(String str)
		{ 
			this.nom = str; 
		}
		public String getName()
		{ 
			return nom; 
		}
	}

	// Variables de classe
	private Option[] tabOption;
	private BoutonMenu[] tabBouton;

	public Menu()
	{
		Fenetre.getFenetre().setTitle("Menu"); // changement du titre de la fenetre
		
		tabOption = Option.values();
		tabBouton = new BoutonMenu[tabOption.length];
		
		this.setLayout(new GridLayout(tabOption.length, 1));
		
		// Création des boutons
		for(int i=0 ; i<tabOption.length ; i++)
		{
			tabBouton[i] = new BoutonMenu(tabOption[i]);
			tabBouton[i].addActionListener(this);
			this.add(tabBouton[i]); // ajouter les boutons a l'affichage
		}
	}

	public void actionPerformed(ActionEvent arg0)
	{
		// récuperer le bouton qui est cliqué
		BoutonMenu bouton = (BoutonMenu)arg0.getSource();

		// actions en fonction des boutons
		switch(bouton.getOption())
		{
		case CREER_PARTIE_MULTI:
			new PartieMultiHote();
			break;

		case JOUEUR_CONTRE_IA:
			new Partie1VSIA();
			break;

		case JOUEUR_CONTRE_JOUEUR:
			new Partie1VS1();
			break;

		case REJOINDRE_PARTIE_MULTI:
			new PartieMultiClient();
			break;

		case QUITTER:
			// arrete le programme
			System.exit(0);
			break;
		}
	}
	
	// Bouton modifié
	class BoutonMenu extends JButton
	{
		private Option option;
		public BoutonMenu(Option o)
		{
			super();
			this.setText(o.getName());
			this.option = o;
		}
		public Option getOption()
		{ 
			return option; 
		}
	}
}
