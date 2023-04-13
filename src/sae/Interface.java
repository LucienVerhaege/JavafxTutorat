package sae;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Interface extends Application {
	
	private static Etudiant etu;

	public void start(Stage stage) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("interfaceMain.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Affections");
			stage.setResizable(false);
			String css = this.getClass().getResource("main.css").toExternalForm();
			scene.getStylesheets().add(css);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}