package com.irisdock.beans;

public class Classe {
	private Integer identifiant;
	private String label;
	private Niveau niveau;
	
	public Classe() {	
	}

	public Integer getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(Integer identifiant) {
		this.identifiant = identifiant;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}
	
	@Override
	public String toString()
	{
		String result = new String();
		
		result += this.getNiveau().getLabel() + " - " + this.getLabel();
		
		return result;
	}
}
