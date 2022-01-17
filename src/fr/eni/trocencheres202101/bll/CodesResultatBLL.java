package fr.eni.trocencheres202101.bll;

public abstract class CodesResultatBLL {
	
	/**
	 * Echec de la récupération du pseudo
	 */
	public static final int ERREUR_PSEUDO =20000;
	
	/**
	 * Echec de la récupération de l'email
	 */
	public static final int ERREUR_EMAIL=20001;
	
	/**
	 * La date de début d'enchère saisie n'est pas correcte
	 */
	public static final int DATE_DEBUT_ENCHERE_ERREUR = 20002;
	
	/**
	 * La date de fin d'enchère saisie n'est pas correcte
	 */
	public static final int DATE_FIN_ENCHERE_ERREUR = 20003;


}
