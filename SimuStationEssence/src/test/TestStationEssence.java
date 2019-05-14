/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.Map;

import metier.GenerationLois;
import metier.StationEssence;


/**
 * @author Quentin MS
 *
 */
public class TestStationEssence {

	public static void main(String args[]) {
		double lambda = 30;
		double mu = 20;
		int nbStat = 2;
		int nbCli = 100;
		
		ArrayList<Double> entree = new ArrayList<>();
		ArrayList<Double> sortie = new ArrayList<>();
		ArrayList<Integer> nbDansSysteme = new ArrayList<>();
		
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
			System.out.println(releve.getKey() + " --> " + releve.getValue());
			entree.add(releve.getKey());
		}

		// sortants
		for (int i = 0; i < stEss.getTempsSorties().size(); i++) {
			sortie.add(stEss.getTempsSorties().get(i));

			System.out.println(stEss.getTempsSorties().get(i));
			//serieSortant.add(sortie, 1);
}

			// System.out.println(stEss.getTempsSorties().get(i));
			// serieSortant.add(sortie, 1);
		


		// nb dans buffer
		for (Map.Entry<Double, Integer> releve : stEss.getContenuBuffer().entrySet()) {
			//System.out.println(releve.getKey() + " --> " + releve.getValue());
			//serieBuffer.add(releve.getKey().doubleValue(), releve.getValue().doubleValue());
			nbDansSysteme.add(releve.getValue());
		}
	}
	
}
