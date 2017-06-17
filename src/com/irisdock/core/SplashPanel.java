package com.irisdock.core;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SplashPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		try {
			Image splashImage = ImageIO.read(new File("splash.png"));
			g.drawImage(splashImage, 0, 0, this);
		} catch (IOException e) {
			System.err.println("Erreur - Chargement du splash.");
			e.printStackTrace();
		}
	
	}
	
}
