package com.irisdock.beans;

public class Niveau {
	private Integer identifiant;
	private String label;
	
	public Niveau() {
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
	
	@Override
	public String toString() {
		return label;
	}
	
	
}
