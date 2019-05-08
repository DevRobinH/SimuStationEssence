/**
 * 
 */
package metier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestionnaire d'une station essence
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class StationEssence {
    /** cadence d'arrivée des voitures (clients) à la station essence */
	private int lambda;
	
	/** cadence de traitement */
	private int mu;
	
	/** nombre de pompes de la station */
	private int nbPompes;
	
	/** nombre de client arrivant à la station */
	private int nbClients;
	
	/** file d'attente de client */
	private LinkedList<Voiture> fileAttenteClient;
	
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
	
	/** nombre pompes innocupées, fraction d'inactivité */
	private double nbSi;
	
	/** Facteur de charge */
	private double psi;

	/**
	 * Initialisation d'une station essence
	 */
	public StationEssence() {
		super();
		this.nbPompes = 0;
		this.nbClients = 0;
		this.fileAttenteClient = new LinkedList<>();
		this.listeTempsSortie = new ArrayList<>();
		this.listeTempsEntree = new ArrayList<>();
		this.nbS = 0.0;
		this.taS = 0.0;
		this.nbF = 0.0;
		this.nbSi = 0.0;
		this.taF = 0.0;
	}
    /**
     * Calcul du facteur de charge
     * @param lambda  cadence d'arrivées
     * @param mu  cadence de traitement
     * @return psi le facteur de charge
     */
	public double calculPsi(int lambda, int mu) {
		return lambda/mu;
	}
	
	/**
	 * Permet de savoir si le système est ergodique
	 * @return true si psi au nombre de station, false sinon
	 */
	public boolean estErgodique() {
		return this.psi < nbPompes;
	}
	
	/**
	 * Calcul de E(NbS), v.a nombre de clients dans le système
	 * @param psi le facteur de charge
	 * @return nbS
	 */
	public double calculNbS(double psi) {
		return psi/(1 - psi);
	}
	
	/**
	 * Calcul de E(TAS), v.a nombre de clients dans la file
	 * @param lambda cadence d'arrivées
	 * @param mu cadence de traitement
	 * @return taS
	 */
	public double calculTAS(int lambda, int mu) {
		return 1 / (mu - lambda);
	}
	
	/**
	 * Calcul de E(NbF), v.a nombre de client dans la file
	 * @param psi facteur de charge
	 * @return nbF
	 */
	public double calculNbF(double psi) {
		return Math.pow(psi,2) / (1 - psi);
	}
	
	/**
	 * Calcul de E(TAF), v.a temps d'attente dasn la file
	 * @param mu cadence de traitement
	 * @param psi facteur de charge
	 * @return taF
	 */
	public double calculTaF(int mu, double psi) {
		return psi/(mu * (1 - psi));
	}
	
	/**
	 * calcul de E(NbSI), v.a nombre de station inoccupées
	 * @param psi facteur de charge
	 * @return nbSi
	 */
	public double calculNbSI(double psi) {
		return 1 - psi;
	}
	
	/**
	 * @return the lambda
	 */
	public int getLambda() {
		return lambda;
	}

	/**
	 * @param lambda the lambda to set
	 */
	public void setLambda(int lambda) {
		this.lambda = lambda;
	}

	/**
	 * @return the mu
	 */
	public int getMu() {
		return mu;
	}

	/**
	 * @param mu the mu to set
	 */
	public void setMu(int mu) {
		this.mu = mu;
	}

	/**
	 * @return the nbPompes
	 */
	public int getNbPompes() {
		return nbPompes;
	}

	/**
	 * @param nbPompes the nbPompes to set
	 */
	public void setNbPompes(int nbPompes) {
		this.nbPompes = nbPompes;
	}

	/**
	 * @return the nbClients
	 */
	public int getNbClients() {
		return nbClients;
	}

	/**
	 * @param nbClients the nbClients to set
	 */
	public void setNbClients(int nbClients) {
		this.nbClients = nbClients;
	}

	/**
	 * @return the fileAttenteClient
	 */
	public LinkedList<Voiture> getFileAttenteClient() {
		return fileAttenteClient;
	}

	/**
	 * @param fileAttenteClient the fileAttenteClient to set
	 */
	public void setFileAttenteClient(LinkedList<Voiture> fileAttenteClient) {
		this.fileAttenteClient = fileAttenteClient;
	}

	/**
	 * @return the listeTempsSortie
	 */
	public ArrayList<Double> getListeTempsSortie() {
		return listeTempsSortie;
	}

	/**
	 * @param listeTempsSortie the listeTempsSortie to set
	 */
	public void setListeTempsSortie(ArrayList<Double> listeTempsSortie) {
		this.listeTempsSortie = listeTempsSortie;
	}

	/**
	 * @return the listeTempsEntree
	 */
	public ArrayList<Double> getListeTempsEntree() {
		return listeTempsEntree;
	}

	/**
	 * @param listeTempsEntree the listeTempsEntree to set
	 */
	public void setListeTempsEntree(ArrayList<Double> listeTempsEntree) {
		this.listeTempsEntree = listeTempsEntree;
	}

	/**
	 * @return the nbS
	 */
	public double getNbS() {
		return nbS;
	}

	/**
	 * @param nbS the nbS to set
	 */
	public void setNbS(double nbS) {
		this.nbS = nbS;
	}

	/**
	 * @return the taS
	 */
	public double getTaS() {
		return taS;
	}

	/**
	 * @param taS the taS to set
	 */
	public void setTaS(double taS) {
		this.taS = taS;
	}

	/**
	 * @return the nbF
	 */
	public double getNbF() {
		return nbF;
	}

	/**
	 * @param nbF the nbF to set
	 */
	public void setNbF(double nbF) {
		this.nbF = nbF;
	}

	/**
	 * @return the taF
	 */
	public double getTaF() {
		return taF;
	}

	/**
	 * @param taF the taF to set
	 */
	public void setTaF(double taF) {
		this.taF = taF;
	}

	/**
	 * @return the nbSi
	 */
	public double getNbSi() {
		return nbSi;
	}

	/**
	 * @param nbSi the nbSi to set
	 */
	public void setNbSi(double nbSi) {
		this.nbSi = nbSi;
	}

	/**
	 * @return the psi
	 */
	public double getPsi() {
		return psi;
	}

	/**
	 * @param psi the psi to set
	 */
	public void setPsi(double psi) {
		this.psi = psi;
	}
	
	
	
	
}
