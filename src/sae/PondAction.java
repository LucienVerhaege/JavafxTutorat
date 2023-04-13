package sae;

import java.io.IOException;
import java.util.ArrayList;

import fr.ulille.but.sae2_02.graphes.Arete;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PondAction {
	private Stage stage;
	private Scene scene;
	private Parent root;

	//Page ponderations

	@FXML
	private TextField deux;

	@FXML
	private Label deuxLab;

	@FXML
	private Label supLab;

	@FXML
	private TextField trois;

	@FXML
	private Label troisLab;

	@FXML
	private TextField tutSup;

	//Page d'accueil

	@FXML
	private TableView<ObjectTab> table;

	@FXML
	private TableView<Etudiant> tableDeux;

	//Page details tuteurs

	@FXML
	private Label anneeTuteur;

	@FXML
	private Label nomTuteur;

	@FXML
	private Label prenomTuteur;

	//Page modifs tuteurs

	@FXML
    private Label anneeTuteurB;

    @FXML
    private Label anneeTuteurC;

    @FXML
    private Label nomTuteurB;

    @FXML
    private Label nomTuteurC;

    @FXML
    private TextField nomTutoreEntree;

    @FXML
    private Label prenomTuteurB;

    @FXML
    private Label prenomTuteurC;

    @FXML
    private Label validationTuteurCouple;

    @FXML
    private Label validationTuteurDel;

	//Page details couple

	@FXML
	private Label anneeTuteurD;

	@FXML
	private Label nomTuteurD;

	@FXML
	private Label nomTutore;

	@FXML
	private Label prenomTuteurD;

	@FXML
	private Label prenomTutore;
	
	//Page modifs tutore
	
	@FXML
    private TextField nomTuteurEntree;

    @FXML
    private Label nomTutoreB;

    @FXML
    private Label nomTutoreC;

    @FXML
    private Label prenomTutoreB;

    @FXML
    private Label prenomTutoreC;

    @FXML
    private Label validationTutoreCouple;

    @FXML
    private Label validationTutoreDel;
    
    //page details tutore
    
    @FXML
    private Label nomTutoreD;

    @FXML
    private Label prenomTutoreD;

	//variables pour la gestion de la classe

	private static UseAffectation affectation = new UseAffectation();

	private static Etudiant etudiant;

	private static Etudiant etudiantDeux;

	private boolean initialize = false;
	
	private static ArrayList<Etudiant> nonAffecte = new ArrayList<>();

	private static boolean flagDel = false;
	
	private static ArrayList<String> nomTuteurs = new ArrayList<>();
	
	private static ArrayList<String> nomTutores = new ArrayList<>();

	public void initialize() {
		System.out.println("Chargement de la page ...");
		if(troisLab != null) {
			troisLab.setText(Affectation.getPondAnnee3() + "");
			deuxLab.setText(Affectation.getPondAnnee2() + "");
			supLab.setText(Affectation.getMoyenneSup() + "");
		}
		if(tableDeux != null && nomTuteur == null && table != null) {
			try {
				initializeTab(new ActionEvent());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			tableDeux.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override 
				public void handle(MouseEvent event) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						setEtudiant(tableDeux.getSelectionModel().getSelectedItem());
						switchToPageDetail(event, tableDeux.getSelectionModel().getSelectedItem());
						setEtudiant(tableDeux.getSelectionModel().getSelectedItem());
					}
				}
			});
			table.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override 
				public void handle(MouseEvent event) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						setEtudiant(table.getSelectionModel().getSelectedItem().getTuteur());
						setEtudiantDeux(table.getSelectionModel().getSelectedItem().getTutore());
						switchToPageDetailCouple(event);
						setEtudiant(table.getSelectionModel().getSelectedItem().getTuteur());
						setEtudiantDeux(table.getSelectionModel().getSelectedItem().getTutore());
					}
				}
			});
		}
		if(nomTuteur != null && etudiant != null) {
			nomTuteur.setText(etudiant.getNom());
			prenomTuteur.setText(etudiant.getPrenom());
			anneeTuteur.setText(String.valueOf(etudiant.getAnnee()));
		}

		if(nomTuteurB != null) {
			nomTuteurB.setText(etudiant.getNom());
			prenomTuteurB.setText(etudiant.getPrenom());
			anneeTuteurB.setText(String.valueOf(etudiant.getAnnee()));
			nomTuteurC.setText(etudiant.getNom());
			prenomTuteurC.setText(etudiant.getPrenom());
			anneeTuteurC.setText(String.valueOf(etudiant.getAnnee()));
		}
		if(nomTuteurD != null) {
			nomTuteurD.setText(etudiant.getNom());
			prenomTuteurD.setText(etudiant.getPrenom());
			anneeTuteurD.setText(String.valueOf(etudiant.getAnnee()));
			nomTutore.setText(etudiantDeux.getNom());
			prenomTutore.setText(etudiantDeux.getPrenom());
		}
		if(nomTutoreB != null) {
			setEtudiant(getEtudiantDeux());
			nomTutoreB.setText(etudiant.getNom());
			prenomTutoreB.setText(etudiant.getPrenom());
			nomTutoreC.setText(etudiant.getNom());
			prenomTutoreC.setText(etudiant.getPrenom());
		}
		
		if(nomTutoreD != null) {
			nomTutoreD.setText(etudiant.getNom());
			prenomTutoreD.setText(etudiant.getPrenom());
		}
		
	}

	@FXML
	public void switchToPagePond(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfacePagePond.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		String css = this.getClass().getResource("main.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.show();
	}

	public void switchToPageMain(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfaceMain.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		String css = this.getClass().getResource("main.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.show();
	}



	@FXML
	public void switchToPageDetail(MouseEvent event, Etudiant etu){
		if(etu instanceof Tuteurs) {
			try {
				root = FXMLLoader.load(getClass().getResource("interfacePageDetailTuteur.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				String css = this.getClass().getResource("detailTuteur.css").toExternalForm();
				scene.getStylesheets().add(css);
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(etu instanceof Tutores){
			try {
				root = FXMLLoader.load(getClass().getResource("interfacePageDetailTutore.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				String css = this.getClass().getResource("detailTuteur.css").toExternalForm();
				scene.getStylesheets().add(css);
				stage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reset(ActionEvent event) throws IOException {
		flagDel = false;
		nonAffecte.clear();
		nomTuteurs.clear();
		nomTutores.clear();
		Affectation.setPondAnnee3(16);
		Affectation.setPondAnnee2(-16);
		Affectation.setMoyenneSup(8);
	}

	@FXML
	public void switchToPageDetailTuteur(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfacePageDetailTuteur.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		String css = this.getClass().getResource("detailTuteur.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.show();
	}

	@FXML
	public void switchToPageDetailTutore(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfacePageDetailTutore.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		String css = this.getClass().getResource("detailTuteur.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void switchToPageModifTuteur(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfacePageModifTuteur.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		String css = this.getClass().getResource("modifTuteur.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.show();
	}

	@FXML
	public void switchToPageModifTutore(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfacePageModifTutore.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		String css = this.getClass().getResource("modifTuteur.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.show();
	}

	@FXML
	public void sauvHandler(ActionEvent event) throws IOException {
		String pondTrois = trois.getText();
		if(pondTrois.matches("-?\\d+")) {
			Affectation.setPondAnnee3(Integer.parseInt(pondTrois));
			System.out.println("Pondération des 3ème années modifiée avec succès.");
			trois.setText("");
		}
		troisLab.setText(Affectation.getPondAnnee3() + "");
		String pondDeux = deux.getText();
		if(pondDeux.matches("-?\\d+")) {
			Affectation.setPondAnnee2(Integer.parseInt(pondDeux));
			System.out.println("Pondération des 2ème années modifiée avec succès.");
			deux.setText("");
		}
		deuxLab.setText(Affectation.getPondAnnee2() + "");
		String pondSup = tutSup.getText();
		if(pondSup.matches("-?\\d+")) {
			Affectation.setMoyenneSup(Integer.parseInt(pondSup));
			System.out.println("Pondération moyenne supérieure modifiée avec succès.");
			tutSup.setText("");
		}
		supLab.setText(Affectation.getMoyenneSup() + "");
	}

	@FXML
	public void initializeTab(ActionEvent event) throws IOException {
		System.out.println("Chargement des tableaux...");
		if(!flagDel && !initialize) {
			ArrayList<ObservableList> etudiants = affectation.initialisation();
			ObservableList<ObjectTab> affecte = etudiants.get(0);
			ObservableList<Etudiant> nonAffecte = etudiants.get(1);
			TableColumn<Etudiant, String> fonction = new TableColumn<Etudiant, String>("Fonction");
			TableColumn<Etudiant, String> nom = new TableColumn<Etudiant, String>("Nom");
			TableColumn<Etudiant, String> prenom = new TableColumn<Etudiant, String>("Prenom");
			TableColumn<Etudiant, Integer> annee = new TableColumn<Etudiant, Integer>("Annee");
			nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
			prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
			fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
			annee.setCellValueFactory(new PropertyValueFactory<>("annee"));
			tableDeux.setItems(nonAffecte);
			tableDeux.getColumns().addAll(fonction, nom, prenom, annee);
			nom.setMinWidth(-1);
			nom.setPrefWidth(150);
			prenom.setMinWidth(-1);
			prenom.setPrefWidth(150);
			annee.setMinWidth(-1);
			annee.setPrefWidth(150);
			fonction.setMinWidth(-1);
			fonction.setPrefWidth(150);
			nom.setResizable(false);
			prenom.setResizable(false);
			annee.setResizable(false);
			fonction.setResizable(false);
			nom.setSortable(false);
			prenom.setSortable(false);
			annee.setSortable(false);
			fonction.setSortable(false);
			TableColumn<ObjectTab, String> tutores = new TableColumn<ObjectTab, String>("Tutores");
			TableColumn<ObjectTab, String> tuteurs = new TableColumn<ObjectTab, String>("Tuteurs");
			tutores.setCellValueFactory(new PropertyValueFactory<>("tutore"));
			tuteurs.setCellValueFactory(new PropertyValueFactory<>("tuteur"));
			tuteurs.setMinWidth(-1);
			tuteurs.setPrefWidth(300);
			tutores.setMinWidth(-1);
			tutores.setPrefWidth(300);
			tuteurs.setResizable(false);
			tutores.setResizable(false);
			tuteurs.setSortable(false);
			tutores.setSortable(false);
			table.setItems(affecte);
			table.getColumns().addAll(tuteurs, tutores);
			initialize = true;
		}
		if(!initialize && flagDel) {
			ArrayList<ObservableList> etudiants = affectation.initialisationDelete(nonAffecte, nomTuteurs, nomTutores);
			ObservableList<ObjectTab> affecte = etudiants.get(0);
			ObservableList<Etudiant> nonAffecte = etudiants.get(1);
			TableColumn<Etudiant, String> fonction = new TableColumn<Etudiant, String>("Fonction");
			TableColumn<Etudiant, String> nom = new TableColumn<Etudiant, String>("Nom");
			TableColumn<Etudiant, String> prenom = new TableColumn<Etudiant, String>("Prenom");
			TableColumn<Etudiant, Integer> annee = new TableColumn<Etudiant, Integer>("Annee");
			nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
			prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
			fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
			annee.setCellValueFactory(new PropertyValueFactory<>("annee"));
			tableDeux.setItems(nonAffecte);
			tableDeux.getColumns().addAll(fonction, nom, prenom, annee);
			nom.setMinWidth(-1);
			nom.setPrefWidth(150);
			prenom.setMinWidth(-1);
			prenom.setPrefWidth(150);
			annee.setMinWidth(-1);
			annee.setPrefWidth(150);
			fonction.setMinWidth(-1);
			fonction.setPrefWidth(150);
			nom.setResizable(false);
			prenom.setResizable(false);
			annee.setResizable(false);
			fonction.setResizable(false);
			nom.setSortable(false);
			prenom.setSortable(false);
			annee.setSortable(false);
			fonction.setSortable(false);
			TableColumn<ObjectTab, String> tutores = new TableColumn<ObjectTab, String>("Tutores");
			TableColumn<ObjectTab, String> tuteurs = new TableColumn<ObjectTab, String>("Tuteurs");
			tutores.setCellValueFactory(new PropertyValueFactory<>("tutore"));
			tuteurs.setCellValueFactory(new PropertyValueFactory<>("tuteur"));
			tuteurs.setMinWidth(-1);
			tuteurs.setPrefWidth(300);
			tutores.setMinWidth(-1);
			tutores.setPrefWidth(300);
			tuteurs.setResizable(false);
			tutores.setResizable(false);
			tuteurs.setSortable(false);
			tutores.setSortable(false);
			table.setItems(affecte);
			table.getColumns().addAll(tuteurs, tutores);
			initialize = true;
		}
	}

	public void modifTuteurhandler(ActionEvent event) throws IOException {
		if(!nomTutores.contains(nomTutoreEntree.getText()) && !nomTuteurs.contains(etudiant.getNom())) {
			nomTutores.add(nomTutoreEntree.getText());
			nomTuteurs.add(etudiant.getNom());
		} else {
			validationTuteurCouple.setText("Impossible");
		}
	}
	
	public void modifTutorehandler(ActionEvent event) throws IOException {
		if(!nomTutores.contains(etudiant.getNom()) && !nomTuteurs.contains(nomTuteurEntree.getText()) && !nonAffecte.contains(etudiant)) {
			nomTutores.add(etudiant.getNom());
			nomTuteurs.add(nomTuteurEntree.getText());
		} else {
			validationTutoreCouple.setText("Impossible");
		}
	}

	public void switchToPageDetailCouple(MouseEvent event) {
		try{
			root = FXMLLoader.load(getClass().getResource("interfaceDetailCouple.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			String css = this.getClass().getResource("detailTuteur.css").toExternalForm();
			scene.getStylesheets().add(css);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteEtu(ActionEvent event) throws IOException {
		if(!nonAffecte.contains(etudiant) && !(nomTuteurs.contains(etudiant.getNom()) || nomTutores.contains(etudiant.getNom()))) {
			flagDel = true;
			nonAffecte.add(etudiant);
			if(nomTutoreB != null) {
				validationTutoreDel.setText("Supprime");
			} else if(nomTuteurB != null) {
				validationTuteurDel.setText("Supprime");
			}
		} else {
			if(nomTutoreB != null) {
				validationTutoreDel.setText("Impossible");
			} else if(nomTuteurB != null) {
				validationTuteurDel.setText("Impossible");
			}
		}
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public void setEtudiantDeux(Etudiant etudiant) {
		this.etudiantDeux = etudiant;
	}
	
	public Etudiant getEtudiantDeux() {
		return this.etudiantDeux;
	}
}