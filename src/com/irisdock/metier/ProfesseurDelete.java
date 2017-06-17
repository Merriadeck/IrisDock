package com.irisdock.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import com.irisdock.beans.Professeur;
import com.irisdock.dao.DAOException;
import com.irisdock.dao.DAOFactory;
import com.irisdock.dao.ProfDAO;

public class ProfesseurDelete {
	public static void invoke(DAOFactory factory)
	{
		// Sélection du professeur
		HashMap<String,String> mapProfesseurs = getProfesseurs(factory.getProfDAO());
		String[] labelsProfesseurs = mapProfesseurs.keySet().toArray(new String[0]);
		
		String answerProfesseur = null;
		answerProfesseur = (String) JOptionPane.showInputDialog(null,
				"Sélectionner le professeur à supprimer",
				"Sélection du professeur",
				JOptionPane.QUESTION_MESSAGE,
				null,
				labelsProfesseurs,
				labelsProfesseurs[0]);
		
		String loginProfesseur = mapProfesseurs.get(answerProfesseur);
		factory.getProfDAO().deleteProf(loginProfesseur);
	}
	
	private static HashMap<String,String> getProfesseurs(ProfDAO dao)
	{
		List<Professeur> professeurs = new ArrayList<Professeur>();
		professeurs = dao.listProfesseurs();
		
		if (professeurs == null)
			throw new DAOException("Erreur lors de la récupération des professeurs.");
		
		HashMap<String,String> mapClasses = new HashMap<String,String>(); // Map contenant (label complet,login) des professeurs
		
		for(Professeur curProf : professeurs)
			mapClasses.put(curProf.getPrenom() + " " + curProf.getNom(),curProf.getLogin());
		
		return mapClasses;
	}
}
