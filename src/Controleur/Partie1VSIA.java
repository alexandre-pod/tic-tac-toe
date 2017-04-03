package Controleur;

import Affichage.Fenetre;

public class Partie1VSIA extends Partie
{
	public Partie1VSIA()
	{
		// changement du titre de la fenetre et de son contenu
		Fenetre.getFenetre().setTitle("Partie Joueur contre IA");
		Fenetre.setAffichage(affichage);
	}

	// définition de la fonction caseCliquee()
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
				if(joueurActuel == Joueur.X)
				{
					changeJoueurActuel();
					caseCliquee(IA.caseAJouer(this.grille));
				}else
				{
					changeJoueurActuel();
					updateMessage("Joueur Actuel : " + joueurActuel);
				}
			}
		}
	}
}

class IA
{
	public static int caseAJouer(Joueur[] tabJ) 
	{
		int[] grilleInt = new int[9];
		for(int i=0 ; i<9 ; i++){
			grilleInt[i] = tabJ[i].getValeur();
		}
		return caseAJouer(grilleInt);
	}

	public static int caseAJouer(int[] tab) {  
		int X =-1;
		int W =-1;// position de la case a jouer pour gagner
		int U=-1;// position de la case a jouer pour contrer

		int[][] combinaison = {
				// lignes
				{0,1,2}, {3,4,5}, {6,7,8},
				// colones
				{0,3,6}, {1,4,7}, {2,5,8},
				// diagonales
				{0,4,8}, {2,4,6}};

		for(int[] comb : combinaison)
		{
			// recerche d'une case pour contrer
			if(tab[comb[0]]+tab[comb[1]]+tab[comb[2]] == 2 && tab[comb[0]]!=2 && tab[comb[1]]!=2 && tab[comb[2]]!=2)
			{
				if(tab[comb[0]] == 0) U = comb[0] ;
				else if(tab[comb[1]] == 0 ) U = comb[1] ;
				else if(tab[comb[2]] == 0) U = comb[2] ;
			}

			// recherche d'une case pour gagner
			if(tab[comb[0]]+tab[comb[1]]+tab[comb[2]] == 4 && tab[comb[0]]!=1 && tab[comb[1]]!=1 && tab[comb[2]]!=1)
			{
				if(tab[comb[0]] == 0) W = comb[0] ;
				else if(tab[comb[1]] == 0 ) W = comb[1] ;
				else if(tab[comb[2]] == 0) W = comb[2] ;
			}
		}

		System.out.print("Case à jouer pour contrer: " + U);
		System.out.print("Case à jouer pour gagner: " + W);

		if (W!=-1) X=W; // si un case pour gagner pour trouver, on joue cette case
		else if(U!=-1) X=U; // sinon si une case pour contrer est disponible, on joue cette case
		else // sinon un cherche une case jouer aléatoirement
		{
			do{
				X = (int) (Math.random()*9);
			}while(tab[X] != 0);
		}
		return X;
	}
}