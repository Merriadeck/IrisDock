package com.irisdock.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import com.irisdock.beans.Classe;
import com.irisdock.beans.Eleve;
import com.irisdock.beans.Niveau;
import com.irisdock.dao.ClasseDAO;
import com.irisdock.dao.DAOException;
import com.irisdock.dao.EleveDAO;

public class GenericMethods {
	protected static final String REGEX_SAFEINPUT = "[1-zA-Z0-9@.\\éèêëàâäôöûüù*=+_ ]*";
	protected static final String REGEX_PHONENUMBER = "[0-9+]*";
	
	/**
	 * Affiche une boîte de dialogue d'avertissement pour cause de recherche vide.
	 * @param message Le message à afficher
	 */
	public static void warningPane(String message)
	{
		JOptionPane.showMessageDialog(null,message,"Opération annulée",
				JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Récupération d'une map des classes de l'école
	 * @param dao La ClasseDAO fournie par la factory
	 * @return Une map contenant les classes sous la forme HashMap(LabelComplet,IdentifiantInterne)
	 */
	protected static HashMap<String,Integer> getClasses(ClasseDAO dao)
	{
		List<Classe> classes = new ArrayList<Classe>();
		classes = dao.listClasses();
		
		if (classes == null)
			throw new DAOException("Erreur lors de la récupération des classes.");
		
		HashMap<String,Integer> mapClasses = new HashMap<String,Integer>(); // Map contenant (label complet,identifiant interne) des classes
		
		for(Classe curClasse : classes)
			mapClasses.put(curClasse.toString(),curClasse.getIdentifiant());
		
		return mapClasses;
	}
	
	/**
	 * Récupération d'une map des élèves d'une classe
	 * @param dao L'EleveDAO fournie par la factory
	 * @param idClasse L'identifiant de la classe pour laquelle chercher les élèves
	 * @return Une map contenant les élèves sous la forme HashMap(labelComplet,loginEleve)
	 */
	protected static HashMap<String,String> getEleves(EleveDAO dao, Integer idClasse)
	{
		List<Eleve> eleves = new ArrayList<Eleve>();
		eleves = dao.listByClasse(idClasse);
		
		if(eleves == null)
			throw new DAOException("Erreur lors de la récupération des élèves.");
		
		HashMap<String,String> mapEleves = new HashMap<String,String>(); // Map contenant (label complet,login eleve) des élèves
		
		for(Eleve curEleve : eleves)
		{
			if(curEleve.getMail() != null)
				mapEleves.put(curEleve.getPrenom() + " " + curEleve.getNom() + " (" + curEleve.getMail() + ")", curEleve.getLogin());
			else
				mapEleves.put(curEleve.getPrenom() + " " + curEleve.getNom(), curEleve.getLogin());
		}
		
		return mapEleves;
			
	}
	
	/**
	 * Récupération d'une map des niveaux de l'école
	 * @param dao La ClasseDAO fournie par la factory
	 * @return Une map contenant les niveaux sous la forme HashMap(LabelComplet,IdentifiantInterne)
	 */
	protected static HashMap<String,Integer> getNiveaux(ClasseDAO dao)
	{
		List<Niveau> niveaux = new ArrayList<Niveau>();
		niveaux = dao.listNiveaux();
		
		if (niveaux == null)
			throw new DAOException("Erreur lors de la récupération des niveaux.");
		
		HashMap<String,Integer> mapNiveaux = new HashMap<String,Integer>(); // Map contenant (label complet,identifiant interne) des classes
		
		for(Niveau curNiveau : niveaux)
			mapNiveaux.put(curNiveau.toString(),curNiveau.getIdentifiant());
		
		return mapNiveaux;
	}
	
	protected static void checkNotNull(Object input) throws InputException
	{
		if(input == null)
			throw new InputException("Champ non renseigné.");
	}
	
	protected static void checkSafeString(String input) throws InputException
	{
		if(! input.matches(REGEX_SAFEINPUT))
			throw new InputException("Le champ contient des caractères interdits.");
	}
	
	/**
	 * Lève une exception (non bloquante) si l'entrée est vide ou illégale
	 * @param input L'entrée à vérifier
	 * @throws InputException Exception non bloquante (contenant en message les détails du problème).
	 */
	protected static void validate(String input) throws InputException
	{
		checkNotNull(input);
		checkSafeString(input);
	}
	
	/**
	 * Lève une exception (non bloquante) si l'entrée ne correspond pas à un potentiel numéro de téléphone
	 * (Ne vérifie pas la longueur du numéro de téléphone)
	 * @param input L'entrée à vérifier
	 * @throws InputException Exception non bloquante (contenant en message les détails du problème).
	 */
	protected static void validatePhoneNumber(String input) throws InputException
	{
		if(! input.matches(REGEX_PHONENUMBER))
			throw new InputException("Le champ contient des caractères interdits.");
	}
}
