package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import metier.LineChartWithMarkers;
import metier.StationEssence;

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
	Label LB_NbS_th;
	
	@FXML
	Label LB_NbS_obs;
	
	@FXML
	Label LB_TAS_th;
	
	@FXML
	Label LB_TAS_obs;
	
	@FXML
	Label LB_NbF_th;
	
	@FXML
	Label LB_NbF_obs;
	
	@FXML
	Label LB_TAF_th;
	
	@FXML
	Label LB_TAF_obs;
	
	@FXML
	Label LB_NbSI_th;
	
	@FXML
	Label LB_NbSI_obs;
	
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
		
		// On masque les labels sans valeurs pour l'instant
		afficherLabels(false);
		
		// Valeurs par d�fauts
		TF_lambda.setText("15");
		TF_mu.setText("20");
		TF_nbStations.setText("1");
		TF_nbClients.setText("350");
		
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

		System.out.println("\nBt Simuler");
		
		// On v�rouille le bouton simuler
		BT_simuler.setDisable(true);
		
		// On d�v�rouille le bouton Arreter
		BT_arreter.setDisable(false);
		
		// On vide le graphe
		clearChart();
		
		// R�cup�ration des param�tres saisis
		double unLambda = recupLambda();
		double unMu = recupMu();
		int unNbStation = recupNbStations();
		int unNbClients = recupNbClients();
		boolean choixMarkov = recupRadioBt();
		
		// Met � jour le sh�ma
		updateSchema(unLambda, unMu, unNbStation);
		
		// Ins�re les donn�es dans le graphe
		insertData(unLambda, unMu, unNbStation, unNbClients, choixMarkov);
		
		// Affichage des labels cach�s
		afficherLabels(true);
	}
	
	/**
	 * Stoppe le programme
	 * 
	 * @param Evenement au clic du bouton "Arr�ter"
	 */
	public void actionArreter(ActionEvent evt){

		System.out.println("\nBt Arr�ter");
		
		// V�rouillage/d�v�rouillage des boutons
		BT_simuler.setDisable(false);
		BT_arreter.setDisable(true);

	}

	/**
	 * R�cup�re le lambda
	 */
	public double recupLambda(){

		// On r�cup�re le champ
		double recup = Double.parseDouble(TF_lambda.getText());

		// Si le champ � r�cup�rer est null
		if (recup == 0.0){
			System.out.println("\nErreur r�cup�ration Lambda");
		}
		
		return recup;
	}
	
	/**
	 * R�cup�re Mu
	 */
	public double recupMu(){

		// On r�cup�re le champ
		double recup = Double.parseDouble(TF_mu.getText());

		// Si le champ � r�cup�rer est null
		if (recup == 0.0){
			System.out.println("\nErreur r�cup�ration Mu");
		}
		
		return recup;
	}
	
	/**
	 * R�cup�re le nombre de stations
	 */
	public int recupNbStations(){

		// On r�cup�re le champ
		int recup = Integer.parseInt(TF_nbStations.getText());

		// Si le champ � r�cup�rer est null
		if (recup == 0){
			System.out.println("\nErreur r�cup�ration du nombre de stations");
		}
		
		return recup;
	}
	
	/**
	 * R�cup�re le nombre de clients
	 */
	public int recupNbClients(){

		// On r�cup�re le champ
		int recup = Integer.parseInt(TF_nbClients.getText());

		// Si le champ � r�cup�rer est null
		if (recup == 0){

			System.out.println("\nErreur r�cup�ration du nombre de clients");
		}
		
		return recup;
	}
	
	/**
	 * R�cup�re le nombre de stations
	 */
	public boolean recupRadioBt(){

		// On r�cup�re le champ
		RadioButton chk = (RadioButton)bt_Markov.getToggleGroup().getSelectedToggle(); // Cast object to radio button
        String chkTxt = chk.getText();
		
		boolean estMarkov = false;
		
		// Si le champ � r�cup�rer n'est pas null
		if (!chkTxt.equals("")){

			// Si le bt Markovien est s�lectionn�
			if (chkTxt.equals("Markovien")){
				estMarkov = true;
				System.out.println("\nMarkovien demand�");	
			}else{
				estMarkov = false;
				System.out.println("\nNon Markovien demand�");	
			}
			
		}
		else{
			System.out.println("\nErreur r�cup�ration chk Radio button");
		}
		
		return estMarkov;
	}
	
	/**
	 * Met � jour les informations du sch�ma
	 */
	public void updateSchema(double unLambda, double unMu, int unNbStation){
		
		// Convertion en int
		int recupLambda = (int)unLambda;
		int recupMu = (int)unMu;
		
		LB_lambda.setText(String.valueOf(recupLambda));
		LB_mu.setText(String.valueOf(recupMu));
		
		String pompe = "";
		
		// Affichage diff�rent selon le nombre de pompes
		if(unNbStation == 0 || unNbStation == 1){
			pompe = " pompe";
		}else{
			pompe = " pompes";
		}
		
		LB_nbPompes.setText(String.valueOf(unNbStation) + pompe);
		LB_MM.setText(String.valueOf(unNbStation));

	}
	
	/**
	 * Ins�re des donn�es dans le graphe
	 */
	public void insertData(double lambda, double mu, int nbStations, int nbClients, boolean estMarkov){
		
		System.out.println("\nDonn�es avant insertion :");
		System.out.println(" -Lambda: " + lambda);
		System.out.println(" -Mu: "+ mu);
		System.out.println(" -NbStations: " + nbStations);
		System.out.println(" -NbClients: " + nbClients);
		System.out.println(" -Markovien: " + estMarkov);
		
		// On cr�er une instance de la classe StationEssence
		StationEssence uneStation = new StationEssence();
		uneStation.chaineMarkovienne(lambda, mu, nbStations, nbClients);
		
		/*for(int i =0; i < stEss.getFileAttenteClient().size(); i++) {
			System.out.println(stEss.getListeTempsEntree().get(i));
		}*/	
	
		// Ajout des valeurs th�oriques
		LB_NbS_th.setText(String.format("%.4f", uneStation.getNbS()));				
		LB_TAS_th.setText(String.format("%.4f", uneStation.getTaS()));		
		LB_NbF_th.setText(String.format("%.4f", uneStation.getNbF()));			
		LB_TAF_th.setText(String.format("%.4f", uneStation.getTaF()));		
		LB_NbSI_th.setText(String.format("%.4f", uneStation.getNbSi()));
		
		LB_psy.setText(String.format("%.4f", uneStation.getPsi()));
		
		LB_nbMaxErgo.setText(String.valueOf(nbStations));
		
		// Ajout des valeurs observ�es
		
		
		// Si psy < nbStations
		if(uneStation.getPsi() < nbStations){
			LB_signe.setText("<");
			LB_ergodique.setText("Le syst�me est Ergodique");
		}else {
			LB_signe.setText(">");
			LB_ergodique.setText("Le syst�me n'est pas Ergodique");
		}			
	}

	/**
	 * Trace une barre verticale sur notre graphe
	 * @param lc : lineChart
	 * @param xAxis : axe des abscisses du graphe
	 * @param x : valeur en abscisse
	 * @param y : valeur en ordonn�e
	 */
	public void setVerticalBar(LineChartWithMarkers<Number, Number> lc, NumberAxis xAxis, double x, double y){
		
		// La barre est cr�e selon les param�tres x et y
		Data<Number, Number> verticalBar = new Data<>(x, y);
		
		// Ajout de la barre au graphe
	    lc.addVerticalValueMarker(verticalBar);
	    
	    // Personnalisation
	    Slider verticalBarSlider = new Slider(xAxis.getLowerBound(), xAxis.getUpperBound(), 0);
	    verticalBarSlider.setOrientation(Orientation.HORIZONTAL);
	    verticalBarSlider.setShowTickLabels(true);
	    verticalBarSlider.valueProperty().bindBidirectional(verticalBar.XValueProperty());
	    verticalBarSlider.minProperty().bind(xAxis.lowerBoundProperty());
	    verticalBarSlider.maxProperty().bind(xAxis.upperBoundProperty());
	}
	
	/**
	 * Vide les donn�es des graphes
	 */
	public void clearChart(){

		// Supprime les barres du graphe
		lc_voitures_entrantes.removeAllVerticalMarker();
		lc_voitures_sortantes.removeAllVerticalMarker();		
		lc_voitures_file.getData().removeAll();
				
		System.out.println("\nDonn�es vid�es");
	}
	
	/**
	 * Masque tous les Labels avant chaque clic de "Simuler"
	 */
	public void afficherLabels(boolean isOrNotVisible){

		LB_NbS_th.setVisible(isOrNotVisible);				
		LB_NbS_obs.setVisible(isOrNotVisible);			
		LB_TAS_th.setVisible(isOrNotVisible);			
		LB_TAS_obs.setVisible(isOrNotVisible);			
		LB_NbF_th.setVisible(isOrNotVisible);			
		LB_NbF_obs.setVisible(isOrNotVisible);			
		LB_TAF_th.setVisible(isOrNotVisible);			
		LB_TAF_obs.setVisible(isOrNotVisible);			
		LB_NbSI_th.setVisible(isOrNotVisible);		
		LB_NbSI_obs.setVisible(isOrNotVisible);
		
		LB_psy.setVisible(isOrNotVisible);
		LB_signe.setVisible(isOrNotVisible);
		LB_nbMaxErgo.setVisible(isOrNotVisible);
	
		LB_ergodique.setVisible(isOrNotVisible);
		
		LB_lambda.setVisible(isOrNotVisible);
		LB_mu.setVisible(isOrNotVisible);
		LB_nbPompes.setVisible(isOrNotVisible);
		LB_MM.setVisible(isOrNotVisible);
		
	}
}

