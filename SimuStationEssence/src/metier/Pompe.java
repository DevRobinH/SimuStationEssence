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
	/** Num�ro de pompe*/
	private int numero;

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
		this.numero = 0;
		this.client = null;
		this.tempsService = 0;
		this.heureDebutService = 0;
	}

	public Pompe(int numero) {
		this.numero = numero;
		this.client = null;
		this.tempsService = 0;
		this.heureDebutService = 0;
	}

	/**
	 * Cr�ation d'une pompe � essence
	 * @param numero numero de la pompe
	 * @param client  le client qui utilise la pompe
	 * @param tempsService temps que prend le client pour se servir � la pompe
	 * @param heureDebutService heure � laquelle le client commence � se servire de la pompe
	 */ 
	public Pompe(int numero, Voiture client, double tempsService, double heureDebutService) {
		super();
		this.numero = numero;
		this.client = client;
		this.tempsService = tempsService;
		this.heureDebutService = heureDebutService;
	}

	/**
	 * Modification de la pompe apr�s passage client
	 */
	public void reinitialiser() {
		this.tempsService = 0;
		this.heureDebutService = 0;
		this.client = null;

	}
	/**
	 * V�rification qu'il n'y est personne � la pome � essence 
	 * @return true s'il n'y a personne
	 */
	public boolean nonUtilise() {
		return this.client == null;
	}

	public boolean isFinish(double temps) {
		return heureDebutService + this.tempsService < temps;
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

	/**
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}


}
