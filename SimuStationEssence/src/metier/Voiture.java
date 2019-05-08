/**
 * 
 */
package metier;

/**
 * Gestionnaire des voitures venant se ravitailler à la station essence
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class Voiture {
    /** heure d'arrivé de la voiture à la pompe */
	private double heureArrivee;

	/** Création de l'objet voiture
	 *  @param heureArrivee l'heure d'arrivée de la voiture 
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
