/**
 * 
 */
package metier;

/**
 * Gestionnaire des voitures venant se ravitailler � la station essence
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class Voiture {
    /** heure d'arriv� de la voiture � la pompe */
	private double heureArrivee;

	/** Cr�ation de l'objet voiture
	 *  @param heureArrivee l'heure d'arriv�e de la voiture 
	 */
	public Voiture(double heureArrivee) {
		super();
		this.heureArrivee = heureArrivee;
	}

	/**
	 * @return the heureArrivee
	 */
	public double getHeureArrivee() {
		return heureArrivee;
	}

	/**
	 * @param heureArrivee the heureArrivee to set
	 */
	public void setHeureArrivee(double heureArrivee) {
		this.heureArrivee = heureArrivee;
	}
	
	
}
