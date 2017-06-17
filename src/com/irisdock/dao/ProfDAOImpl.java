package com.irisdock.dao;

import static com.irisdock.dao.DAOGenericMethods.closeResource;
import static com.irisdock.dao.DAOGenericMethods.makePreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.irisdock.beans.Professeur;

public class ProfDAOImpl implements ProfDAO {
	private DAOFactory factory = null;
	
	private static final String REQUEST_CREATE_PROFESSEUR = "INSERT INTO Professeur VALUES (?,?,?,?,?,?)";
	private static final String REQUEST_UPDATE_PROFESSEUR = "UPDATE Professeur SET NomProfesseur = ?,PrenomProfesseur = ?,MailProfesseur = ?,TelProfesseur = ? WHERE LoginProfesseur = ?";
	private static final String REQUEST_CHANGEPASSWORD = "UPDATE Professeur SET PassProfesseur = ? WHERE LoginProfesseur = ?";
	private static final String REQUEST_DELETE_PROFESSEUR = "DELETE FROM Professeur WHERE LoginProfesseur = ?";
	private static final String REQUEST_FIND_PROFESSEUR = "SELECT * FROM Professeur WHERE LoginProfesseur = ?";
	private static final String REQUEST_GET_PASSWORD = "SELECT passProfesseur FROM Professeur WHERE LoginProfesseur = ?";
	private static final String REQUEST_LIST_PROFESSEUR = "SELECT * FROM Professeur";
	
	public ProfDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public void addProf(Professeur professeur, String password) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet idGenere = null;
		int retour = 0;
		
		String login;
		if(professeur.getNom().length() > 29)
			login = professeur.getPrenom().charAt(0) + professeur.getNom().substring(0, 29);
		else
			login = professeur.getPrenom().charAt(0) + professeur.getNom();
		login = login.toLowerCase();
		password = hashPassword(password,login);
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CREATE_PROFESSEUR,true,login,professeur.getNom(),professeur.getPrenom(),professeur.getMail(),professeur.getTelephone(),password);
			retour = statement.executeUpdate();
			
			// Vérification que le retour de la requête est correct
			if(retour == 0)
				throw new DAOException("Erreur - Le professeur n'a pu être ajouté.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(idGenere,statement,connexion);
		}
	}

	@Override
	public void editProf(Professeur professeur) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requéte
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_UPDATE_PROFESSEUR,false,professeur.getNom(),professeur.getPrenom(),professeur.getMail(),professeur.getTelephone(),professeur.getLogin());
			retour = statement.executeUpdate();
			
			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun professeur n'a été mis à jour.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un professeur à été mis à jour !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public void changePassword(String loginProfesseur, String newPassword) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		String password = hashPassword(newPassword,loginProfesseur);
		
		try {
			// Récupération d'une connexion et exécution de la requéte
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CHANGEPASSWORD,false,password,loginProfesseur);
			retour = statement.executeUpdate();
			
			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun professeur n'a été mis à jour.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un professeur à été mis à jour !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public void deleteProf(String loginProfesseur) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_DELETE_PROFESSEUR,false,loginProfesseur);
			retour = statement.executeUpdate();

			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucun professeur n'a été supprimé.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'un professeur a été supprimé !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public Professeur findProfesseur(String loginProfesseur) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		Professeur prof = null;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_FIND_PROFESSEUR,false,loginProfesseur);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats dans l'objet retourné
			if(set.next())
				prof = toBean(set);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
	
		return prof;
	}

	@Override
	public boolean checkPassword(String loginProfesseur, String password) throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		
		password = hashPassword(password,loginProfesseur);
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_GET_PASSWORD,false,loginProfesseur);
			set = statement.executeQuery();
			
			// Récupération du résultat et vérification de la correspondance des mots de passe
			if(set.next()) {
				return password.equals(set.getString("passProfesseur"));
			} else
				throw new DAOException("Erreur - Identifiant introuvable.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
	}

	@Override
	public List<Professeur> listProfesseurs() throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Professeur> listProfesseurs = new ArrayList<Professeur>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_LIST_PROFESSEUR,false);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listProfesseurs.add(toBean(set));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listProfesseurs;
	}

	private Professeur toBean(ResultSet set) throws SQLException {
		Professeur prof = new Professeur();
		prof.setLogin(set.getString("LoginProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setNom(set.getString("NomProfesseur"));
		prof.setPrenom(set.getString("PrenomProfesseur"));
		prof.setMail(set.getString("MailProfesseur"));
		prof.setTelephone(set.getString("TelProfesseur"));
		
		return prof;
	}
	
	private static String hashPassword(String password, String login) {
		password = password + password.length() + login;
		password = Hashing.sha512().hashString(password, Charsets.UTF_8).toString();
		return password;
	}
}
