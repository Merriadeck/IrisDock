package com.irisdock.beans;

public class Professeur {
	private String login;
	private String nom;
	private String prenom;
	private String mail;
	private String telephone;
	
	public Professeur() {
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Override
	public String toString()
	{
		String result = new String();
		
		result += "Informations professeur : \n";
		result += "M(me) " + this.nom + " " + this.prenom + "\n\n";
		result += "Login : " + this.login + "\n";
		result += "Mail : " + this.mail + "\n";
		if(this.telephone != null)
			result += "Téléphone : " + this.telephone + "\n";
		else
			result += "Téléphone non renseigné.";
			
		return result;
	}
	
}
