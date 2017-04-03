package Affichage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AffGame extends JPanel {

	private JPanel grille;
	private BoutonGame[] boutonGrille;
	private JButton boutonReset, boutonMenu;
	private JLabel affichage;

	// nom des commandes des boutons
	public static final String GRILLE = "Grille", MENU = "Menu", RESET = "Reset";

	public AffGame(ActionListener a)
	{
		initPanel();
		addActionListener(a);
	}

	private void initPanel()
	{
		// Création des differents elements
		grille = new JPanel(new GridLayout(3, 3));
		boutonGrille = new BoutonGame[9];
		affichage = new JLabel(" ");
		affichage.setHorizontalAlignment(getWidth()/2);

		for(int i=0 ; i<9 ; i++)
		{
			boutonGrille[i] = new BoutonGame(i);
			boutonGrille[i].setActionCommand(GRILLE);
			grille.add(boutonGrille[i]);
		}

		// Création des boutons menu et quitter

		boutonMenu = new JButton("menu");
		boutonMenu.setActionCommand(MENU);

		boutonReset = new JButton("restart");
		boutonReset.setActionCommand(RESET);

		// organisation des composants

		JPanel panelBouton = new JPanel();

		panelBouton.add(boutonReset);
		panelBouton.add(boutonMenu);

		this.setLayout(new BorderLayout());
		this.add(affichage, BorderLayout.NORTH);
		this.add(grille, BorderLayout.CENTER);
		this.add(panelBouton, BorderLayout.SOUTH);
	}

	// fonction permettant de definir qui va gerer l'action des boutons
	public void addActionListener(ActionListener actionListener)
	{
		for(int i=0 ; i<9 ; i++)
		{
			boutonGrille[i].addActionListener(actionListener);
		}
		boutonMenu.addActionListener(actionListener);
		boutonReset.addActionListener(actionListener);
	}

	// fonction permettant de modifier la grille
	public void updateGrille(String[] grille)
	{
		for(int i=0 ; i<9 ; i++)
		{
			boutonGrille[i].setText(grille[i]);
		}
	}
	// fonction permettant de modifier le message
	public void setMessage(String message)
	{
		this.affichage.setText(message);
	}
	public int getPosBoutonGame(JButton b)
	{
		for(int i=0 ; i<9 ; i++)
		{
			if( ((BoutonGame)b) == boutonGrille[i]) return i;
		}
		return -1;
	}
	public void setPrevisuBouton(boolean prev)
	{
		for(int i=0 ; i<9 ; i++)
		{
			boutonGrille[i].setPrevisualisation(prev);
		}
	}
}
