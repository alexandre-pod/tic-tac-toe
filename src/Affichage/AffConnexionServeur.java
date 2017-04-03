package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.PartieMultiClient;

public class AffConnexionServeur extends JPanel
{
	private JTextField adresseIP = new JTextField(15);
	private JButton menu, connexion;
	private JLabel information;

	public AffConnexionServeur(String title, PartieMultiClient partieMultiClient) {
		Fenetre.getFenetre().setTitle(title); // changement du titre de la fenetre

		// configurations des boutons et de la zone de texte
		menu = new JButton("Retour au menu");
		menu.setActionCommand(AffGame.MENU);
		menu.addActionListener(partieMultiClient);
		connexion = new JButton("Connexion");
		connexion.setActionCommand("connexion");
		connexion.addActionListener(partieMultiClient);
		adresseIP.setPreferredSize(new Dimension(150, 30));
		information = new JLabel("  Entrez l'adresse IP : ");
		information.setHorizontalAlignment(this.getWidth()/2);

		this.setLayout(new GridLayout(2, 2, 150, 150));

		JPanel panelAdresse = new JPanel();
		panelAdresse.setLayout(new BorderLayout());
		panelAdresse.add(information, BorderLayout.CENTER);
		panelAdresse.add(adresseIP, BorderLayout.SOUTH);

		JPanel panelConnexion = new JPanel ();
		panelConnexion.setLayout (new BorderLayout());
		panelConnexion.add(connexion, BorderLayout.CENTER);
		panelConnexion.add(menu, BorderLayout.EAST);

		this.add(panelAdresse, BorderLayout.CENTER);
		this.add(panelConnexion, BorderLayout.SOUTH);
	}
	public String getIP()
	{
		return adresseIP.getText();
	}
}