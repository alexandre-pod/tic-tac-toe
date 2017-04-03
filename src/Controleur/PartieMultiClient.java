package Controleur;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Affichage.AffConnexionServeur;
import Affichage.Fenetre;

public class PartieMultiClient extends Partie
{
	private Socket socket;
	private int port = 12345;

	private PrintWriter out;
	private BufferedReader in;

	private AffConnexionServeur affconnexionServ;
	private ConnectionServer connexionServ;

	public PartieMultiClient()
	{
		affconnexionServ = new AffConnexionServeur("Connexion au serveur", this);
		Fenetre.setAffichage(affconnexionServ);
		joueurActuel = Joueur.O;
	}

	class ConnectionServer extends Thread{
		String adresse;
		public ConnectionServer(String ip){
			adresse = ip;
		}
		public void run()
		{
			try {
				socket = new Socket(adresse, port);
				System.out.println("connecté");

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());

				// changement du titre de la fenetre eu de son contenu
				Fenetre.setAffichage(affichage);
				Fenetre.getFenetre().setTitle("Joueur O");
				affichage.setPrevisuBouton(false);

				while(true) // boucle ecoutant le serveur en continu
				{
					String message = in.readLine();
					if(message == ".") break;
					traitementMessage(message);
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception se) {}
		}
	}

	protected void traitementMessage(String mess) // traitement du message recu
	{
		System.out.println("<< " + mess + " >>");
		String[] message = mess.split("\t");

		if(message[0] != null)
		{
			switch(message[0])
			{
			case"message":
				if(message.length >= 2)
				{
					updateMessage(message[1]);
					switch(message[1])
					{
					case"Joueur Actuel : O":
						affichage.setPrevisuBouton(true);
						break;
					default:
						affichage.setPrevisuBouton(false);
						break;
					}
				}
				break;
			case"grille":
				if(message.length >= 2)
				{
					System.out.println((message[1].split(":"))[1] == "X");
					affichage.updateGrille(message[1].split(":"));
				}
				break;
			case"quitter":
				menu();
				break;
			case"reset":
				super.reset();
				joueurActuel = Joueur.O;
				break;
			}
		}
	}

	private void envoyer(String message) // fonction permettant de communiquer avec le serveur
	{
		if(out != null)
		{
			out.println(message);
			out.flush();
		}
	}

	private void fermerConnexion() // ferme toutes les connexions
	{
		try {
			if(socket != null) socket.close();
			if(in != null) in.close();
			if(out != null) out.close();
			if(connexionServ != null)connexionServ.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand() == "connexion")
		{
			connexionServ = new ConnectionServer(affconnexionServ.getIP());
			connexionServ.start();
		}
		super.actionPerformed(arg0);
	}

	@Override
	protected void caseCliquee(int pos) 
	{
		envoyer("jeu" + "\t" + String.valueOf(pos));
	}

	@Override
	protected void reset() {}

	@Override
	protected void menu() {
		envoyer("quitter");
		fermerConnexion();
		super.menu();
	}
}
