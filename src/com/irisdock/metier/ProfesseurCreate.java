package com.irisdock.metier;

import javax.swing.JOptionPane;

import com.irisdock.beans.Professeur;
import com.irisdock.dao.DAOException;
import com.irisdock.dao.DAOFactory;
import com.irisdock.dao.ProfDAO;

public class ProfesseurCreate {
	public static void invoke(DAOFactory factory)
	{
		Professeur professeur = new Professeur();
		String password = null;
		
		
		try {
			professeur.setNom(queryString("nom"));
			professeur.setPrenom(queryString("prénom"));
			professeur.setMail(queryString("mail"));
			professeur.setTelephone(queryTelephone());
			password = queryString("mot de passe");
		} catch (InputException e) {
			GenericMethods.warningPane(e.getMessage());
		}
		
		if(password != null)
		{
			if(professeur.getTelephone() == null)
				professeur.setTelephone("NULL");
			try {
				ProfDAO profDAO = factory.getProfDAO();
				
				profDAO.addProf(professeur, password);
				Professeur addedProfesseur = profDAO.findProfesseur(generateLogin(professeur));
				
				JOptionPane.showMessageDialog(null,addedProfesseur.toString() + "\n\nMot de passe : " + password,"Informations du professeur ajouté :",JOptionPane.INFORMATION_MESSAGE);
			} catch (DAOException e) {
				System.err.println(e.getMessage());
				GenericMethods.warningPane("Erreur - Un professeur existe déjà probablement avec ces informations.");
			}
		}
		
	}
	
	/**
	 * Réclame une saisie de String par le biais d'une boîte de dialogue et effectue une vérification de base dessus.
	 * @param label L'intitulé de la String à demander
	 * @return La String saisie par l'utilisateur, une fois validée
	 */
	private static String queryString(String label) throws InputException
	{
		String input = null;
		
		input = JOptionPane.showInputDialog(null,"Indiquez le " + label + " du professeur : ","Ajout de professeur",JOptionPane.QUESTION_MESSAGE);
		
		GenericMethods.validate(input);
			
		return input;
	}
	
	private static String queryTelephone() throws InputException
	{
		String input = null;
				
		input = JOptionPane.showInputDialog(null,"Indiquez le numéro de téléphone du professeur : ","Ajout de professeur",JOptionPane.QUESTION_MESSAGE);
		
		GenericMethods.validatePhoneNumber(input);
		
		return input;
	}
	
	/**
	 * Calcule le login avec lequel le professeur sera créé
	 * @param professeur L'objet professeur pour lequel calculer le login
	 * @return Le login du professeur
	 */
	private static String generateLogin(Professeur professeur)
	{
		String login;
		if(professeur.getNom().length() > 29)
			login = professeur.getPrenom().charAt(0) + professeur.getNom().substring(0, 29);
		else
			login = professeur.getPrenom().charAt(0) + professeur.getNom();
		
		return login;
	}
}
