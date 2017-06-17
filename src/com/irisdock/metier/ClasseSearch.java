package com.irisdock.metier;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.irisdock.dao.DAOFactory;

public class ClasseSearch {
	public static void invoke(DAOFactory factory)
	{
		HashMap<String,Integer> mapClasses = GenericMethods.getClasses(factory.getClasseDAO());
		String[] labelsClasses = mapClasses.keySet().toArray(new String[0]);
		
		String messageClasses = new String();
		for(String curClasse : labelsClasses)
			messageClasses += curClasse + "\n";
		
		JOptionPane.showMessageDialog(null, messageClasses, "Liste des classes", JOptionPane.INFORMATION_MESSAGE);
	}
}
