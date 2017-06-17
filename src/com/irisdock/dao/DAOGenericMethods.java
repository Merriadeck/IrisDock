package com.irisdock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * M�thodes g�n�riques pour les DAO
 * Contient les m�thodes de pr�paration de requ�te g�n�rique et les fermetures de ressources du DAO
 */
public class DAOGenericMethods {
	/**
	 * Prépare une requête générique à envoyer à la BDD
	 * @param connexion Objet de connexion vers la base
	 * @param request Requête SQL à préparer 
	 * @param returnGeneratedKeys Retourner ou non l'id généré
	 * @param params Paramètres à passer à la requête SQL
	 * @return Requête sous la forme d'un objet preparedStatement
	 * @throws SQLException
	 */
	public static PreparedStatement makePreparedStatement(Connection connexion, String request, boolean returnGeneratedKeys, Object... params) throws SQLException {
	    
		PreparedStatement preparedStatement = connexion.prepareStatement(request,returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    
	    for ( int i = 0; i < params.length; i++ ) {
	        preparedStatement.setObject( i + 1, params[i] );
	    }
	    
	    return preparedStatement;
	}
	
	/**
	 * Lib�re un ResultSet
	 * @param set ResultSet � lib�rer
	 */
	public static void closeResource(ResultSet set) {
		if(set != null)
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Lib�re un Statement
	 * @param statement Statement � lib�rer
	 */
	public static void closeResource(Statement statement) {
		if(statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Lib�re une connexion
	 * @param connexion Connexion � lib�rer
	 */
	public static void closeResource(Connection connexion) {
		if(connexion != null)
			try {
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Lib�re un statement puis une connexion
	 * @param statement Statement � lib�rer
	 * @param connexion Connexion � lib�rer
	 */
	public static void closeResource(Statement statement, Connection connexion) {
		closeResource(statement);
		closeResource(connexion);
	}
	
	/**
	 * Lib�re un ResultSet, un Statement puis une connexion
	 * @param set ResultSet � lib�rer
	 * @param statement Statement � lib�rer
	 * @param connexion Connexion � lib�rer
	 */
	public static void closeResource(ResultSet set, Statement statement, Connection connexion) {
		closeResource(set);
		closeResource(statement,connexion);
	}

}