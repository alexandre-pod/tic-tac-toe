package Controleur;

import Affichage.Fenetre;

public class Partie1VS1 extends Partie
{
	public Partie1VS1(){
		// changement du titre de la fenetre
		Fenetre.getFenetre().setTitle("Partie Joueur contre Joueur");
		// affichage de la grille de jeu
		Fenetre.setAffichage(affichage);
	}

	/* définition de la fonction caseCliquee(int pos)
	 * 
	 * si la case sur laquelle le joueur n'est pas déja jouée,
	 * la case prend la valeur du joueur actuel,
	 * on verifie que la partie n'est pas finie (voictoire ou match nul)
	*/
	protected void caseCliquee(int pos)
	{
		if(grille[pos] == Joueur.NULL && jeuEnCours)
		{
			grille[pos] = joueurActuel;
			updateGrille();
			if(finPartie())
			{
				jeuEnCours = false;
			}else
			{
				changeJoueurActuel();
				updateMessage("Joueur Actuel : " + joueurActuel);
			}
		}
	}
}
