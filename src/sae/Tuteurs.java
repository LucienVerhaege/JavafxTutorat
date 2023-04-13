package sae;

public class Tuteurs extends Etudiant {
	private	Matiere matiere;
	private double moyenne;
	
	public Tuteurs(String prenom,String nom,Matiere matiere, double moyenne,int annee) {
		super(prenom,nom,annee);
		this.matiere = matiere;
		this.moyenne = moyenne;
	}
	
	public Tuteurs(Tuteurs tuteur) {
		this(tuteur.getPrenom(), tuteur.getNom(), tuteur.getMatiere(), tuteur.getMoyenne(), tuteur.getAnnee());
	}

	public void setPrenom(String prenom) {
		super.setPrenom(prenom);
	}

	public void setNom(String nom) {
		super.setNom(nom);
	}

	public String getPrenom() {
		return super.getPrenom();
	}

	public String getNom() {
		return super.getNom();
	}

	public double getMoyenne() {
		return moyenne;
	}

	public int getAnnee() {
		return super.getAnnee();
	}

	public void setMoyenne(double moyenne) {
		this.moyenne = moyenne;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public String toString() {
		return super.toString() + "["+matiere+"]";
	}
	
	@Override
	public double getMoyenne(Matiere m) {
		return (m == matiere) ? moyenne : 0.0;
	}
	
	
}
