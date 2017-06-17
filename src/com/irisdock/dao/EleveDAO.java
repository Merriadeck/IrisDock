package com.irisdock.dao;

import java.util.List;

import com.irisdock.beans.Eleve;

public interface EleveDAO {
	void addEleve(Eleve eleve, String password) throws DAOException;
	
	void editEleve(Eleve eleve) throws DAOException;
	
	void changePassword(String loginEleve, String newPassword) throws DAOException;
	
	void deleteEleve(String loginEleve) throws DAOException;
	
	Eleve findEleve(String loginEleve) throws DAOException;
	
	boolean checkPassword(String loginEleve, String password) throws DAOException;
	
	List<Eleve> listByClasse(Integer idClasse) throws DAOException;
}
