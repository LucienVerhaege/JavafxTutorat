package sae;

import java.util.Scanner;

public class Enseignant extends Personne {
	private Matiere matiere;
	private final String LOGIN;
	private final String PWD;
	
	Enseignant(String prenom, String nom, Matiere matiere, String mdp) {
		super(prenom,nom);
		this.matiere = matiere;
		LOGIN = prenom+"."+nom;
		PWD = mdp;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
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

	public String getNom() {
		return super.getNom();
	}

	@SuppressWarnings("resource")
	public boolean isUserPWD() throws WrongLoginException, WrongPwdException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter login name");
		String login = sc.nextLine();
		System.out.println("Enter password");
		String pwd = sc.nextLine();
		
		if (login == null || !login.equals(LOGIN)) throw new WrongLoginException();
		if (pwd == null || !pwd.equals(PWD)) throw new WrongPwdException();
		
		return true;
	}
}
