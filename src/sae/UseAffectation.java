package sae;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UseAffectation {

	private static Affectation affectation = new Affectation();

	public static ArrayList<ObservableList> initialisation() {

		Enseignant testTeach = new Enseignant("Kazuya", "Mishima", Matiere.DEVELOPPEMENT, "motdepasse");

		ArrayList<Etudiant> tuteurs = new ArrayList<>();
		ArrayList<Etudiant> tutores = new ArrayList<>();
		List<String> p1 = new ArrayList<>();
		List<String> p2 = new ArrayList<>();

		HashMap<Matiere, Double> hm1 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm2 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm3 = new HashMap<Matiere, Double>();
		hm1.put(Matiere.BDD, 10.0); hm1.put(Matiere.MATHS, 0.2); hm1.put(Matiere.IHM, 7.2); hm1.put(Matiere.GRAPHE, 3.1);
		hm2.put(Matiere.BDD, 8.8); hm2.put(Matiere.MATHS, 10.2); hm2.put(Matiere.DEVELOPPEMENT, 1.4);

		hm3.put(Matiere.BDD, 4.8);
		hm3.put(Matiere.MATHS, 3.2);
		hm3.put(Matiere.DEVELOPPEMENT, 1.4);
		hm3.put(Matiere.IHM, 10.0);
		hm3.put(Matiere.GRAPHE, 16.5);

		HashMap<Matiere, Double> hm7 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm8 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm9 = new HashMap<Matiere, Double>();

		hm7.put(Matiere.BDD, 19.9);
		hm7.put(Matiere.MATHS, 3.6);
		hm7.put(Matiere.DEVELOPPEMENT, 16.5);
		hm7.put(Matiere.IHM, 16.0);
		hm7.put(Matiere.GRAPHE, 2.5);

		hm8.put(Matiere.BDD, 17.5);
		hm8.put(Matiere.MATHS, 14.6);
		hm8.put(Matiere.DEVELOPPEMENT, 1.5);
		hm8.put(Matiere.IHM, 7.0);
		hm8.put(Matiere.GRAPHE, 12.5);

		hm9.put(Matiere.BDD, 10.0);
		hm9.put(Matiere.MATHS, 8.9);
		hm9.put(Matiere.DEVELOPPEMENT, 10.5);
		hm9.put(Matiere.IHM, 12.0);
		hm9.put(Matiere.GRAPHE, 14.5);



		//Initialisation de l'Arraylist tutores
		tutores.add(new Tutores("Hugues","Bigot",hm1));
		tutores.add(new Tutores("Thierry","Moreno",hm2));
		tutores.add(new Tutores("David","Collin",hm3));
		tutores.add(new Tutores("Marine","Roux",hm1));
		tutores.add(new Tutores("Claude","Allard",hm8));
		tutores.add(new Tutores("Lucas","Bouchet",hm9));
		//Initialisation de l'Arraylist tuteurs
		tuteurs.add(new Tuteurs("Paul","Sanchez",Matiere.MATHS,12.0,3));
		tuteurs.add(new Tuteurs("Ines","Gautier",Matiere.DEVELOPPEMENT,9.3,3));
		tuteurs.add(new Tuteurs("Alex","Marchand",Matiere.BDD, 8.7,3));
		tuteurs.add(new Tuteurs("Juliette","Traore",Matiere.IHM,9.8,2));
		tuteurs.add(new Tuteurs("Joseph","Boyer",Matiere.GRAPHE,7.7,2));
		tuteurs.add(new Tuteurs("Sabine","Leleu",Matiere.DEVELOPPEMENT,15.8,2));
		tuteurs.add(new Tuteurs("Therese","Gay",Matiere.MATHS,11.5,2));

		affectation = new Affectation(tuteurs,tutores);
		GrapheNonOrienteValue<Etudiant> g = new GrapheNonOrienteValue<>();
		//Login: Kazuya.Mishima   MDP: motdepasse
		boolean connecte = false;
		/*try {
			connecte = testTeach.isUserPWD();
			System.out.println("Connexion réussie! Bienvenue, "+testTeach.getPrenom() + " "+testTeach.getNom());
			tutores = affectation.deleteTutores(tutores,tuteurs,g);
			tuteurs = affectation.deleteTuteurs(tuteurs,tutores,g);
		}
		catch(WrongLoginException wle) {
			System.err.println("Mauvais login!");
		}
		catch(WrongPwdException wpe) {
			System.err.println("Mauvais mot de passe!");
		}
		finally {*/
		affectation.autoCompletion(tuteurs, tutores);
		affectation = new Affectation(tuteurs,tutores);
		affectation.firstSet(p1);
		affectation.secondSet(p2);
		if (connecte) g = affectation.addCouple(tuteurs, tutores, g);
		affectation.addingVertices(g);
		affectation.poidsAffectation(g, Matiere.MATHS);
		//System.out.println(g);
		//}

		//Calcul de l'affectation au poids minimum

		CalculAffectation<Etudiant> cal = new CalculAffectation<>(g,tuteurs,tutores);
		//System.out.println(cal.getAffectation());
		//System.out.println(cal.getCout());
		//System.out.println(cal.getAffectation().get(2).getExtremite1());

		ObservableList<ObjectTab> tab = FXCollections.observableArrayList();
		ObservableList<Etudiant> tabAb = FXCollections.observableArrayList();
		for(Arete<Etudiant> e : cal.getAffectation()) {
			if(e.getExtremite2().toString().contains("Tutore fictif")) {
				tabAb.add(e.getExtremite1());
			} else {
				tab.add(new ObjectTab((Tuteurs)e.getExtremite1(), (Tutores)e.getExtremite2()));
			}
		}
		ArrayList<ObservableList> tabFin = new ArrayList<>();
		tabFin.add(tab);
		tabFin.add(tabAb);
		return tabFin;
	}

	public static ArrayList<ObservableList> initialisationDelete(ArrayList<Etudiant> etuDel, ArrayList<String> etuTuteur, ArrayList<String> etuTutore) {

		Enseignant testTeach = new Enseignant("Kazuya", "Mishima", Matiere.DEVELOPPEMENT, "motdepasse");

		ArrayList<Etudiant> tuteurs = new ArrayList<>();
		ArrayList<Etudiant> tutores = new ArrayList<>();
		List<String> p1 = new ArrayList<>();
		List<String> p2 = new ArrayList<>();

		HashMap<Matiere, Double> hm1 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm2 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm3 = new HashMap<Matiere, Double>();
		hm1.put(Matiere.BDD, 10.0); hm1.put(Matiere.MATHS, 0.2); hm1.put(Matiere.IHM, 7.2); hm1.put(Matiere.GRAPHE, 3.1);
		hm2.put(Matiere.BDD, 8.8); hm2.put(Matiere.MATHS, 10.2); hm2.put(Matiere.DEVELOPPEMENT, 1.4);

		hm3.put(Matiere.BDD, 4.8);
		hm3.put(Matiere.MATHS, 3.2);
		hm3.put(Matiere.DEVELOPPEMENT, 1.4);
		hm3.put(Matiere.IHM, 10.0);
		hm3.put(Matiere.GRAPHE, 16.5);

		HashMap<Matiere, Double> hm7 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm8 = new HashMap<Matiere, Double>();
		HashMap<Matiere, Double> hm9 = new HashMap<Matiere, Double>();

		hm7.put(Matiere.BDD, 19.9);
		hm7.put(Matiere.MATHS, 3.6);
		hm7.put(Matiere.DEVELOPPEMENT, 16.5);
		hm7.put(Matiere.IHM, 16.0);
		hm7.put(Matiere.GRAPHE, 2.5);

		hm8.put(Matiere.BDD, 17.5);
		hm8.put(Matiere.MATHS, 14.6);
		hm8.put(Matiere.DEVELOPPEMENT, 1.5);
		hm8.put(Matiere.IHM, 7.0);
		hm8.put(Matiere.GRAPHE, 12.5);

		hm9.put(Matiere.BDD, 10.0);
		hm9.put(Matiere.MATHS, 8.9);
		hm9.put(Matiere.DEVELOPPEMENT, 10.5);
		hm9.put(Matiere.IHM, 12.0);
		hm9.put(Matiere.GRAPHE, 14.5);



		//Initialisation de l'Arraylist tutores
		tutores.add(new Tutores("Hugues","Bigot",hm1));
		tutores.add(new Tutores("Thierry","Moreno",hm2));
		tutores.add(new Tutores("David","Collin",hm3));
		tutores.add(new Tutores("Marine","Roux",hm1));
		tutores.add(new Tutores("Claude","Allard",hm8));
		tutores.add(new Tutores("Lucas","Bouchet",hm9));
		//Initialisation de l'Arraylist tuteurs
		tuteurs.add(new Tuteurs("Paul","Sanchez",Matiere.MATHS,12.0,3));
		tuteurs.add(new Tuteurs("Ines","Gautier",Matiere.DEVELOPPEMENT,9.3,3));
		tuteurs.add(new Tuteurs("Alex","Marchand",Matiere.BDD, 8.7,3));
		tuteurs.add(new Tuteurs("Juliette","Traore",Matiere.IHM,9.8,2));
		tuteurs.add(new Tuteurs("Joseph","Boyer",Matiere.GRAPHE,7.7,2));
		tuteurs.add(new Tuteurs("Sabine","Leleu",Matiere.DEVELOPPEMENT,15.8,2));
		tuteurs.add(new Tuteurs("Therese","Gay",Matiere.MATHS,11.5,2));

		affectation = new Affectation(tuteurs,tutores);
		GrapheNonOrienteValue<Etudiant> g = new GrapheNonOrienteValue<>();
		//Login: Kazuya.Mishima   MDP: motdepasse
		boolean connecte = false;
		for(Etudiant e : etuDel) {
			if(e instanceof Tuteurs) {
				ArrayList<String> nom = new ArrayList<>();
				nom.add(e.getNom());
				tuteurs = affectation.deleteTuteursWithoutScan(tuteurs,nom,g);
			} else {
				ArrayList<String> nom = new ArrayList<>();
				nom.add(e.getNom());
				tutores = affectation.deleteTutoresWithoutScan(tutores,nom,g);
			}
		}

		affectation.autoCompletion(tuteurs, tutores);
		affectation = new Affectation(tuteurs,tutores);
		affectation.firstSet(p1);
		affectation.secondSet(p2);
		g = affectation.addCoupleWithoutScan(tuteurs, tutores, g, etuTuteur, etuTutore);
		affectation.addingVertices(g);
		affectation.poidsAffectation(g, Matiere.MATHS);
		//System.out.println(g);

		//Calcul de l'affectation au poids minimum

		CalculAffectation<Etudiant> cal = new CalculAffectation<>(g,tuteurs,tutores);
		//System.out.println(cal.getAffectation());
		//System.out.println(cal.getCout());
		//System.out.println(cal.getAffectation().get(2).getExtremite1());

		ObservableList<ObjectTab> tab = FXCollections.observableArrayList();
		ObservableList<Etudiant> tabAb = FXCollections.observableArrayList();
		for(Arete<Etudiant> e : cal.getAffectation()) {
			if(e.getExtremite2().toString().contains("Tutore fictif")) {
				tabAb.add(e.getExtremite1());
			} else if(e.getExtremite1().toString().contains("Tuteur fictif")) {
				tabAb.add(e.getExtremite2());
			} else {
				tab.add(new ObjectTab((Tuteurs)e.getExtremite1(), (Tutores)e.getExtremite2()));
			} 
		}
		ArrayList<ObservableList> tabFin = new ArrayList<>();
		tabFin.add(tab);
		tabFin.add(tabAb);
		return tabFin;
	}

	public static Affectation getAffectation() {
		return affectation;
	}

	public static void main(String[] args) {
		UseAffectation.initialisation();
	}
}
