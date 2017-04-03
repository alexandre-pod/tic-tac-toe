package Affichage;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.PartieMultiHote;

public class AttenteJoueur extends JPanel{

	private JLabel message;
	private JButton menu;

	public AttenteJoueur(String title, String message, PartieMultiHote partieMultiHote)
	{
		Fenetre.getFenetre().setTitle(title); // changement du titre de la fenetre
		// configuration des éléments
		this.message = new JLabel(message);
		this.message.setHorizontalAlignment(getWidth()/2);
		menu = new JButton("Retour au menu");
		menu.setActionCommand(AffGame.MENU);
		menu.addActionListener(partieMultiHote);
		
		// positionnement des éléments
		this.setLayout(new GridLayout(2, 1, 200, 200));

		this.add(this.message, BorderLayout.CENTER);
		this.add(menu, BorderLayout.SOUTH);
	}
}
