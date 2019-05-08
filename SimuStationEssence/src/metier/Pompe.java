/**
 * 
 */
package metier;

/**
 * Gestionnaire des pompes de la station essence
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class Pompe {
    /** Client qui utilise la pompe � essence */
	private Voiture client;
	
	/** Temps service � la pompe */
	private double tempsService;
	
	/** heure � laquelle commence le service � la pompe */
	private double heureDebutService;

	/**
	 * Pompe par d�faut
	 */
	public Pompe() {
		this.client = null;
		this.tempsService = 0;
		this.heureDebutService = 0;
	}

	/**
	 * Cr�ation d'une pompe � essence
	 * @param client  le client qui utilise la pompe
	 * @param tempsService temps que prend le client pour se servir � la pompe
	 * @param heureDebutService heure � laquelle le client commence � se servire de la pompe
	 */ 
	public Pompe(Voiture client, double tempsService, double heureDebutService) {
		super();
		this.client = client;
		this.tempsService = tempsService;
		this.heureDebutService = heureDebutService;
	}
	
	/**
	 * V�rification qu'il n'y est personne � la pome � essence 
	 * @return true s'il n'y a personne
	 */
	public boolean nonUtilise() {
		return this.client == null;
	}
	
	
	/**
	 * @return the client
	 */
	public Voiture getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Voiture client) {
		this.client = client;
	}

	/**
	 * @return the tempsService
	 */
	public double getTempsService() {
		return tempsService;
	}

	/**
	 * @param tempsService the tempsService to set
	 */
	public void setTempsService(double tempsService) {
		this.tempsService = tempsService;
	}

	/**
	 * @return the heureDebutService
	 */
	public double getHeureDebutService() {
		return heureDebutService;
	}

	/**
	 * @param heureDebutService the heureDebutService to set
	 */
	public void setHeureDebutService(double heureDebutService) {
		this.heureDebutService = heureDebutService;
	}
	
	
}
