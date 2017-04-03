package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Affichage.AffGame;
import Affichage.Fenetre;
import Affichage.Menu;

// Classe permettant de gérer une partie de morpion

public abstract class Partie implements ActionListener
{
	protected AffGame affichage;

	protected Joueur[] grille;
	protected static Joueur joueurActuel;
	protected static boolean jeuEnCours;

	public Partie()
	{
		affichage = new AffGame(this);
		init();
	}
	
	// initialisation des variables necessaires au controle d'une partie
	private void init()
	{
		grille = new Joueur[]{Joueur.NULL, Joueur.NULL, Joueur.NULL,
				Joueur.NULL, Joueur.NULL, Joueur.NULL,
				Joueur.NULL, Joueur.NULL, Joueur.NULL, };
		joueurActuel = Joueur.X;
		jeuEnCours = true;
		updateMessage("Joueur Actuel : " + joueurActuel);
	}

	// fonction restant a définir par les classes filles
	protected abstract void caseCliquee(int pos);

	// change la variable du joueurActuel
	protected void changeJoueurActuel()
	{
		if(joueurActuel == Joueur.X)
			joueurActuel = Joueur.O;
		else
			joueurActuel = Joueur.X;
	}
	
	public static int getJoueurActuel()
	{
		return joueurActuel.getValeur();
	}
	
	public static boolean partieEnCours()
	{
		return jeuEnCours;
	}
	
	// renvoie true si il y a un gagnant ou que la grille est pleine
	protected boolean finPartie()
	{
		//verifie si un joueur a gagné en alignant 3 cases
		for(int i=0 ; i<3 ; i++)
		{
			if(grille[i*3] == grille[i*3+1] && grille[i*3] == grille[i*3+2] && grille[i*3] != Joueur.NULL||
					grille[i] == grille[i+3] && grille[i] == grille[i+6] && grille[i] != Joueur.NULL||
					grille[0] == grille[4] && grille[0] == grille[8] && grille[0] != Joueur.NULL||
					grille[2] == grille[4] && grille[2] == grille[6] && grille[2] != Joueur.NULL )
			{
				updateMessage("Joueur Gagnant : " + joueurActuel);
				return true;
			}
		}
		//Compte le nombre de cases vide
		int count = 0;
		for(int i=0 ; i<9 ; i++)
		{
			if(grille[i]==Joueur.NULL) count++;
		}
		if(count == 0)
		{
			updateMessage("Match nul");
			return true;
		}
		return false;
	}

	// mise a jour la grille de l'affichage
	protected void updateGrille()
	{
		String[] grilleStr = new String[9];
		for(int i=0 ; i<9 ; i++)
		{
			grilleStr[i] = grille[i].toString();
		}
		affichage.updateGrille(grilleStr);
	} 
	
	// mise a jour du message de l'affichage
	protected void updateMessage(String str){
		affichage.setMessage(str);
	}

	// fonctionappellée lors d'une action d'un bouton
	public void actionPerformed(ActionEvent arg0)
	{
		switch(arg0.getActionCommand())
		{
		case AffGame.GRILLE:
			int pos =affichage.getPosBoutonGame((JButton)arg0.getSource());
			caseCliquee(pos);
			break;
		case AffGame.MENU:
			menu();
			break;
		case AffGame.RESET:
			reset();
			break;
		}
	}

	// remise a zero de la partie
	protected void reset()
	{
		init();
		updateGrille();
	}

	protected void menu() // renvois au menu
	{
		Fenetre.setAffichage(new Menu());
	}
}
