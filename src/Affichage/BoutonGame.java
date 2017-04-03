package Affichage;

import java.awt.Color;

public class BoutonGame extends Bouton
{
	private int pos;

	public BoutonGame(int p)
	{
		this.pos = p;
		setBackground(Color.GREEN);
	}

	public int getPos()
	{
		return pos;
	}
}
