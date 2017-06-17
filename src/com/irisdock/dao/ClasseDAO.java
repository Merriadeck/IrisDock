package com.irisdock.dao;

import java.util.List;

import com.irisdock.beans.Classe;
import com.irisdock.beans.Niveau;

public interface ClasseDAO {
	List<Classe> listClasses() throws DAOException;
	
	void addClasse(Classe classe) throws DAOException;
	
	void deleteClasse(Integer idClasse) throws DAOException;
	
	List<Niveau> listNiveaux() throws DAOException;
}
