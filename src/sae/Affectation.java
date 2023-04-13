package sae;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class Affectation {
	private ArrayList<Etudiant> tuteurs;
	private ArrayList<Etudiant> tutores;
	private static double cpt = 1;
	private static int pondAnnee3 =16;
	private static int pondAnnee2 = -16;
	private static int moyenneSup = 8;
	
	
	public Affectation(ArrayList<Etudiant> tuteurs,ArrayList<Etudiant> tutores) {
		this.tuteurs = tuteurs;
		this.tutores = tutores;
	}
	
	public Affectation() {
		this.tuteurs = new ArrayList<>();
		this.tutores = new ArrayList<>();
	}
	
	/**
	 * Ici on calcul la moyenne de tous les tuteurs
	 * @return on retourne une moyenne
	 */
	
	public double moyenneAllTuteurs(Matiere m) {
		double moyenne = 0.0;
		int cpt = 0;
		for(Etudiant t : tuteurs) {
			if (t.getMatiere()==m) {
				cpt++;
				moyenne = moyenne + t.getMoyenne(m);
			}
		}
		
		return moyenne/cpt;
	}
	
	/**
	 * 
	 * @return une ArrayList de tuteurs
	 */
	public ArrayList<Etudiant> getTutors() { return this.tuteurs; }
	/**
	 * 
	 * @return une ArrayList de tutores
	 */
    public ArrayList<Etudiant> getTutored() { return this.tutores; }
    
    /**
     * Cette fonction ajoute les prenoms en sommet du graphe non oriente
     * @param g graphe non oriente
     * 
     */
    
    public void addingVertices(GrapheNonOrienteValue<Etudiant> g) {
        for (int i=0; i<tuteurs.size(); i++) {
            g.ajouterSommet(this.getTutors().get(i));
            g.ajouterSommet(this.getTutored().get(i));
        }
    }
    
	/**
	 * Cette fonction permet de calcule le poids entre un tuteur et un tutore
	 * @param tuteur Tuteurs
	 * @param tutore Tutores
	 * @return le poids de l'arete tuteur/tutore
	 */
    
    public double calculPoids(Etudiant tuteur,Etudiant tutore) {
		double res = (tutore.getMoyenne(tuteur.getMatiere())-tuteur.getMoyenne(tuteur.getMatiere())) * cpt ;
		if(tuteur.getAnnee() == 3)
			res = res - pondAnnee3;
		else
			res = res + pondAnnee2;
		if(tuteur.getMoyenne(tuteur.getMatiere())>= moyenneAllTuteurs(tuteur.getMatiere()))
			res = res - moyenneSup;
		Affectation.cpt -= 0.02;
		return res;
	}
    
    /**
     * Cette fonction permet d'ajouter les aretes tuteur/tutore dans le graphe
     * @param g graphe non oriente
     * @see #getTutors()
     * @see #getTutored()
     */
    
    public void poidsAffectation (GrapheNonOrienteValue<Etudiant> g, Matiere m) {
        for (Etudiant tuteur : tuteurs) {
            for (Etudiant tutore : tutores) {
            	if(!g.contientArete(tuteur, tutore)) {
            		if(tuteur.getMoyenne(m) == 0.0 || tutore.getMoyenne(tuteur.getMatiere()) >= 20) {
            			g.ajouterArete(tuteur,tutore,999);
            		} else {
            			g.ajouterArete(tuteur, tutore,this.calculPoids(tuteur, tutore));
            		}
            	}
            }
        }
    }
    
   
	/**
	 * Cette fonction sert � ajouter les noms des tuteurs dans une liste
	 * @param p1 List de String (vide)
	 * @return List de string
	 */
	public List<String> firstSet( List<String> p1) {
        for (int i=0; i<tuteurs.size(); i++) {
            p1.add(tuteurs.get(i).getNom());
        }
        return p1;
    }
    
	/**
	 * Cette fonction sert � ajouter les noms des tutores dans une liste
	 * @param p2 List de String (vide)
	 * @return List de String
	 */
    public List<String> secondSet(List<String> p2) {
        for (int i=0; i<tutores.size(); i++) {
            p2.add(tutores.get(i).getNom());
        }
        return p2;
    }
    
    /**
     * Cette fonction est une fonction alternative qui ne sert que pour les tests
     * Elle supprime un/des tutore(s) de l'ArrayList
     * @param t ArrayList de Tutores
     * @param prenom ArrayList de String
     * @return 
     */
    
    public ArrayList<Etudiant> deleteTutoresWithoutScan(ArrayList<Etudiant> t,ArrayList<String> nom,GrapheNonOrienteValue<Etudiant> g){
    	//ArrayList<Etudiant> toDelete = new ArrayList<>();
    	int cpt = 0;
    	Tuteurs tuteur;
    	for(Etudiant tutore : t) {
    		if(nom.contains(tutore.getNom())) {
    			tuteur = new Tuteurs("Tuteur fictif" + cpt,"",Matiere.MATHS,0.0,2);
    			tuteurs.add(tuteur);
    			g.ajouterSommet(tutore);
				g.ajouterSommet(tuteur);
				g.ajouterArete(tuteur, tutore, -500);
				cpt++;
    		}
    	}
    	return t;
    }
    
    /**
     * Cette fonction est une fonction alternative qui ne sert que pour les tests
     * Elle supprime un/des tuteur(s) de l'ArrayList
     * @param t ArrayList de Tuteurs
     * @param prenom ArrayList de String
     * @return 
     */
    public ArrayList<Etudiant> deleteTuteursWithoutScan(ArrayList<Etudiant> t,ArrayList<String> nom,GrapheNonOrienteValue<Etudiant> g){
    	Tutores tutore;
    	for(int i=0; i<t.size(); i++) {
    		if(nom.contains(t.get(i).getNom())) {
    			HashMap<Matiere, Double> hm = new HashMap<Matiere, Double>();
		    	hm.put(Matiere.MATHS,20.0);
				tutore = new Tutores("Tutore fictif" + i,"",hm);
				tutores.add(tutore);
				g.ajouterSommet(tuteurs.get(i));
				g.ajouterSommet(tutore);
				g.ajouterArete(tuteurs.get(i),tutore, -250);
    		}
    	}
    	return t;
    }
    
    /**
     * Cette fonction permet � l'utilisateur de supprimer ou non des Tutores de l'ArrayList de Tutores
     * @param tutores ArrayList de Tutores
     * @return une ArrayList de Tutores
     */
    public ArrayList<Etudiant> deleteTutores(ArrayList<Etudiant> tutores,ArrayList<Etudiant> tuteurs,GrapheNonOrienteValue<Etudiant> g){
    	@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Voulez-vous supprimer des tutores de la liste ? [y/n]");
		String rep = input.nextLine();
		char cara2 = 'b';
		if(!rep.equals("") && rep.length() == 1) {
			rep = rep.substring(0,1);
			cara2 = rep.charAt(0);
		}
		
		boolean test = false;
		boolean test2 = false;
		boolean test3 = false;
		
		while(!test) {
			if(cara2 == 'y') {
				System.out.println("Combien voulez vous en supprimer ?");
				test = true;
				
			}else if(cara2 == 'n'){
				test = true;
			}else {
				System.out.print("Ce n'est pas une reponse possible");
				rep = input.nextLine();
				if(!rep.equals("") && rep.length() == 1) {
					rep = rep.substring(0,1);
					cara2 = rep.charAt(0);
				}
			}
		}
		
		if(cara2 == 'y') {
			
			String nbSupr = "";
			while(!test2) {
				nbSupr = input.nextLine();
				try 
				{ 
					Integer.parseInt(nbSupr); 
					test2 = true;
				}  
				catch (NumberFormatException e) {
					System.out.println("Ce n'est pas un nombre");
				}
			}
			for(int cpt = 0; cpt < Integer.parseInt(nbSupr);cpt++ ) {
				while(!test3) {
					if(cara2 == 'y') {
						System.out.println("Qui voulez-vous supprimer ?");
						test3 = true;
						
					}else if(cara2 == 'n'){
						test3 = true;
					}else {
						System.out.print("Ce n'est pas une reponse possible");
						rep = input.nextLine().substring(0, 1);
						cara2 = rep.charAt(0);
					}
				}
				test3 = false;
				int indice =0;
				
				if(cara2 == 'y') {
					rep = input.nextLine();
					while(!test3) {
						int indice2 = 0;
						for(Etudiant t : tutores) {
							if(t.getNom().equals(rep)) {
								test3 = true;
								indice = indice2;
							}
							indice2++;
							
						}if(!test3) {
							System.out.println("Cette personne n'est pas un tutore");
							rep = input.nextLine();
						}
					}
				}
				
				Tuteurs tuteur = new Tuteurs("Tuteur fictif" + indice,"",Matiere.MATHS,0.0,2);
				tuteurs.add(tuteur);
				g.ajouterSommet(tutores.get(indice));
				g.ajouterSommet(tuteur);
				g.ajouterArete(tuteur, tutores.get(indice), -500);
				
				test3 = false;
		
			}
		}
		return tutores;
    }
    
    /**
     * Cette fonction permet � l'utilisateur de supprimer ou non des Tuteurs de l'ArrayList de Tuteurs
     * @param tuteurs ArrayList de Tuteurs
     * @return une ArrayList de Tuteurs
     */
    public ArrayList<Etudiant> deleteTuteurs(ArrayList<Etudiant> tuteurs,ArrayList<Etudiant> tutores,GrapheNonOrienteValue<Etudiant> g){
    	@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Voulez-vous supprimer des tuteurs de la liste ? [y/n]");
		String rep = input.nextLine();
		char cara2 = 'b';
		if(!rep.equals("") && rep.length() == 1) {
			rep = rep.substring(0,1);
			cara2 = rep.charAt(0);
		}
		
		boolean test = false;
		boolean test2 = false;
		boolean test3 = false;
		
		while(!test) {
			if(cara2 == 'y') {
				System.out.println("Combien voulez vous en supprimer ?");
				test = true;
				
			}else if(cara2 == 'n'){
				test = true;
			}else {
				System.out.print("Ce n'est pas une reponse possible");
				rep = input.nextLine();
				if(!rep.equals("") && rep.length() == 1) {
					rep = rep.substring(0,1);
					cara2 = rep.charAt(0);
				}
				
			}
		}
		
		if(cara2 == 'y') {
			String nbSupr = "";
			while(!test2) {
				nbSupr = input.nextLine();
				try 
				{ 
					Integer.parseInt(nbSupr); 
					test2 = true;
				}  
				catch (NumberFormatException e) {
					System.out.println("Ce n'est pas un nombre");
				}
			}
			for(int cpt = 0; cpt < Integer.parseInt(nbSupr);cpt++ ) {
				while(!test3) {
					if(cara2 == 'y') {
						System.out.println("Qui voulez-vous supprimer ?");
						test3 = true;
					}else if(cara2 == 'n'){
						test3 = true;
					}else {
						System.out.print("Ce n'est pas une reponse possible");
						rep = input.nextLine().substring(0, 1);
						cara2 = rep.charAt(0);
					}
				}
				test3 = false;
				int indice =0;
				
				if(cara2 == 'y') {
					rep = input.nextLine();
					while(!test3) {
						int indice2 = 0;
						for(Etudiant t : tuteurs) {
							if(t.getNom().equals(rep)) {
								test3 = true;
								indice = indice2;
							}
							indice2++;
							
						}if(!test3) {
							System.out.println("Cette personne n'est pas un tuteur");
							rep = input.nextLine();
						}
					}
				}
				HashMap<Matiere, Double> hm = new HashMap<Matiere, Double>();
		    	hm.put(Matiere.MATHS,20.0);
		    	Tutores tutore = new Tutores("Tutore fictif" + indice,"",hm);
				tutores.add(tutore);
				g.ajouterSommet(tuteurs.get(indice));
				g.ajouterSommet(tutore);
				g.ajouterArete(tuteurs.get(indice),tutore, -250);
				test3 = false;
			}
		}
		return tuteurs;
    }
    
 	
    /**
     * Cette fonction permet d'ajouter un ou plusieurs Tutores fictifs si necessaire.
     * Sinon, si il n'y a pas assez de tuteurs, elle permet de créer par la suite des connexions permettant aux tuteurs de 3e année d'avoir plusieurs tutores.
     * En effet, elle fait en sorte que le nombre de tuteurs soit le même que le nombre de tutores.
     * @param tuteurs ArrayList de Tuteurs
     * @param tutores ArrayList de Tutores
     * @return Une ArrayList de Tuteurs
     */
    
    public void autoCompletion(ArrayList<Etudiant> tuteurs, ArrayList<Etudiant> tutores){
    	int diff = tuteurs.size() - tutores.size();
    	if(diff < 0) {
			ArrayList<Etudiant> tuteursYear3 = new ArrayList<Etudiant>();
			Etudiant tuteurY3;
			for (Etudiant tuteur : tuteurs) if (tuteur.getAnnee()==3) tuteursYear3.add(tuteur);
			for (int i = diff*-1; i<=diff*-1; ++i) {
				tuteurY3 = tuteursYear3.get(i%tuteursYear3.size());
				tuteurs.add(new Tuteurs((Tuteurs) tuteurY3));
			}
		} else if (diff > 0) {
	    	HashMap<Matiere, Double> hm = new HashMap<Matiere, Double>();
	    	hm.put(Matiere.MATHS,20.0);
			for(int i = 0; i < diff;i++) {
				tutores.add(new Tutores("Tutore fictif" + i,"",hm));
			}
		}
    }
    /**
     * Cette fonction permet � l'utilisateur de forcer des couples, c'est � dire de choisir un Tutores et un Tuteurs et de les mettres ensemble
     * @param tuteurs ArrayList de Tuteurs
     * @param tutores ArrayList de Tutores
     * @param g graphe non oriente
     * @return Un graphe non oriente
     */
    public GrapheNonOrienteValue<Etudiant> addCouple(ArrayList<Etudiant> tuteurs, ArrayList<Etudiant> tutores,GrapheNonOrienteValue<Etudiant> g) {
		boolean test = false;
		boolean test2 = false;
		//Obligation de couples + gestion de robustesse
		Scanner input = new Scanner(System.in);
		System.out.println("Voulez-vous obliger des couples ? [y/n]");
		String rep = input.nextLine();
		char cara = 'b';
		if(!rep.equals("") && rep.length() == 1) {
			rep = rep.substring(0,1);
			cara = rep.charAt(0);
		}
		while(!test) {
			if(cara == 'y') {
				System.out.println("Combien voulez vous en faire ?");
				test = true;
				
			}else if(cara == 'n'){
				test = true;
			}else {
				System.out.print("Ce n'est pas une reponse possible");
				rep = input.nextLine();
				if(!rep.equals("") && rep.length() == 1) {
					rep = rep.substring(0,1);
					cara = rep.charAt(0);
				}
			}
		}
		if(cara == 'y') {
			String nb = "0";
			while(!test2) {
				nb = input.nextLine();
				try 
				{ 
					Integer.parseInt(nb); 
					test2 = true;
				}  
				catch (NumberFormatException e) {
					System.out.println("Ce n'est pas un nombre");
				}
			}
			for(int cpt = 0; cpt < Integer.parseInt(nb);cpt++ ) {
				System.out.print("Quel est le prenom du tuteur ?");
				String tuteur = input.nextLine();
				boolean isTuteur = false;
				while(!isTuteur) {
					for(Etudiant i : tuteurs) {
						if(i.getNom().equals(tuteur)) {
							isTuteur = true;
						}
					}if(!isTuteur) {
						System.out.println("Cette personne n'est pas un tuteur");
						tuteur = input.nextLine();
					}
				}
				System.out.print("Quel est le prenom du tutore ?");
				String tutore = input.nextLine();
				boolean isTutore = false;
				while(!isTutore) {
					for(Etudiant i : tutores) {
						if(i.getNom().equals(tutore)) {
							isTutore = true;
						}	
					}if(!isTutore) {
						System.out.println("Cette personne n'est pas un tutore");
						tutore = input.nextLine();
					}
				}
				for(Etudiant i : tuteurs) {
					for(Etudiant t : tutores) {
						if(!g.contientArete(i, t) && i.getNom().equals(tuteur) && t.getNom().equals(tutore)){
								g.ajouterSommet(i);
								g.ajouterSommet(t);
								g.ajouterArete(i, t, -500);
						}
					}	
				}
			}
		}
		input.close();
		return g;
    }
    /**
     * Cette fonction est une fonction qui ne sert que pour la class test, elle fait exactement la meme chose que la fonction addCouple
     * @param tuteurs ArrayList de Tuteurs
     * @param tutores ArrayList de Tutores
     * @param g graphe non oriente
     * @param prenomTuteurs ArrayList de String
     * @param prenomTutores ArrayList de String
     * @return Un graphe non oriente
     * @see #addCouple(ArrayList, ArrayList, GrapheNonOrienteValue)
     */
    public GrapheNonOrienteValue<Etudiant> addCoupleWithoutScan(ArrayList<Etudiant> tuteurs, ArrayList<Etudiant> tutores,GrapheNonOrienteValue<Etudiant> g,ArrayList<String> nomTuteurs,ArrayList<String> nomTutores) {
	    for(int index = 0;index < nomTuteurs.size();index ++) {
	    	for(Etudiant tuteur : tuteurs) {
				for(Etudiant tutore : tutores) {
					if(!g.contientArete(tuteur, tutore) && tuteur.getNom().equals(nomTuteurs.get(index)) && tutore.getNom().equals(nomTutores.get(index))){
							g.ajouterSommet(tuteur);
							g.ajouterSommet(tutore);
							g.ajouterArete(tuteur, tutore, -500);
					}
				}
			}
	    }
	    return g;
    }
    

	public static void setPondAnnee3(int pondAnnee3) {
		Affectation.pondAnnee3 = pondAnnee3;
	}

	public static void setPondAnnee2(int pondAnnee2) {
		Affectation.pondAnnee2 = pondAnnee2;
	}

	public static void setMoyenneSup(int moyenneSup) {
		Affectation.moyenneSup = moyenneSup;
	}

	public static int getPondAnnee3() {
		return pondAnnee3;
	}

	public static int getPondAnnee2() {
		return pondAnnee2;
	}

	public static int getMoyenneSup() {
		return moyenneSup;
	}
    
}
