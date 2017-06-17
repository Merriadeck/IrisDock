package com.irisdock.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * Objet Factory pour la gestion des connexions à la base et des DAO
 */
public class DAOFactory {
	/**
	 * Emplacement du fichier de propriétés de connexion à la BDD
	 */
	public static final String FILE_PROPERTIES = "com/irisdock/dao/dao.properties";
	/**
	 * Clé correspondant au chemin d'accès à la BDD 
	 */
	public static final String PROP_DBURL = "url";
	/**
	 * Clé correspondant au driver utilisé pour l'accès à la BDD 
	 */
	public static final String PROP_DBDRIVER = "driver";
	/**
	 * Clé correspondant au login utilisé pour l'accès à la BDD
	 */
	public static final String PROP_DBUSER = "user";
	/**
	 * Clé correspondant au mot de passe lié au login
	 */
	public static final String PROP_DBPWD = "pwd";
	private static BoneCP connectionPool = null;
	
	/**
	 * Constructeur de la Factory - ne pas appeler directement, utiliser getInstance() à la place !
	 * @param connectionPool Pool de connection à utiliser
	 */
	private DAOFactory(BoneCP connectionPool) {
		DAOFactory.connectionPool = connectionPool;
	}
	
	/**
	 * Récupère les propriétés de la Factory et génère une instance de celle-ci
	 * @return Une instance de la DAOFactory
	 * @throws DAOConfException Erreur liée à la configuration ou l'absence d'un élément requis pour charger la factory (propriétés, driver)
	 */
	public static DAOFactory getInstance() throws DAOConfException {
		Properties properties = new Properties();
		String dbUrl = null;
		String dbDriver = null;
		String dbUser = null;
		String dbPwd = null;
		
		/**
		 * Stream du fichier de propri�t�s de la connexion
		 */
		InputStream fileProperties = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PROPERTIES);
		
		// Vérification que le fichier de configuration existe
		if(fileProperties == null)
			throw new DAOConfException("Erreur DAO - Le fichier de configuration est introuvable.");
		
		// Chargement des propriétés de connexion � la BDD
		try {
			properties.load(fileProperties);
			dbUrl = properties.getProperty(PROP_DBURL);
			dbDriver = properties.getProperty(PROP_DBDRIVER);
			dbUser = properties.getProperty(PROP_DBUSER);
			dbPwd = properties.getProperty(PROP_DBPWD);
		} catch (IOException e1) {
			throw new DAOConfException("Impossible de charger les propriétés de connexion à la BDD",e1);
		}
		
		// Chargement du driver de la BDD
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfException("Le driver n'a pu être chargé.", e);
		}
		
		try {
			BoneCPConfig boneConfig = new BoneCPConfig();
			boneConfig.setJdbcUrl(dbUrl);
			boneConfig.setUsername(dbUser);
			boneConfig.setPassword(dbPwd);
			boneConfig.setPartitionCount(5);
			boneConfig.setMinConnectionsPerPartition(3);
			boneConfig.setMaxConnectionsPerPartition(10);
			
			connectionPool = new BoneCP(boneConfig);
		} catch (SQLException e) {
			throw new DAOConfException("Impossible d'initialiser le connection pool.", e);
		}
		
		// Si tout s'est bien passé, instantiation et retour de la Factory
		return (new DAOFactory(connectionPool)); 
	}
	
	public static void closePool() {
		if(connectionPool != null)
			connectionPool.shutdown();
	}
	
	/**
	 * Méthode de connexion à la BDD
	 * @return Objet 'Connection' pour l accès à la BDD
	 * @throws SQLException
	 */
	Connection getConnection() throws SQLException{
		Connection connexion = connectionPool.getConnection();
		return connexion;
	}
	
	/**
	 * Récupération de l'implémentation du DAO Eleve
	 * @return Une instance de l'implémentation du DAO concerné
	 */
	public EleveDAO getEleveDAO() {
		return new EleveDAOImpl(this);
	}
	
	/**
	 * Récupération de l'implémentation du DAO Professeur
	 * @return Une instance de l'implémentation du DAO concerné
	 */
	public ProfDAO getProfDAO() {
		return new ProfDAOImpl(this);
	}
	
	/**
	 * Récupération de l'implémentation du DAO Classe
	 * @return Une instance de l'implémentation du DAO concerné
	 */
	public ClasseDAO getClasseDAO() {
		return new ClasseDAOImpl(this);
	}
}
