package com.irisdock.dao;

/**
 * Exception lev�e en cas d'erreur lors de l'initialisation du DAO ou de l'acc�s � la base
 */
public class DAOConfException extends RuntimeException {
	private static final long serialVersionUID = -7001744162275911177L;

	public DAOConfException (String message, Throwable reason) {
		super(message, reason);
	}
	
	public DAOConfException (String message) {
		super(message);
	}
	
	public DAOConfException (Throwable reason) {
		super(reason);
	}
}
