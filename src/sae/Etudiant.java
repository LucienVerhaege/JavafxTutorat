package sae;

import javafx.beans.property.StringProperty;

public abstract class Etudiant extends Personne {
	private int annee;
	private String fonction;
	
	public Etudiant(String prenom, String nom,int annee) {
		super(prenom,nom);
		this.annee = annee;
		if(annee > 1) {
			this.fonction = "Tuteur";
		} else {
			this.fonction = "Tutore";
		}
	}
	
	public String getPrenom() {
		return super.getPrenom();
	}

	public void setPrenom(String prenom) {
		super.setPrenom(prenom);
	}

	public void setNom(String nom) {
		super.setNom(nom);
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getNom() {
		return super.getNom();
	}
	
	public String getFonction() {
		return fonction;
	}

	public int getAnnee() {
		return annee;
	}
	
	public abstract double getMoyenne(Matiere matiere);
	
	public abstract Matiere getMatiere();
	
	public String toString() {
		return getNom() + " " + getPrenom();
	}
	
}