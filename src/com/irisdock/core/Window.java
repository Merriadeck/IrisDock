package com.irisdock.core;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.irisdock.dao.DAOFactory;
import com.irisdock.metier.ClasseCreate;
import com.irisdock.metier.ClasseDelete;
import com.irisdock.metier.ClasseSearch;
import com.irisdock.metier.EleveCreate;
import com.irisdock.metier.EleveDelete;
import com.irisdock.metier.EleveSearch;
import com.irisdock.metier.NiveauCreate;
import com.irisdock.metier.NiveauDelete;
import com.irisdock.metier.NiveauSearch;
import com.irisdock.metier.ProfesseurCreate;
import com.irisdock.metier.ProfesseurDelete;
import com.irisdock.metier.ProfesseurSearch;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension defaultSize = new Dimension(400,300);
	
	// Propriétés du menu
	private JMenuBar menu_coreBar = new JMenuBar();
	private JMenu menu_core = new JMenu("Menu");
	private JMenu menu_eleves = new JMenu("Eleves");
	private JMenu menu_eleves_rechercher = new JMenu("Rechercher");
	private JMenu menu_professeurs = new JMenu("Professeurs");
	private JMenu menu_classes = new JMenu("Classes");
	private JMenu menu_niveaux = new JMenu("Niveaux");
	private JMenuItem eleves_ajouter = new JMenuItem("Ajouter");
	private JMenuItem eleves_rechercher_byClasse = new JMenuItem("Recherche par classe");
	private JMenuItem eleves_rechercher_byLogin = new JMenuItem("Recherche par login");
	private JMenuItem eleves_supprimer = new JMenuItem("Supprimer");
	private JMenuItem professeurs_ajouter = new JMenuItem("Ajouter");
	private JMenuItem professeurs_rechercher = new JMenuItem("Rechercher");
	private JMenuItem professeurs_supprimer = new JMenuItem("Supprimer");
	private JMenuItem classes_ajouter = new JMenuItem("Ajouter");
	private JMenuItem classes_lister = new JMenuItem("Lister");
	private JMenuItem classes_supprimer = new JMenuItem("Supprimer");
	private JMenuItem niveaux_ajouter = new JMenuItem("Ajouter");
	private JMenuItem niveaux_lister = new JMenuItem("Lister");
	private JMenuItem niveaux_supprimer = new JMenuItem("Supprimer");
	private JMenuItem item_fermer = new JMenuItem("Fermer");
	
	public Window(DAOFactory factory)
	{
		// Récupération de la DAOFactory pour l'interaction avec la BDD
		
		// Définition des propriétés de base de la fenêtre
		this.setTitle("IrisDock");
		this.setSize(defaultSize);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		
		// Définition du menu
		this.menu_eleves.add(eleves_ajouter);
		eleves_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EleveCreate.invoke(factory);			
			}	
		} );
		this.menu_eleves_rechercher.add(eleves_rechercher_byClasse);
		eleves_rechercher_byClasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EleveSearch.byClasse(factory);			
			}	
		} );
		this.menu_eleves_rechercher.add(eleves_rechercher_byLogin);
		eleves_rechercher_byLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EleveSearch.byLogin(factory);			
			}	
		} );
		this.menu_eleves.add(menu_eleves_rechercher);
		this.menu_eleves.add(eleves_supprimer);
		eleves_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EleveDelete.invoke(factory);
			}
		} );
		
		
		this.menu_professeurs.add(professeurs_ajouter);
		professeurs_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfesseurCreate.invoke(factory);			
			}	
		} );
		this.menu_professeurs.add(professeurs_rechercher);
		professeurs_rechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfesseurSearch.invoke(factory);			
			}	
		} );
		this.menu_professeurs.add(professeurs_supprimer);
		professeurs_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfesseurDelete.invoke(factory);			
			}	
		} );
		
		this.menu_classes.add(classes_ajouter);
		classes_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClasseCreate.invoke(factory);			
			}	
		} );
		this.menu_classes.add(classes_lister);
		classes_lister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClasseSearch.invoke(factory);			
			}	
		} );
		this.menu_classes.add(classes_supprimer);
		classes_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClasseDelete.invoke(factory);			
			}	
		} );
		
		this.menu_niveaux.add(niveaux_ajouter);
		niveaux_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NiveauCreate.invoke(factory);			
			}	
		} );
		this.menu_niveaux.add(niveaux_lister);
		niveaux_lister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NiveauSearch.invoke(factory);			
			}	
		} );
		this.menu_niveaux.add(niveaux_supprimer);
		niveaux_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NiveauDelete.invoke(factory);			
			}	
		} );
		
		this.menu_core.add(menu_eleves);
		this.menu_core.add(menu_professeurs);
		this.menu_core.add(menu_classes);
		//this.menu_core.add(menu_niveaux);
		this.menu_core.addSeparator();
		item_fermer.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					Shutdown.nice();
				}
			}	
		);
		this.menu_core.add(item_fermer);
		this.menu_coreBar.add(menu_core);
		
		this.setJMenuBar(menu_coreBar);
		
		
		
		
		this.setVisible(true);
		
		// Instructions de fermeture de la fenêtre
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				Shutdown.nice();
			}
		} );
		
	}
	
}
