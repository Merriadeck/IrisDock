package com.irisdock.metier;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.irisdock.beans.Classe;
import com.irisdock.beans.Niveau;
import com.irisdock.dao.ClasseDAO;
import com.irisdock.dao.DAOException;
import com.irisdock.dao.DAOFactory;

public class ClasseCreate {
	public static void invoke(DAOFactory factory)
	{
		Classe classe = new Classe();
		
		try {
			classe.setLabel(queryString("nom"));
			classe.setNiveau(queryNiveau(factory.getClasseDAO()));
		} catch (InputException e) {
			GenericMethods.warningPane(e.getMessage());
		}
		
		if(classe.getNiveau() != null)
			try {
				factory.getClasseDAO().addClasse(classe);
				
				JOptionPane.showMessageDialog(null,classe.toString(),"Informations de la classe ajoutée :", JOptionPane.INFORMATION_MESSAGE);
			} catch (DAOException e) {
				System.err.println(e.getMessage());
				GenericMethods.warningPane("Erreur - Une classe existe déjà probablement avec ces informations.");
			}
	}
	
	/**
	 * Réclame une saisie de String par le biais d'une boîte de dialogue et effectue une vérification de base dessus.
	 * @param label L'intitulé de la String à demander
	 * @return La String saisie par l'utilisateur, une fois validée
	 */
	private static String queryString(String label) throws InputException {
		String input = null;
		
		input = JOptionPane.showInputDialog(null,"Indiquez le " + label + " de la classe : ","Ajout de classe",JOptionPane.QUESTION_MESSAGE);
		
		GenericMethods.validate(input);
			
		return input;
	}
	
	private static Niveau queryNiveau(ClasseDAO dao) throws InputException {
		HashMap<String,Integer> mapNiveaux = GenericMethods.getNiveaux(dao);
		String[] labelsNiveaux = mapNiveaux.keySet().toArray(new String[0]);
		
		if(! mapNiveaux.isEmpty()) {
			String answerNiveaux = null;
			answerNiveaux = (String) JOptionPane.showInputDialog(null,
					"Sélectionner le niveau de la classe à ajouter",
					"Ajout de classe",
					JOptionPane.QUESTION_MESSAGE,
					null,
					labelsNiveaux,
					labelsNiveaux[0]);

			Integer idNiveau = mapNiveaux.get(answerNiveaux);
			
			Niveau inputNiveau = new Niveau();
			inputNiveau.setIdentifiant(idNiveau);
			inputNiveau.setLabel(answerNiveaux);
			
			return inputNiveau;
		} else
			return null;
	}
}
