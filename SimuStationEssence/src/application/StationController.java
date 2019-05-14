package application;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import metier.LineChartWithMarkers;

public class StationController {
	
	@FXML
	TextField TF_lambda;
	
	@FXML
	TextField TF_mu;
	
	@FXML
	TextField TF_nbStations;
	
	@FXML
	TextField TF_nbClients;
	
	@FXML
	Button BT_simuler;
	
	@FXML
	Button BT_arreter;
	
	@FXML
	Label LB_psy;
	
	@FXML
	Label LB_signe;
	
	@FXML
	Label LB_nbMaxErgo;
	
	@FXML
	Label LB_NbS;
	
	@FXML
	Label LB_TAS;
	
	@FXML
	Label LB_NbF;
	
	@FXML
	Label LB_TAF;
	
	@FXML
	Label LB_NbSI;
	
	@FXML
	Label LB_ergodique;
	
	@FXML
	Label LB_MM;
	
	@FXML
	Label LB_lambda;
	
	@FXML
	Label LB_mu;
	
	@FXML
	Label LB_nbPompes;
	
	@FXML
	private VBox vb_voitures_entrantes = new VBox();
	
	@FXML
	private VBox vb_voitures_sortantes = new VBox();	
	
	@FXML
	private VBox vb_voitures_file = new VBox();
	
	final NumberAxis xAxis_entrantes = new NumberAxis();
    final NumberAxis yAxis_entrantes  = new NumberAxis();
    
	final NumberAxis xAxis_sortantes  = new NumberAxis();
    final NumberAxis yAxis_sortantes  = new NumberAxis();
    
	final NumberAxis xAxis_file = new NumberAxis();
    final NumberAxis yAxis_file = new NumberAxis();
    
	@FXML
	LineChartWithMarkers<Number, Number> lc_voitures_entrantes = new LineChartWithMarkers<Number, Number>(xAxis_entrantes, yAxis_entrantes);

	@FXML
	LineChartWithMarkers<Number, Number> lc_voitures_sortantes = new LineChartWithMarkers<Number, Number>(xAxis_sortantes, yAxis_sortantes);
	
	@FXML
	LineChart<Number, Number> lc_voitures_file = new LineChart<Number, Number>(xAxis_file, yAxis_file);

	// Définition des radio boutons
	@FXML
	RadioButton bt_Markov = new RadioButton();
	@FXML
	RadioButton bt_NonMarkov = new RadioButton();
			
			
	// Appelé au lancement de l'application
	@FXML
	private void initialize(){
		
		// Valeurs par défauts
		TF_lambda.setText("20");
		TF_mu.setText("20");
		TF_nbStations.setText("6");
		TF_nbClients.setText("3");
		
		// Le lineChart est ajouté dans la Vbox
		vb_voitures_entrantes.getChildren().add(lc_voitures_entrantes);
		vb_voitures_sortantes.getChildren().add(lc_voitures_sortantes);
		vb_voitures_file.getChildren().add(lc_voitures_file);
		
		/* Config du lineChart */
		
		// Pas de légende
		lc_voitures_entrantes.setLegendVisible(false);
		lc_voitures_sortantes.setLegendVisible(false);
		lc_voitures_file.setLegendVisible(false);
		
		// Pas de grille horizontales et verticales
		lc_voitures_entrantes.setHorizontalGridLinesVisible(false);
		lc_voitures_entrantes.setVerticalGridLinesVisible(false);
		
		lc_voitures_sortantes.setHorizontalGridLinesVisible(false);
		lc_voitures_sortantes.setVerticalGridLinesVisible(false);
		
		lc_voitures_file.setHorizontalGridLinesVisible(false);
		lc_voitures_file.setVerticalGridLinesVisible(false);
		
		// Plage sur l'axe des abscisses
		xAxis_entrantes.setAutoRanging(false);
		xAxis_sortantes.setAutoRanging(false);
		xAxis_file.setAutoRanging(false);
		
		xAxis_entrantes.setUpperBound(10);
		xAxis_sortantes.setUpperBound(10);
		xAxis_file.setUpperBound(10);
		
		xAxis_entrantes.setLowerBound(0);
		xAxis_sortantes.setLowerBound(0);
		xAxis_file.setLowerBound(0);
		
		// Plage sur l'axe des ordonnées
		yAxis_entrantes.setAutoRanging(false);
		yAxis_entrantes.setUpperBound(1);
		yAxis_entrantes.setLowerBound(0);
		
		// Plage sur l'axe des ordonnées
		yAxis_sortantes.setAutoRanging(false);
		yAxis_sortantes.setUpperBound(1);
		yAxis_sortantes.setLowerBound(0);
		
		// Plage sur l'axe des ordonnées
		yAxis_file.setAutoRanging(false);
		yAxis_file.setUpperBound(1);
		yAxis_file.setLowerBound(0);
		
		// Radio boutons
		ToggleGroup toggleGroup = new ToggleGroup();

		bt_Markov.setToggleGroup(toggleGroup);
		bt_NonMarkov.setToggleGroup(toggleGroup);

		// Récupération de la valeur du radio bouton
		toggleGroup.getSelectedToggle();
	}

}

