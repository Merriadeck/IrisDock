package com.irisdock.metier;

import java.util.HashMap;
import javax.swing.JOptionPane;
import com.irisdock.dao.DAOFactory;

public class EleveDelete {
	
	
	public static void invoke(DAOFactory factory)
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
					
					factory.getEleveDAO().deleteEleve(loginEleve);
				}
			}
			else
				GenericMethods.warningPane("Aucun élève dans la classe sélectionnée.");
			
		}
		else
			GenericMethods.warningPane("Aucune classe disponible.");
		
	}
	
}