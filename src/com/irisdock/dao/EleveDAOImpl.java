package com.irisdock.dao;

import static com.irisdock.dao.DAOGenericMethods.*;
import com.irisdock.dao.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.irisdock.beans.Classe;
import com.irisdock.beans.Eleve;
import com.irisdock.beans.Niveau;

public class EleveDAOImpl implements EleveDAO {
	private DAOFactory factory;
	
	private static final String REQUEST_CREATE_ELEVE = "INSERT INTO Eleve VALUES (?,?,?,?,?,?)";
	private static final String REQUEST_UPDATE_ELEVE = "UPDATE Eleve SET NomEleve = ?,PrenomEleve = ?,MailEleve = ?,IdClasse = ? WHERE LoginEleve = ?";
	private static final String REQUEST_CHANGEPASSWORD = "UPDATE Eleve SET PassEleve = ? WHERE LoginEleve = ?";
	private static final String REQUEST_DELETE_ELEVE = "DELETE FROM Eleve WHERE LoginEleve = ?";
	private static final String REQUEST_FIND_ELEVE = "SELECT * FROM Eleve INNER JOIN Classe ON Eleve.IdClasse = Classe.IdClasse INNER JOIN Niveau ON Classe.IdNiveau = Niveau.IdNiveau WHERE LoginEleve = ?";
	private static final String REQUEST_GET_PASSWORD = "SELECT passEleve FROM Eleve WHERE LoginEleve = ?";
	private static final String REQUEST_LIST_BYCLASSE = "SELECT * FROM Eleve INNER JOIN Classe ON Eleve.IdClasse = Classe.IdClasse INNER JOIN Niveau ON Classe.IdNiveau = Niveau.IdNiveau WHERE Eleve.IdClasse = ?";
	
	public EleveDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public void addEleve(Eleve eleve, String password) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet idGenere = null;
		int retour = 0;
		
		String login;
		if(eleve.getNom().length() > 29)
			login = eleve.getPrenom().charAt(0) + eleve.getNom().substring(0, 29);
		else
			login = eleve.getPrenom().charAt(0) + eleve.getNom();
		login = login.toLowerCase();
		password = hashPassword(password,login);
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CREATE_ELEVE,true,login,eleve.getNom(),eleve.getPrenom(),eleve.getMail(),password,eleve.getClasse().getIdentifiant());
			retour = statement.executeUpdate();
			
			// Vérification que le retour de la requête est correct
			if(retour == 0)
				throw new DAOException("Erreur - L'élève n'a pu être ajouté.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(idGenere,statement,connexion);
		}
	}

	@Override
	public void editEleve(Eleve eleve) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requéte
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_UPDATE_ELEVE,false,eleve.getNom(),eleve.getPrenom(),eleve.getMail(),eleve.getClasse().getIdentifiant(),eleve.getLogin());
			retour = statement.executeUpdate();
			
			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun élève n'a été mis à jour.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un élève à été mis à jour !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public void changePassword(String loginEleve, String newPassword) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		String password = hashPassword(newPassword,loginEleve);
		
		try {
			// Récupération d'une connexion et exécution de la requéte
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CHANGEPASSWORD,false,password,loginEleve);
			retour = statement.executeUpdate();
			
			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun élève n'a été mis à jour.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un élève à été mis à jour !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public void deleteEleve(String loginEleve) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_DELETE_ELEVE,false,loginEleve);
			retour = statement.executeUpdate();

			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun élève n'a été supprimé.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un élève a été supprimé !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public Eleve findEleve(String loginEleve) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		Eleve eleve = null;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_FIND_ELEVE,false,loginEleve);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats dans l'objet retourné
			if(set.next())
				eleve = toBean(set);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
	
		return eleve;
	}

	@Override
	public boolean checkPassword(String loginEleve, String password) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		
		password = hashPassword(password,loginEleve);
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_GET_PASSWORD,false,loginEleve);
			set = statement.executeQuery();
			
			// Récupération du résultat et vérification de la correspondance des mots de passe
			if(set.next()) {
				return password.equals(set.getString("passEleve"));
			} else
				throw new DAOException("Erreur - Identifiant introuvable.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		} 
	}
	

	@Override
	public List<Eleve> listByClasse(Integer idClasse) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Eleve> listEleves = new ArrayList<Eleve>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_LIST_BYCLASSE,false,idClasse);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listEleves.add(toBean(set));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listEleves;
	}
	
	/**
	 * Convertit une ligne de la base en bean Eleve
	 * @param set Set contenant les informations de l'élève récupéré dans la base
	 * @return Bean représentant l'élève
	 * @throws SQLException En cas d'erreur liée à la manipulation du ResultSet
	 */
	private static Eleve toBean(ResultSet set) throws SQLException {
		Niveau niveau = new Niveau();
		niveau.setIdentifiant(set.getInt("IdNiveau"));
		niveau.setLabel(set.getString("LabelNiveau"));
		
		Classe classe = new Classe();
		classe.setIdentifiant(set.getInt("IdClasse"));
		classe.setLabel(set.getString("LabelClasse"));
		classe.setNiveau(niveau);
		
		Eleve eleve = new Eleve();
		eleve.setLogin(set.getString("LoginEleve"));
		eleve.setMail(set.getString("MailEleve"));
		eleve.setNom(set.getString("NomEleve"));
		eleve.setPrenom(set.getString("PrenomEleve"));
		eleve.setClasse(classe);
		
		return eleve;
	}
	
	private static String hashPassword(String password, String login) {
		password = password + password.length() + login;
		password = Hashing.sha512().hashString(password, Charsets.UTF_8).toString();
		return password;
	}

}
