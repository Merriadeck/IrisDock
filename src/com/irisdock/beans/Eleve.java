package com.irisdock.beans;

public class Eleve {
	private String login;
	private String nom;
	private String prenom;
	private String mail;
	private Classe classe;
	
	public Eleve() {
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	
	@Override
	public String toString()
	{
		String result = new String();
		
		result += "Informations élève :\n";
		result += this.prenom + " " + this.nom + "\n\n";
		result += "Login : " + this.login + "\n";
		if(this.mail != null)
			result += "Mail : " + this.mail + "\n";
		else
			result += "Mail non renseigné.";
		result += "Classe : " + this.classe.toString();
		
		return result;
	}
	
	
}
