package sae;

public class ObjectTab {
	
	private Etudiant tuteur;
	private Etudiant tutore;
	
	public ObjectTab(Tuteurs tuteur, Tutores tutore) {
		this.tuteur = tuteur;
		this.tutore = tutore;
	}

	public Etudiant getTuteur() {
		return tuteur;
	}

	public Etudiant getTutore() {
		return tutore;
	}
}
