package com.irisdock.core;

import com.irisdock.dao.DAOFactory;
import com.irisdock.dao.DAOFactoryInit;

public class Main {

	public static void main(String[] args) {
		DAOFactory factory = DAOFactoryInit.create();
		
		Window coreWindow = new Window(factory);
		coreWindow.setContentPane(new SplashPanel());
		
		while(true) {
			
		}
	}

}
