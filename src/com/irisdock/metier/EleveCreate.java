package com.irisdock.metier;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.irisdock.beans.Classe;
import com.irisdock.beans.Eleve;
import com.irisdock.dao.ClasseDAO;
import com.irisdock.dao.DAOException;
import com.irisdock.dao.DAOFactory;
import com.irisdock.dao.EleveDAO;

public class EleveCreate {

	public static void invoke(DAOFactory factory)
	{	
		Eleve eleve = new Eleve();
		String password = null;
		
		try {
			eleve.setNom(queryString("nom"));
			eleve.setPrenom(queryString("prenom"));
			eleve.setMail(queryString("mail"));
			password = queryString("mot de passe");
			eleve.setClasse(queryClasse(factory.getClasseDAO()));
		} catch (InputException e) {
			GenericMethods.warningPane(e.getMessage());
		}
		
		if(eleve.getClasse() != null)
			try {
				EleveDAO eleveDAO = factory.getEleveDAO();
				
				eleveDAO.addEleve(eleve, password);
				Eleve addedEleve = eleveDAO.findEleve(generateLogin(eleve));
				
				JOptionPane.showMessageDialog(null,addedEleve.toString() + "\n\nMot de passe : " + password,"Informations de l'élève ajouté :", JOptionPane.INFORMATION_MESSAGE);
			} catch (DAOException e) {
				System.err.println(e.getMessage());
				GenericMethods.warningPane("Erreur - Un élève existe déjà probablement avec ces informations.");
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
		
		input = JOptionPane.showInputDialog(null,"Indiquez le " + label + " de l'élève : ","Ajout d'élève",JOptionPane.QUESTION_MESSAGE);
		
		GenericMethods.validate(input);
			
		return input;
	}
	
	/**
	 * Réclame un choix de classe par le biais d'une boîte de dialogue
	 * @return La classe sélectionnée par l'utilisateur
	 */
	private static Classe queryClasse(ClasseDAO dao)
	{
		HashMap<String,Integer> mapClasses = GenericMethods.getClasses(dao);
		String[] labelsClasses = mapClasses.keySet().toArray(new String[0]);
		
		if(! mapClasses.isEmpty()) {
			String answerClasses = null;
			answerClasses = (String) JOptionPane.showInputDialog(null,
					"Sélectionner la classe de l'élève à ajouter",
					"Ajout d'élève",
					JOptionPane.QUESTION_MESSAGE,
					null,
					labelsClasses,
					labelsClasses[0]);

			Integer idClasse = mapClasses.get(answerClasses);
			
			Classe inputClasse = new Classe();
			inputClasse.setIdentifiant(idClasse);
			inputClasse.setLabel(answerClasses);
			
			return inputClasse;
		} else
			return null;
	}
	
	/**
	 * Calcule le login avec lequel l'élève sera créé
	 * @param eleve L'objet élève pour lequel calculer le login
	 * @return Le login de l'élève
	 */
	private static String generateLogin(Eleve eleve)
	{
		String login;
		if(eleve.getNom().length() > 29)
			login = eleve.getPrenom().charAt(0) + eleve.getNom().substring(0, 29);
		else
			login = eleve.getPrenom().charAt(0) + eleve.getNom();
		
		return login;
	}
}
