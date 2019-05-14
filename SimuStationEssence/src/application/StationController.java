package application;

import javafx.event.ActionEvent;
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

	// D�finition des radio boutons
	@FXML
	RadioButton bt_Markov = new RadioButton();
	@FXML
	RadioButton bt_NonMarkov = new RadioButton();
			
			
	// Appel� au lancement de l'application
	@FXML
	private void initialize(){
		
		// V�rouillage/d�v�rouillage des boutons
		BT_simuler.setDisable(false);
		BT_arreter.setDisable(true);
		
		// On active le bouton Markovien
		bt_Markov.setSelected(true);
		
		// Valeurs par d�fauts
		TF_lambda.setText("20");
		TF_mu.setText("20");
		TF_nbStations.setText("6");
		TF_nbClients.setText("3");
		
		// Le lineChart est ajout� dans la Vbox
		vb_voitures_entrantes.getChildren().add(lc_voitures_entrantes);
		vb_voitures_sortantes.getChildren().add(lc_voitures_sortantes);
		vb_voitures_file.getChildren().add(lc_voitures_file);
		
		/* Config du lineChart */
		
		// Pas de l�gende
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
		
		// Plage sur l'axe des ordonn�es
		yAxis_entrantes.setAutoRanging(false);
		yAxis_entrantes.setUpperBound(1);
		yAxis_entrantes.setLowerBound(0);
		
		// Plage sur l'axe des ordonn�es
		yAxis_sortantes.setAutoRanging(false);
		yAxis_sortantes.setUpperBound(1);
		yAxis_sortantes.setLowerBound(0);
		
		// Plage sur l'axe des ordonn�es
		yAxis_file.setAutoRanging(false);
		yAxis_file.setUpperBound(1);
		yAxis_file.setLowerBound(0);
		
		// Radio boutons
		ToggleGroup toggleGroup = new ToggleGroup();

		bt_Markov.setToggleGroup(toggleGroup);
		bt_NonMarkov.setToggleGroup(toggleGroup);

		// R�cup�ration de la valeur du radio bouton
		toggleGroup.getSelectedToggle();
	}
	
	/**
	 * D�marre la simulation
	 * 
	 * @param Evenement au clic du bouton "Simuler"
	 */
	public void actionSimuler(ActionEvent evt){

		System.out.println("\n bt Simuler");
		
		// On v�rouille le bouton simuler
		BT_simuler.setDisable(true);
		
		// On d�v�rouille le bouton Arreter
		BT_arreter.setDisable(false);
		
		// On vide le graphe
		//clearChart();
		
		// R�cup�ration des param�tres saisis
		recupLambda();
		recupMu();
		recupNbStations();
		recupNbClients();
		recupRadioBt();
		
		// Ins�re les donn�es dans le graphe
		//insertData();
	}
	
	/**
	 * Stoppe le programme
	 * 
	 * @param Evenement au clic du bouton "Arr�ter"
	 */
	public void actionArreter(ActionEvent evt){

		System.out.println("\n bt Arr�ter");
		
		// V�rouillage/d�v�rouillage des boutons
		BT_simuler.setDisable(false);
		BT_arreter.setDisable(true);
		
		//clearChart();
	}

	/**
	 * R�cup�re le lambda
	 */
	public void recupLambda(){

		// On r�cup�re le champ
		String recup = TF_lambda.getText();

		// Si le champ � r�cup�rer n'est pas null
		if (!recup.equals("")){

			System.out.println("\nLambda:");
			System.out.println(recup);				
		}
		else{
			System.out.println("\nErreur r�cup�ration Lambda");
		}
	}
	
	/**
	 * R�cup�re Mu
	 */
	public void recupMu(){

		// On r�cup�re le champ
		String recup = TF_mu.getText();

		// Si le champ � r�cup�rer n'est pas null
		if (!recup.equals("")){

			System.out.println("\nMu:");
			System.out.println(recup);				
		}
		else{
			System.out.println("\nErreur r�cup�ration Mu");
		}
	}
	
	/**
	 * R�cup�re le nombre de stations
	 */
	public void recupNbStations(){

		// On r�cup�re le champ
		String recup = TF_nbStations.getText();

		// Si le champ � r�cup�rer n'est pas null
		if (!recup.equals("")){

			System.out.println("\nNb stations:");
			System.out.println(recup);				
		}
		else{
			System.out.println("\nErreur r�cup�ration du nombre de stations");
		}
	}
	
	/**
	 * R�cup�re le nombre de clients
	 */
	public void recupNbClients(){

		// On r�cup�re le champ
		String recup = TF_nbClients.getText();

		// Si le champ � r�cup�rer n'est pas null
		if (!recup.equals("")){

			System.out.println("\nNb Clients:");
			System.out.println(recup);				
		}
		else{
			System.out.println("\nErreur r�cup�ration du nombre de clients");
		}
	}
	
	/**
	 * R�cup�re le nombre de stations
	 */
	public void recupRadioBt(){

		// On r�cup�re le champ
		String recup = TF_nbStations.getText();

		// Si le champ � r�cup�rer n'est pas null
		if (!recup.equals("")){

			System.out.println("\nNb Stations:");
			System.out.println(recup);				
		}
		else{
			System.out.println("\nErreur r�cup�ration du nombre de stations");
		}
	}
	
}

