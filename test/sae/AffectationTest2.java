package sae;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class AffectationTest2 {
	public Affectation a;
	public ArrayList<Etudiant> tuteurs = new ArrayList<>();
	public ArrayList<Etudiant> tutores = new ArrayList<>();
	public List<String> p1 = new ArrayList<>();
	public List<String> p2 = new ArrayList<>();
	public GrapheNonOrienteValue<Etudiant> g;
	HashMap<Matiere, Double> hm1 = new HashMap<Matiere, Double>();
	HashMap<Matiere, Double> hm2 = new HashMap<Matiere, Double>();
	HashMap<Matiere, Double> hm3 = new HashMap<Matiere, Double>();
	HashMap<Matiere, Double> hm7 = new HashMap<Matiere, Double>();
	HashMap<Matiere, Double> hm8 = new HashMap<Matiere, Double>();
	HashMap<Matiere, Double> hm9 = new HashMap<Matiere, Double>();
	
	@BeforeEach
	public void initialization() {
		
		//Initialisation des moyennes pour les tutorés
		hm1.put(Matiere.BDD, 10.0); hm1.put(Matiere.MATHS, 0.2); hm1.put(Matiere.IHM, 7.2); hm1.put(Matiere.GRAPHE, 3.1);
		hm2.put(Matiere.BDD, 8.8); hm2.put(Matiere.MATHS, 10.2); hm2.put(Matiere.DEVELOPPEMENT, 1.4);
		hm3.put(Matiere.BDD, 4.8);hm3.put(Matiere.MATHS, 3.2);hm3.put(Matiere.DEVELOPPEMENT, 1.4);hm3.put(Matiere.IHM, 10.0);hm3.put(Matiere.GRAPHE, 16.5);
		hm7.put(Matiere.BDD, 19.9);hm7.put(Matiere.MATHS, 3.6);hm7.put(Matiere.DEVELOPPEMENT, 16.5);hm7.put(Matiere.IHM, 16.0);hm7.put(Matiere.GRAPHE, 6.5);
		hm8.put(Matiere.BDD, 17.5);hm8.put(Matiere.MATHS, 14.6);hm8.put(Matiere.DEVELOPPEMENT, 1.5);hm8.put(Matiere.IHM, 7.0);hm8.put(Matiere.GRAPHE, 12.5);
		hm9.put(Matiere.BDD, 10.0);hm9.put(Matiere.MATHS, 8.9);hm9.put(Matiere.DEVELOPPEMENT, 10.5);hm9.put(Matiere.IHM, 12.0);hm9.put(Matiere.GRAPHE, 14.5);
		
		//Initialisation de l'Arraylist tuteurs
		tuteurs.add(new Tuteurs("Paul","Sanchez",Matiere.MATHS,12.0,3));
		tuteurs.add(new Tuteurs("Ines","Gautier",Matiere.DEVELOPPEMENT,9.3,3));
		tuteurs.add(new Tuteurs("Alex","Marchand",Matiere.BDD, 8.7,3));
		tuteurs.add(new Tuteurs("Sabine","Leleu",Matiere.DEVELOPPEMENT,15.8,2));
		tuteurs.add(new Tuteurs("Therese","Gay",Matiere.MATHS,11.5,2));
		tuteurs.add(new Tuteurs("Juliette","Traore",Matiere.IHM,9.8,2));
		tuteurs.add(new Tuteurs("Joseph","Boyer",Matiere.GRAPHE,7.7,2));
		//Initialisation de l'Arraylist tutor�s			
		tutores.add(new Tutores("Hugues","Bigot",hm1));
		tutores.add(new Tutores("Thierry","Moreno",hm2));
		tutores.add(new Tutores("David","Collin",hm3));
		tutores.add(new Tutores("Marine","Roux",hm7));
		tutores.add(new Tutores("Claude","Allard",hm8));
		tutores.add(new Tutores("Lucas","Bouchet",hm9));
		a = new Affectation(tuteurs,tutores);
		p1 = a.firstSet(p1);
		p2 = a.secondSet(p2);
	}

	@Test
	void testCalculPoids() {
		hm1.put(Matiere.BDD, 10.0); hm1.put(Matiere.MATHS, 0.2); hm1.put(Matiere.IHM, 7.2); hm1.put(Matiere.GRAPHE, 3.1);
		hm2.put(Matiere.BDD, 8.8); hm2.put(Matiere.MATHS, 10.2); hm2.put(Matiere.DEVELOPPEMENT, 1.4);
		double poids = a.calculPoids(new Tuteurs("Paul","Sanchez",Matiere.MATHS,12.0,3),new Tutores("Hugues","Bigot",hm1));
		assertEquals(poids,-35,8);
		poids = a.calculPoids(new Tuteurs("Ines","Gautier",Matiere.DEVELOPPEMENT,9.3,3), new Tutores("Thierry","Moreno",hm2));
		assertEquals(poids,-23,742);
	}

	@Test
	void testDeleteTutoresWithoutScan() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("Bigot");
		test.add("Moreno");
		GrapheNonOrienteValue<Etudiant> g1 = new GrapheNonOrienteValue<>();
		a.deleteTutoresWithoutScan(tutores, test,g1);
		assertTrue(g1.contientArete(tuteurs.get(tuteurs.size()-2), tutores.get(0)) && g1.contientArete(tuteurs.get(tuteurs.size()-1), tutores.get(1)));	
	}

	@Test
	void testAddCoupleWithoutScan() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("Bigot");
		test.add("Moreno");
		ArrayList<String> test2 = new ArrayList<String>();
		test2.add("Boyer");
		test2.add("Gay");
		g = new GrapheNonOrienteValue<>();
		g = a.addCoupleWithoutScan(tuteurs, tutores, g, test2, test);
		assertTrue(g.contientArete(tuteurs.get(6), tutores.get(0)) && g.contientArete(tuteurs.get(4), tutores.get(1)) && !g.contientArete(tuteurs.get(6), tutores.get(1)));
	}
}
