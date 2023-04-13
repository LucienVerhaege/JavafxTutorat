package sae;

public abstract class Personne {
	private String prenom;
	private String nom;
	
	Personne (String prenom, String nom) {
		this.prenom = prenom;
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
}
