package com.irisdock.dao;

import static com.irisdock.dao.DAOGenericMethods.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.irisdock.beans.Classe;
import com.irisdock.beans.Niveau;

public class ClasseDAOImpl implements ClasseDAO {
	private DAOFactory factory;
	
	private static final String REQUEST_LIST_CLASSES = "SELECT * FROM Classe INNER JOIN Niveau ON Classe.IdNiveau = Niveau.IdNiveau ORDER BY LabelNiveau ASC";
	private static final String REQUEST_CREATE_CLASSE = "INSERT INTO Classe(LabelClasse,IdNiveau) VALUES (?,?)";
	private static final String REQUEST_DELETE_CLASSE = "DELETE FROM Classe WHERE IdClasse = ?";
	private static final String REQUEST_LIST_NIVEAUX = "SELECT * FROM Niveau";
	
	public ClasseDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Classe> listClasses() throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Classe> listClasses = new ArrayList<Classe>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_LIST_CLASSES,false);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listClasses.add(toBean(set));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listClasses;
	}

	@Override
	public void addClasse(Classe classe) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		ResultSet idGenere = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_CREATE_CLASSE,true,classe.getLabel(),classe.getNiveau().getIdentifiant());
			retour = statement.executeUpdate();
			
			// Vérification que le retour de la requête est correct
			if(retour == 0)
				throw new DAOException("Erreur - La classe n'a pu être ajoutée.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(idGenere,statement,connexion);
		}
	}

	@Override
	public void deleteClasse(Integer idClasse) throws DAOException {
		Connection connexion = null;
		PreparedStatement statement = null;
		int retour = 0;
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_DELETE_CLASSE,false,idClasse);
			retour = statement.executeUpdate();

			// Vérification que l'indication de retour de la requête est correcte
			if(retour == 0)
				throw new DAOException("Erreur - Aucune classe n'a été supprimée.");
			else if(retour > 1)
				throw new DAOException("Erreur grave - Plus d'une classe a été supprimée !");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(statement,connexion);
		}
	}

	@Override
	public List<Niveau> listNiveaux() throws DAOException {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connexion = null;
		List<Niveau> listNiveaux = new ArrayList<Niveau>();
		
		try {
			// Récupération d'une connexion et exécution de la requête
			connexion = factory.getConnection();
			statement = makePreparedStatement(connexion,REQUEST_LIST_NIVEAUX,false);
			set = statement.executeQuery();
			
			// Récupération et stockage des résultats
			while(set.next())
				listNiveaux.add(toBeanNiveau(set));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResource(set,statement,connexion);
		}
		
		return listNiveaux;
	}
	
	private static Classe toBean(ResultSet set) throws SQLException {
		Niveau niveau = new Niveau();
		niveau.setIdentifiant(set.getInt("IdNiveau"));
		niveau.setLabel(set.getString("LabelNiveau"));
		
		Classe classe = new Classe();
		classe.setIdentifiant(set.getInt("IdClasse"));
		classe.setLabel(set.getString("LabelClasse"));
		classe.setNiveau(niveau);
		
		return classe;		
	}
	
	private static Niveau toBeanNiveau(ResultSet set) throws SQLException {
		Niveau niveau = new Niveau();
		niveau.setIdentifiant(set.getInt("IdNiveau"));
		niveau.setLabel(set.getString("LabelNiveau"));
		
		return niveau;
	}

}
