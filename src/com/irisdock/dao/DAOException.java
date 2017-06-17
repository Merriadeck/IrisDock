package com.irisdock.dao;

/**
 * Exception lev�e en cas d'erreur lors de la manipulation d'un DAO
 */
public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 3226272732868729635L;

	public DAOException (String message, Throwable reason) {
		super(message, reason);
	}
	
	public DAOException (String message) {
		super(message);
	}
	
	public DAOException (Throwable reason) {
		super(reason);
	}
}
