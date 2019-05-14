/**
 * 
 */
package test;

import java.util.Map;

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
		stEss.setLambda(lambda);
		stEss.setMu(mu);
		stEss.setNbPompes(nbStat);
		stEss.setNbClients(nbCli);
		stEss.simulation(true);
		
		System.out.println("Psi : " + stEss.calculPsi());
		System.out.println("NbS : " + stEss.calculNbS());
		System.out.println("NbF : " + stEss.calculNbF());
		System.out.println("TaS : " + stEss.calculTAS());
		System.out.println("TaF : " + stEss.calculTaF());
		
		
		for (Map.Entry<Double, Integer> releve : stEss.getContenuBuffer().entrySet()) {
			//System.out.println(releve.getKey() + " --> " + releve.getValue());
			
		}

		// sortants
		for (double sortie :stEss.getTempsSorties()) {
			System.out.println(sortie);
		}

		// nb dans buffer
		for (Map.Entry<Double, Integer> releve : stEss.getContenuBuffer().entrySet()) {
			//System.out.println(releve.getKey() + " --> " + releve.getValue());
			//serieBuffer.add(releve.getKey().doubleValue(), releve.getValue().doubleValue());
		}
	}
	
}
