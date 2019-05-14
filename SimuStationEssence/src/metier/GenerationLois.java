/*
 * 3iL promotion 2017
 * 23/04/2019 
 */
package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * G�n�rateur de nombres al�atoires
 * <ul>
 *     <li>Loi uniforme</li>
 *     <li>Loi exponentielle</li>
 *     <li>Loi normale</li>
 *     <li>Loi de Poisson</li>
 * </ul>
 * @author Quentin MS, Florent Mamprin, Robin Henry
 * @version 1.0
 */
public class GenerationLois {

	/** Nombre de valeurs que l'on va g�n�rer */
	public final static int NB_VALEUR = 1000;

	/** Map cl� compteur val valeur g�n�r�e par loi poisson */
	public static ArrayList<Double> valPoisson = new ArrayList<>();

	/**
	 * G�n�ration de nombre al�atoire en suivant une loi uniforme
	 * @return  un nombre compris entre 0.0 et 1.0
	 */
	public static double loiUniforme() {
		// Cr�ation d'un objet ramdom afin de g�n�rer un nombre al�atoire
		Random rng = new Random();

		return rng.nextDouble();			
	}

	/**
	 * G�n�ration de nombre al�atoire en suivant une loi exponentielle
	 * @param lambda param�tre de la loi exponentielle
	 * @return un nombre al�atoire 
	 */
	public static double loiExponentielle(double lambda) {
		// G�n�ration d'un nombre al�atoire
		double x = loiUniforme();		

		return -(1/lambda) * Math.log(x);	
	}

	/**
	 * G�n�ration de nombre al�atoire suivant une loi normale
	 * @param moyenne
	 * @param ecarType
	 * @return un nombre al�atoire
	 */
	public static double loiNormale(double moyenne, double ecarType) {
		// Initialisation de deux nombre suivant une loi uniforme
		double u = loiUniforme();
		double v = loiUniforme();

		return Math.sqrt(-2 * Math.log(u)) * Math.cos(2 * Math.PI * v) * ecarType + moyenne;
	}

	/** G�n�ration de nombre al�atoire suivant une loi de poisson discrete
	 * @param lambda param�tre de la loi exponentielle
	 * @param T interval de temps
	 */
	public static int loiPoisson(double lambda, double T) {				
		double expo = 0;
		int compteur;
		// on vide la liste contenant les valeurs
		valPoisson.clear();
		for (compteur = 0 ; expo < T ; compteur++) {
			expo += loiExponentielle(lambda);
			valPoisson.add(expo);  
		}
		return compteur;
	}

	/**
	 * G�n�ration de nombre al�atoire suivant une loi de Weibull
	 * @param alpha 
	 * @param beta 
	 */
	public static double loiWeibull(double alpha, double beta) {
		// G�n�ration d'un nombre al�atoire suivant une loi uniforme
		double y = loiUniforme();

		return Math.pow((-(Math.log(1-y)/Math.pow(alpha, beta))),1/beta);	
	}

	/**
	 * calcul d'une factorielle
	 * @param n nombre pour lequel la factorielle va �tre calcul�
	 * @return la factorielle de n
	 */
	public static long factorielle(long n) {
		if (n <= 1) {
			return 1;
		} else {
			return n * factorielle(n - 1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*double res, save;
		res = save = 0;
		for(int i = 0 ; i<NB_VALEUR ; i++) {
			System.out.println("UNIFORME : " + loiUniforme());
		}
		for(int i = 0 ; i<NB_VALEUR ; i++) {
			System.out.println("EXPONENTIELLE : " + loiExponentielle(1));
		}
		for(int i = 0 ; i<NB_VALEUR ; i++) {
			System.out.println("NORMALE : " + loiNormale());
			res= loiNormale();
			save += res;
		}
		System.out.println("Moyenne : " + save / NB_VALEUR);*/

		// test de la loi de poisson
		int T = 100;
		double sommeEcart = 0;
		double lambda = 2;
		int nbTire = 0;
		double somme = 0;
		double calcul = 0.0;
		double nbEventIntervalle = 0;
		double nbEventIntervalleTh = 0;
		for (int i =0; i < 100; i++) {
			nbTire = loiPoisson(lambda, T);
			System.out.println("POISSON : " + nbTire );
			for (int j = 0 ; j < valPoisson.size(); j++) {
				System.out.println(valPoisson.get(j));
			}
			somme += nbTire;
			calcul = valPoisson.get(valPoisson.size()-1);
		}
		nbEventIntervalle = somme/100;
		nbEventIntervalleTh = Math.exp(-lambda) * (Math.pow((lambda),20) / factorielle(20));
		System.out.println("Moyenne obs poisson : " + nbEventIntervalle);
		System.out.println("Moyenne th poisson : " + lambda*T );
		System.out.println("Moyenne th expo :" + 1/lambda );
		System.out.println("Moyenne obs expo :" + calcul/nbTire );


		System.out.println("test pour flo");
		for(int i = 0; i < valPoisson.size(); i++) {
			if (i == 0) {
				//System.out.println("Valeur de t0 :" + valPoisson.get(i));
				sommeEcart+= valPoisson.get(i);
				//System.out.println(valPoisson.get(i));
			} else {
				double diff = valPoisson.get(i)-valPoisson.get(i-1);
				//System.out.println("Diff�rence entre t" + i + " et t" + (i-1) + ": "+ diff );
			//	System.out.println(diff);
				sommeEcart+= diff;
			}
			//System.out.println(lambda*(Math.exp((-lambda) * valPoisson.get(i))));
		}
		System.out.println(sommeEcart/valPoisson.size());

	}
}
