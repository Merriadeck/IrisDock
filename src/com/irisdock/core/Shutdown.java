package com.irisdock.core;

import com.irisdock.dao.DAOFactoryInit;

@SuppressWarnings("unused")
public class Shutdown {
	
	/**
	 * Termine proprement l'exécution du programme, en fermant le pool de connexion à la BDD.
	 */
	public static void nice()
	{
		// DAOFactoryInit.destroy();
		System.exit(0);
	}
}
