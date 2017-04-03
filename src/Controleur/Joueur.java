package Controleur;

public enum Joueur {
	NULL(0, " "),
	X(1, "X"),
	O(2, "O");

	String name = "";
	int valeur = -1;
	
	private Joueur(int i, String str){
		valeur = i;
		this.name = str;
	}
	public String toString(){
		return this.name;
	}
	public int getValeur(){
		return this.valeur;
	}
	public Joueur getJoueur(int i){
		if(i == 1)
			return Joueur.X;
		else if(i == 2)
			return Joueur.O;
		else
			return Joueur.NULL;
	}
}
