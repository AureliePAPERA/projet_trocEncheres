package fr.eni.trocencheres202101.dal;

public abstract class CodesResultatDAL {
	
	// ERREURS GENERALES :
	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_OBJET_NULL=10000;
	
	/**
	 * Echec général quand erreur non gérée à l'insertion 
	 */
	public static final int INSERT_OBJET_ECHEC=10001;

	// ERREURS ARTICLEVENDU :
	/**
	 * Echec suppression d'un article
	 */
	public static final int SUPPRESSION_ARTICLE_ERREUR = 10002;
	
	/**
	 * Echec suppresion d'un point de retrait
	 */
	public static final int SUPPRESSION_RETRAIT_ERREUR = 10003;

	/**
	 * Echec suppression d'une enchère
	 */
	public static final int SUPPRESSION_ENCHERE_ERREUR = 10004;

	/**
	 * Echec lecture d'un ou plusieurs articles
	 */
	public static final int LECTURE_ARTICLES_ECHEC = 10005;
	
	/**
	 * Echec de la lecture de la catégorie
	 */
	public static final int LECTURE_CATEGORIES_ECHEC = 10010;

	// ERREURS UTILISATEUR :
	/**
	 * Echec suppression d'un utilisateur
	 */
	public static final int SUPPRESSION_UTILISATEUR_ERREUR = 10006;

	/**
	 * Echec lecture de la liste des utilisateurs
	 */
	public static final int LECTURE_UTILISATEURS_ECHEC = 10007;
	
	/**
	 * Echec lecture de l'utilisateur
	 */
	public static final int LECTURE_UTILISATEUR_ECHEC = 10008;
	
	/**
	 * Utilisateur inexistant
	 */
	public static final int LECTURE_UTILISATEUR_INEXISTANT = 10009;

	
}