package Controleur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Affichage.AttenteJoueur;
import Affichage.Fenetre;

public class PartieMultiHote extends Partie 
{
	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;

	private Thread connexionJoueur;

	private String adresseIP;
	private int port = 12345;

	public PartieMultiHote(){
		this.jeuEnCours = false;

		try {
			adresseIP = InetAddress.getLocalHost().getHostAddress().toString(); // recherche de l'adresse IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
			adresseIP = "inconnue";
		}
		// Affichage de l'ecran d'attente
		Fenetre.setAffichage(new AttenteJoueur("Attente de connexion du joueur", "Adresse IP : " + adresseIP, this));

		try {
			connexionJoueur = new ConnexionJoueur(port);
			connexionJoueur.start();
		} catch (IOException e) {
			e.printStackTrace();
			couperConnexion();
		}
	}

	// Controle du réseau
	class ConnexionJoueur extends Thread
	{
		private final ServerSocket server; 

		public ConnexionJoueur(int port) throws IOException{
			server = new ServerSocket(port);
		}
		public void run()
		{
			try {
				socket = server.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
				joueurConnecte();
				while(true)
				{
					String message = in.readLine();
					if(message == ".") break;
					traiterInformation(message);
				}
			} catch (Exception se) {}
		}
		@Override
		public void interrupt()
		{
			try {
				server.close();
			} catch (IOException ignored) {
			} finally {
				super.interrupt();
			}
		}
	}

	public void joueurConnecte() {
		Fenetre.setAffichage(affichage); // Affichage de la grille de jeu
		Fenetre.getFenetre().setTitle("Joueur X");
		jeuEnCours = true;
	}

	protected void traiterInformation(String mess) // traite l'infirmation recue du joueur 2
	{
		String[] message = mess.split("\t");

		if(message[0] != null)
		{
			switch(message[0])
			{
			case"jeu":
				caseCliquee(Integer.valueOf(message[1]), Joueur.O);
				break;
			case"quitter":
				menu();
				break;
			}
		}
	}

	private void envoyer(String message) // envoyer un message au joueur 2
	{
		if(out != null)
		{
			out.println(message);
			out.flush();
		}
	}

	private void couperConnexion() // arrete toute connexion
	{
		try 
		{
			if(socket != null) socket.close();
			if(in != null) in.close();
			if(out != null) out.close();
			connexionJoueur.interrupt();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	// Controle de la partie
	protected void caseCliquee(int pos) 
	{
		caseCliquee(pos, Joueur.X);
	}

	protected void caseCliquee(int pos, Joueur j)
	{
		if(j == joueurActuel)
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
					if(joueurActuel == Joueur.X) affichage.setPrevisuBouton(true);
					else affichage.setPrevisuBouton(false);
				}
			}
		}
	}

	@Override
	protected void updateGrille() 
	{
		String message = "grille";
		message += "\t";
		for(int i=0 ; i<9 ; i++)
		{
			message += grille[i].toString() + ":";
		}
		envoyer(message);
		super.updateGrille();
	}

	@Override
	protected void updateMessage(String str) 
	{
		envoyer("message" + "\t" + str);
		super.updateMessage(str);
	}

	@Override
	protected void reset() 
	{
		envoyer("reset");
		super.reset();
	}

	@Override
	protected void menu()
	{
		envoyer("quitter");
		couperConnexion();
		super.menu();
	}
}



