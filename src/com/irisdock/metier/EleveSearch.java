package com.irisdock.metier;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.irisdock.beans.Eleve;
import com.irisdock.dao.DAOFactory;

public class EleveSearch {
	public static void byClasse(DAOFactory factory)
	{
		// Sélection de la classe
				HashMap<String,Integer> mapClasses = GenericMethods.getClasses(factory.getClasseDAO());
				String[] labelsClasses = mapClasses.keySet().toArray(new String[0]);
				
				if(! mapClasses.isEmpty())
				{
					String answerClasses = null;
					answerClasses = (String) JOptionPane.showInputDialog(null,
							"Sélectionner la classe de l'élève à supprimer",
							"Sélection de la classe",
							JOptionPane.QUESTION_MESSAGE,
							null,
							labelsClasses,
							labelsClasses[0]);

					Integer idClasse = mapClasses.get(answerClasses);
					
					// Sélection de l'élève
					HashMap<String,String> mapEleves = GenericMethods.getEleves(factory.getEleveDAO(),idClasse);
					String[] labelsEleves = mapEleves.keySet().toArray(new String[0]);
					
					if(! mapEleves.isEmpty())
					{
						String answerEleve = null;
						answerEleve = (String) JOptionPane.showInputDialog(null,
								"Sélectionner l'élève à supprimer",
								"Sélection de l'élève",
								JOptionPane.QUESTION_MESSAGE,
								null,
								labelsEleves,
								labelsEleves[0]);
						
						if(answerEleve != null)
						{
							String loginEleve = mapEleves.get(answerEleve);
							
							Eleve selectEleve = factory.getEleveDAO().findEleve(loginEleve);
							
							afficherEleve(selectEleve);
						}
					}
					else
						GenericMethods.warningPane("Aucun élève dans la classe sélectionnée.");
					
				}
				else
					GenericMethods.warningPane("Aucune classe disponible.");
	}
	
	public static void byLogin(DAOFactory factory)
	{
		String inputLogin = null;
		
		inputLogin = JOptionPane.showInputDialog(null,"Indiquez le login de l'élève à rechercher : ","Recherche par login",JOptionPane.QUESTION_MESSAGE);
		try
		{
			GenericMethods.validate(inputLogin);
			
			Eleve selectEleve = factory.getEleveDAO().findEleve(inputLogin);
			if(selectEleve != null)
				afficherEleve(selectEleve);
			else
				GenericMethods.warningPane("Aucun élève trouvé avec ce login.");
		} catch (InputException e) {
			GenericMethods.warningPane(e.getMessage());
		}
		
		
	}
	
	/**
	 * Affiche les détails d'un élève dans une boîte de dialogue
	 * @param selectEleve L'élève pour lequel afficher les détails
	 */
	private static void afficherEleve(Eleve selectEleve)
	{
		JOptionPane.showMessageDialog(null, selectEleve.toString(), "Informations Eleve",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
