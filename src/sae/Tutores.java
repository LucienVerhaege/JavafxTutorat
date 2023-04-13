package sae;

import java.util.HashMap;

public class Tutores extends Etudiant {
	private	HashMap<Matiere, Double> moyenne;

	public Tutores(String prenom, String nom, HashMap<Matiere, Double> moyenne) {
		super(prenom,nom,1);
		this.moyenne = moyenne;
	}

	public String getPrenom() {
		return super.getPrenom();
	}
	
	public String getNom() {
		return super.getNom();
	}

	public double getMoyenne(Matiere m) {
		if (!moyenne.keySet().contains(m)) return 20.0;
		return moyenne.get(m) == null ? 20.0 : moyenne.get(m);
	}
	
	public HashMap<Matiere, Double> getMoyennes() {
		return moyenne;
	}

	public void setPrenom(String prenom) {
		super.setPrenom(prenom);
	}

	public void setNom(String nom) {
		super.setNom(nom);
	}

	public void setMoyenne(HashMap<Matiere, Double> moyenne) {
		this.moyenne = moyenne;
	}
	
	public void setMoyenne(Matiere m, double moyenne) {
		this.moyenne.put(m,moyenne);
	}
	
	public void setMoyenneMax() {
		for (Matiere m : moyenne.keySet()) {
			moyenne.put(m, 20.0);
		}
	}
	
	public String toString() {
		return super.toString();
	}

	@Override
	/**
	 * getMatiere() est une méthode utilisée pour les tuteurs, ici elle retourne null. Elle est contenue dans la classe 
	 */
	public Matiere getMatiere() {
		return null;
	}
	
}
