/**
 * 
 */
package metier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Gestionnaire d'une station essence
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class StationEssence {

	/** Cadence d'arrivée */
	private double lambda;

	/** Cadence de traitement */
	private double mu;

	/** Nombre de clients du sytème */
	private int nbClients;

	/** nombre de pompe */
	private int nbPompes;

	/** Liste des clients */
	private LinkedList<Voiture> voitures;

	/** File d'attente des clients */
	private LinkedList<Voiture> buffer;

	/**Liste des pompes */
	private ArrayList<Pompe> pompes;

	/** Liste des temps de sortie du système */
	private LinkedList<Double> tempsSorties;

	/** Contenu de la file */
	private LinkedHashMap<Double, Integer> contenuBuffer;

	/** Nombre de personne max dans la file */
	private int nbPersonneMax = 0;

	/** NbS observé */
	private double moyenneVoitureSysteme = 0;

	/** NbF observé */
	private double moyenneVoitureFileAttente = 0;

	/** TaS observé */
	private double tempsMoyenAttenteSysteme = 0;

	/** TaF observé */
	private double tempsMoyenAttenteFile = 0;

	/** nombre moyen de clients dans le système théorique */
	private double nbS;

	/** moyenne temps attente dans le système théorique */
	private double taS;

	/** moyenne nombre clients dans la file théorique */
	private double nbF;

	/** moyenne temps attente dans la file théorique */
	private double taF;

	/** Facteur de charge */
	private double psi;

	public static long compteurTemps = 0;


	/** Initialisation d'une station essence */
	private void initialize() {
		pompes = new ArrayList<Pompe>();
		buffer = new LinkedList<Voiture>();
		voitures = new LinkedList<Voiture>();
		tempsSorties = new LinkedList<Double>();
		contenuBuffer = new LinkedHashMap<Double, Integer>();
		moyenneVoitureSysteme = 0;
		moyenneVoitureFileAttente = 0;
		tempsMoyenAttenteSysteme = 0;
		tempsMoyenAttenteFile = 0;
		compteurTemps = 0;
		nbPersonneMax = 0;
		psi = 0;
	}


	/**
	 * Simulation du système station essence
	 * @param markov, true simulation en mode markovien, false sinon
	 */
	public void simulation(boolean markov) {
		initialize();        
        this.psi = calculPsi();
		// Tirage des Voitures
		double temps = 0;
		for (int i = 0; i < nbClients; i++) {
			temps += markov ? GenerationLois.loiExponentielle(lambda) : Math.abs(GenerationLois.loiNormale(1.0/lambda,1.0/lambda));
			voitures.add(new Voiture(temps));
		}

		for (int i = 0; i < nbPompes; i++) {
			pompes.add(new Pompe(i));
		}

		temps = 0;
		while (voitures.size() > 0) {
			int nbClientsysteme = 0;

			// Pour chacune des Pompes
			for (int i = 0; i < pompes.size(); i++) {
				Pompe Pompe = pompes.get(i);
				if (Pompe.nonUtilise() && !buffer.isEmpty()) {
					// On va chercher un Voiture
					Pompe.setClient(buffer.removeFirst());
					Pompe.setHeureDebutService(temps);
					Pompe.setTempsService(markov ? GenerationLois.loiExponentielle(mu) : GenerationLois.loiExponentielle(mu));
					tempsMoyenAttenteFile += temps - Pompe.getClient().getHeureArrivee();
				} else if (!Pompe.nonUtilise() && Pompe.isFinish(temps)) {
					// Le Voiture a été traité
					tempsSorties.add(temps);
					tempsMoyenAttenteSysteme += temps - Pompe.getClient().getHeureArrivee();
					Pompe.reinitialiser();
					i--;
				}
			}

			for (Pompe Pompe : pompes) {
				if (!Pompe.nonUtilise()) {
					nbClientsysteme++;
				}
			}

			// On regarde les Voitures arrivants
			while (voitures.size() > 0 &&
					temps >= voitures.getFirst().getHeureArrivee()) {
				buffer.add(voitures.removeFirst());
				contenuBuffer.put(temps, buffer.size());
				nbPersonneMax = buffer.size() > nbPersonneMax ? 
						buffer.size() : nbPersonneMax;
			}

			nbClientsysteme += buffer.size();

			moyenneVoitureFileAttente += buffer.size();
			moyenneVoitureSysteme += nbClientsysteme;
			compteurTemps++;
			temps += 0.00001;
		}


		moyenneVoitureFileAttente /= compteurTemps;
		moyenneVoitureSysteme /= compteurTemps;
		tempsMoyenAttenteFile /= nbClients;
		tempsMoyenAttenteSysteme /= nbClients;
		System.out.println("Moyenne de Voitures dans la file d'attente : " + moyenneVoitureFileAttente);
		System.out.println("Moyenne de Voitures dans le système : " + moyenneVoitureSysteme);
		System.out.println("Temps moyen dans la file d'attente : " + tempsMoyenAttenteFile);
		System.out.println("Temps moyen dans le système : " + tempsMoyenAttenteSysteme);
		temps = 0;
	}

	/**
	 * Calcul du facteur de charge
	 * @param lambda  cadence d'arrivées
	 * @param mu  cadence de traitement
	 * @return psi le facteur de charge
	 */
	public double calculPsi() {
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
	 * Determination de la factorielle d'un nombre
	 * @param nombre
	 * @return
	 */
	public double factorielle(double nombre) {
		int fac = 1;
		for (double i = nombre; i > 1; i--) {
			fac *= i;
		}
		return fac;
	}
	/**
	 * Calcul du po*
	 * @return po*
	 */
	public double po() {
		double valueBas = 0;
		for (double i = 0; i <= nbPompes - 1; i++) {
			valueBas += (Math.pow(psi, i) / factorielle(i));
		}
		return 1 / (valueBas + (Math.pow(psi, nbPompes))
				/ (factorielle(nbPompes) * (1 - psi / nbPompes)));
	}

	/**
	 * Calcul de E(NbS), v.a nombre de clients dans le système
	 * @param psi le facteur de charge
	 * @return nbS
	 */
	public double calculNbS() {
		// MM1
		if(nbPompes == 1) {
			return psi/(1 - psi);
			// MMS
		} else {
			return psi + (nbPompes / Math.pow(nbPompes - psi, 2))
					* ((Math.pow(psi, nbPompes + 1)) / factorielle(nbPompes)) * po();
		}
	}

	/**
	 * Calcul de E(TAS), v.a nombre de clients dans la file
	 * @param lambda cadence d'arrivées
	 * @param mu cadence de traitement
	 * @return taS
	 */
	public double calculTAS() {
		// MM1
		if(nbPompes == 1) {
			return 1 / (mu - lambda);
			// MMS
		} else {
			return calculNbS() / lambda;
		}
	}

	/**
	 * Calcul de E(NbF), v.a nombre de client dans la file
	 * @param psi facteur de charge
	 * @return nbF
	 */
	public double calculNbF() {
		// MM1
		if (nbPompes == 1) {
			return Math.pow(psi,2) / (1 - psi);
			// MM2
		} else {
			return (nbPompes / Math.pow(nbPompes - psi, 2))
					* ((Math.pow(psi, nbPompes + 1)) / factorielle(nbPompes)) * po();
		}
	}

	/**
	 * Calcul de E(TAF), v.a temps d'attente dasn la file
	 * @param mu cadence de traitement
	 * @param psi facteur de charge
	 * @return taF
	 */
	public double calculTaF() {
		// MM1
		if (nbPompes == 1) {
			return psi/(mu * (1 - psi));
			// MMS
		} else {
			return calculNbF() / lambda;
		}
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
	 * @return the voitures
	 */
	public LinkedList<Voiture> getVoitures() {
		return voitures;
	}


	/**
	 * @param voitures the voitures to set
	 */
	public void setVoitures(LinkedList<Voiture> voitures) {
		this.voitures = voitures;
	}


	/**
	 * @return the buffer
	 */
	public LinkedList<Voiture> getBuffer() {
		return buffer;
	}


	/**
	 * @param buffer the buffer to set
	 */
	public void setBuffer(LinkedList<Voiture> buffer) {
		this.buffer = buffer;
	}


	/**
	 * @return the pompes
	 */
	public ArrayList<Pompe> getPompes() {
		return pompes;
	}


	/**
	 * @param pompes the pompes to set
	 */
	public void setPompes(ArrayList<Pompe> pompes) {
		this.pompes = pompes;
	}


	/**
	 * @return the tempsSorties
	 */
	public LinkedList<Double> getTempsSorties() {
		return tempsSorties;
	}


	/**
	 * @param tempsSorties the tempsSorties to set
	 */
	public void setTempsSorties(LinkedList<Double> tempsSorties) {
		this.tempsSorties = tempsSorties;
	}


	/**
	 * @return the contenuBuffer
	 */
	public LinkedHashMap<Double, Integer> getContenuBuffer() {
		return contenuBuffer;
	}


	/**
	 * @param contenuBuffer the contenuBuffer to set
	 */
	public void setContenuBuffer(LinkedHashMap<Double, Integer> contenuBuffer) {
		this.contenuBuffer = contenuBuffer;
	}


	/**
	 * @return the nbPersonneMax
	 */
	public int getNbPersonneMax() {
		return nbPersonneMax;
	}


	/**
	 * @param nbPersonneMax the nbPersonneMax to set
	 */
	public void setNbPersonneMax(int nbPersonneMax) {
		this.nbPersonneMax = nbPersonneMax;
	}


	/**
	 * @return the moyenneVoitureSysteme
	 */
	public double getMoyenneVoitureSysteme() {
		return moyenneVoitureSysteme;
	}


	/**
	 * @param moyenneVoitureSysteme the moyenneVoitureSysteme to set
	 */
	public void setMoyenneVoitureSysteme(double moyenneVoitureSysteme) {
		this.moyenneVoitureSysteme = moyenneVoitureSysteme;
	}


	/**
	 * @return the moyenneVoitureFileAttente
	 */
	public double getMoyenneVoitureFileAttente() {
		return moyenneVoitureFileAttente;
	}


	/**
	 * @param moyenneVoitureFileAttente the moyenneVoitureFileAttente to set
	 */
	public void setMoyenneVoitureFileAttente(double moyenneVoitureFileAttente) {
		this.moyenneVoitureFileAttente = moyenneVoitureFileAttente;
	}


	/**
	 * @return the tempsMoyenAttenteSysteme
	 */
	public double getTempsMoyenAttenteSysteme() {
		return tempsMoyenAttenteSysteme;
	}


	/**
	 * @param tempsMoyenAttenteSysteme the tempsMoyenAttenteSysteme to set
	 */
	public void setTempsMoyenAttenteSysteme(double tempsMoyenAttenteSysteme) {
		this.tempsMoyenAttenteSysteme = tempsMoyenAttenteSysteme;
	}


	/**
	 * @return the tempsMoyenAttenteFile
	 */
	public double getTempsMoyenAttenteFile() {
		return tempsMoyenAttenteFile;
	}


	/**
	 * @param tempsMoyenAttenteFile the tempsMoyenAttenteFile to set
	 */
	public void setTempsMoyenAttenteFile(double tempsMoyenAttenteFile) {
		this.tempsMoyenAttenteFile = tempsMoyenAttenteFile;
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
	 * @return the compteurTemps
	 */
	public static long getCompteurTemps() {
		return compteurTemps;
	}


	/**
	 * @param compteurTemps the compteurTemps to set
	 */
	public static void setCompteurTemps(long compteurTemps) {
		StationEssence.compteurTemps = compteurTemps;
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
