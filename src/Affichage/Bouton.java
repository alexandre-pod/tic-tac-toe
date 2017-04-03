package Affichage;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import Controleur.Partie;

public class Bouton extends JButton implements MouseListener
{
	private Image img;
	private boolean caseJouee = false;
	private boolean previsu = true;

	public Bouton()
	{
		this.img = GestionnaireImage.FOND.getImage();
		addMouseListener(this);
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(GestionnaireImage.FOND.getImage(), 0, 0, getWidth(), getHeight(), this);// affichage de l'image de fond

		if(!caseJouee && !(img == GestionnaireImage.FOND.getImage()))
		{
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // affichage de l'image en transparent
		}
		else g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // affichage de l'image sans transparence

		g2d.drawImage(this.img, 0, 0, getWidth(), getHeight(), this); // affichage de l'image de la vairble img
	}

	@Override
	public void setText(String nom) // fonction setText() modifiée pour changer l'image de fond plutot que le texte du bouton
	{		
		switch(nom){
		case"X":
			this.img = GestionnaireImage.CROIX.getImage();
			caseJouee = true;
			break;
		case"O":
			this.img = GestionnaireImage.ROND.getImage();
			caseJouee = true;
			break;
		case" ":
			this.img = GestionnaireImage.FOND.getImage(); 
			caseJouee = false;
			break;
		}
		
		this.repaint();
	}
	public void setPrevisualisation(boolean prev)
	{
		this.previsu = prev;
	}

	// gestion de l'affichage au survol d'une case
	public void mouseEntered(MouseEvent event)
	{
		if (!this.caseJouee && Partie.partieEnCours() && previsu) {
			if(Partie.getJoueurActuel() == 1) this.img = GestionnaireImage.CROIX.getImage();
			if(Partie.getJoueurActuel() == 2) this.img = GestionnaireImage.ROND.getImage();
		}
		this.repaint();
	}

	public void mouseExited(MouseEvent event)
	{
		if (!this.caseJouee) {
			this.img = GestionnaireImage.FOND.getImage();
		}
		this.repaint();
	}

	public void mouseClicked(MouseEvent event) {}

	public void mousePressed(MouseEvent event) {}

	public void mouseReleased(MouseEvent event) {}
}
