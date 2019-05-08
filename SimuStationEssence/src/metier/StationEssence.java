/**
 * 
 */
package metier;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Gestionnaire d'une station essence
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class StationEssence {
    /**cadence d'arrivée des voitures (clients) à la station essence */
	private int lambda;
	
	/**cadence de traitement */
	private int mu;
	
	/** nombre de pompes de la station */
	private int nbPompes;
	
	/** nombre de client arrivant à la station */
	private int nbClients;
	
	/** file d'attente de client */
	private Queue<Voiture> fileAttenteClient;
	
	/** liste stockant les temps de sortie des clients*/
	private ArrayList<Double> listeTempsSortie;
	
	/** liste stockant les temps d'entrée des clients*/
	private ArrayList<Double> listeTempsEntree;
	
	/** nombre moyen de clients dans le système */
	private double nbS;
	
	/** moyenne temps attente dans le système */
	private double taS;
	
	/** moyenne nombre clients dans la file */
	private double nbF;
	
	/** moyenne temps attente dans la file */
	private double taF;
	
	/**Facteur de charge */
	private double psi;
}
