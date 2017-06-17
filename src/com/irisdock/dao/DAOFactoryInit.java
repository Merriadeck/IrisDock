package com.irisdock.dao;

public class DAOFactoryInit {
	
	public static void destroy()
	{
		DAOFactory.closePool();
	}
	
	public static DAOFactory create()
	{
		DAOFactory daoFactory = DAOFactory.getInstance();
		return daoFactory;
	}

}
