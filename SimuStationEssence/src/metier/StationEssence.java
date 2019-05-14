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
	private double lambda;

	/** cadence de traitement */
	private double mu;

	/** nombre de pompes de la station */
	private double nbPompes;

	/** nombre de client arrivant à la station */
	private int nbClients;

	/** file d'attente de client */
	private LinkedList<Voiture> fileAttenteClient;

	/** liste stockant les temps de sortie des clients*/
	private ArrayList<Double> listeTempsSortie;

	/** liste stockant les temps d'entrée des clients*/
	private ArrayList<Double> listeTempsEntree;

	/** Liste du tems d'attnte de chaque client avant d'arrivé à la pompe */
	private ArrayList<Double> listeTempsAttenteClients;

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
	 * Initialisation d'une station essence sans paramètre
	 */
	public StationEssence() {
		super();
		this.nbPompes = 0;
		this.nbClients = 0;
		this.fileAttenteClient = new LinkedList<>();
		this.listeTempsSortie = new ArrayList<>();
		this.listeTempsEntree = new ArrayList<>();
		this.listeTempsAttenteClients = new ArrayList<>();
		this.nbS = 0.0;
		this.taS = 0.0;
		this.nbF = 0.0;
		this.nbSi = 0.0;
		this.taF = 0.0;
		this.psi = 0.0;
	}
	/**
	 * Calcul du facteur de charge
	 * @param lambda  cadence d'arrivées
	 * @param mu  cadence de traitement
	 * @return psi le facteur de charge
	 */
	public double calculPsi(double lambda, double mu) {
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
	public double calculTAS(double lambda, double mu) {
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
	public double calculTaF(double mu, double psi) {
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
	 * Simulation en mode markovien
	 * @param lambda Cadence d'arrivée du phénomène
	 * @param mu Cadence de traitement du système
	 * @param nbStation nombre de pompe du système
	 * @param nbClient nombre de personne venant se ravitailler en essence
	 */
	public void chaineMarkovienne(double lambda, double mu, int nbStation, int nbClient) {
		if (nbStation == 1) {
			mm1(lambda, mu, nbClient);
		} else {
			mms(lambda, mu, nbStation, nbClient);
		}
	}

	/**
	 * Simulation en mode markovien en mode mm1
	 * @param lambda Cadence d'arrivée du phénomène
	 * @param mu Cadence de traitement du système
	 * @param nbClient nombre de personne venant se ravitailler en essence
	 */
	private void mm1 (double lambda, double mu, int nbClient) {
		// Définition des paramètres
		this.lambda = lambda;
		this.mu = mu;
		this.nbClients = nbClient;

		// Calcul de psi 
		this.psi = calculPsi(lambda, mu);

		// file d'attente
		this.fileAttenteClient = new LinkedList<>();
		// Génération des clients de la station
		for (int i = 0; i < nbClient; i ++) {
			if (i == 0) {
				this.fileAttenteClient.add(new Voiture(GenerationLois.loiExponentielle(lambda)));
				// Ajout du temps d'arrivé à la liste des temps d'arrivés
				this.listeTempsEntree.add(this.fileAttenteClient.get(i).getHeureArrivee());
			} else {
				this.fileAttenteClient.add(new Voiture(GenerationLois.loiExponentielle(lambda)+fileAttenteClient.get(i-1).getHeureArrivee()));
				// Ajout du temps d'arrivé à la liste des temps d'arrivés
				this.listeTempsEntree.add(this.fileAttenteClient.get(i).getHeureArrivee());
			}
		}
		// génération de la station essence
		Pompe pompeEss = new Pompe();

		//Calcul des moyennes
		this.setNbS(calculNbS(psi));
		this.setNbF(calculNbF(this.psi));
		this.setTaF(calculTaF(mu, this.psi));
		this.setTaS(calculTAS(lambda, mu));

	}

	private void mms(double lambda2, double mu2, int nbStation, int nbClient) {

	}


	/**
	 * @return the lambda
	 */
	public double getLambda() {
		return lambda;
	}

	/**
	 * @param lambda the lambda to set
	 */
	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	/**
	 * @return the mu
	 */
	public double getMu() {
		return mu;
	}

	/**
	 * @param mu the mu to set
	 */
	public void setMu(double mu) {
		this.mu = mu;
	}

	/**
	 * @return the nbPompes
	 */
	public double getNbPompes() {
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
