package com.irisdock.metier;

import java.util.HashMap;
import javax.swing.JOptionPane;
import com.irisdock.dao.DAOFactory;

public class ClasseDelete {
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
			
			if(answerClasses != null)
			{
				Integer idClasse = mapClasses.get(answerClasses);
				
				factory.getClasseDAO().deleteClasse(idClasse);	
			}
		}
		else
			GenericMethods.warningPane("Aucune classe disponible.");
	}
}
