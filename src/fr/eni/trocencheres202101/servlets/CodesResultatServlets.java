package fr.eni.trocencheres202101.servlets;

public abstract class CodesResultatServlets {
	
	/**
	 * Echec de la lecture de l'identifiant
	 */
	public static final int IDENTIFIANT_OBLIGATOIRE =30000;
	
	/**
	 * Echec de la lecture du mot de passe
	 */
	public static final int MOT_DE_PASSE_OBLIGATOIRE=30001;

	/**
	 * Echec de la lecture du nom de l'article
	 */
	public static final Integer NOM_ARTICLE_OBLIGATOIRE = 30002;

	/**
	 * Echec de la lecture de la description de l'article
	 */
	public static final Integer DESCRITION_ARTICLE_OBLIGATOIRE = 30003;

	/**
	 * Echec de la lecture de la catégorie de l'article
	 */
	public static final Integer CATEGORIE_ARTICLE_OBLIGATOIRE = 30004;

	/**
	 * Echec de la lecture de la date de début d'enchère
	 */
	public static final Integer DATE_DEBUT_ENCHERE_OBLIGATOIRE = 30005;
	
	/**
	 * Echec de la lecture de la date de fin d'enchère
	 */
	public static final Integer DATE_FIN_ENCHERE_OBLIGATOIRE = 30006;

	/**
	 * Echec de la lecture de la rue
	 */
	public static final Integer RUE_RETRAIT_OBLIGATOIRE = 30007;

	/**
	 * Echec de la lecture du code postal
	 */
	public static final Integer CODE_POSTAL_RETRAIT_OBLIGATOIRE = 30008;

	/**
	 * Echec de la lecture de la ville
	 */
	public static final Integer VILLE_RETRAIT_OBLIGATOIRE = 30009;

	
   /**
    * Echec de la lecture du contenu du champ
    */
   public static final int CHAMP_OBLIGATOIRE=30010;

    /**
     * Non-concordance du MdP et de la confirmation MdP
     */
    public static final int MISMATCH = 30011;

   
    /**
     * Saisie de caractères autres qu'alpha-numériques
     */
    public static final int SAISIE_INCORRECTE = 30012;
	
	

}
