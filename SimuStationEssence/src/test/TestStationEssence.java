/**
 * 
 */
package test;

import metier.GenerationLois;
import metier.StationEssence;

/**
 * @author Quentin MS
 *
 */
public class TestStationEssence {

	public static void main(String args[]) {
		double lambda = 15;
		double mu = 20;
		int nbStat = 1;
		int nbCli = 100;
		StationEssence stEss = new StationEssence();
		stEss.chaineMarkovienne(lambda, mu, nbStat, nbCli);
		for(int i =0; i < stEss.getFileAttenteClient().size(); i++) {
			System.out.println(stEss.getListeTempsEntree().get(i));
		}
		System.out.println("#####################################");
		for(int i =0; i < stEss.getFileAttenteClient().size(); i++) {
			System.out.println(stEss.getListeTempsSortie().get(i));
		}
		System.out.println("#####################################");
		for(int i =0; i < stEss.getFileAttenteClient().size(); i++) {
			System.out.println(stEss.getListeTempsAttenteClients().get(i));
		}
		System.out.println("Psi : " + stEss.getPsi());
		System.out.println("NbS : " + stEss.getNbS());
		System.out.println("NbF : " + stEss.getNbF());
		System.out.println("TaS : " + stEss.getTaS());
		System.out.println("TaF : " + stEss.getTaF());
		
	}
	
}
