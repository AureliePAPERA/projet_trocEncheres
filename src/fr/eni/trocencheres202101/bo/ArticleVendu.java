package fr.eni.trocencheres202101.bo;

import java.io.Serializable;

import java.time.LocalDateTime;



public class ArticleVendu implements Serializable {

	private static final long serialVersionUID=1L;

	private Utilisateur utilisateur;
	private Categorie categorie;
	private Retrait retrait;
	private Enchere enchere;
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDateTime dateDebutEnchere = LocalDateTime.now();
	private LocalDateTime dateFinEnchere = LocalDateTime.now().plusDays(1);
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private String image;
	
	
	public ArticleVendu() {

	}
	
	/**
	 * @param nomArticle
	 * @param description
	 * @param dateDebutEnchere
	 * @param dateFinEnchere
	 * @param utilisateur
	 * @param categorie
	 * @param etatVente
	 * @param retrait
	 * @param enchere
	 */
	public ArticleVendu(String nomArticle, String description, LocalDateTime dateDebutEnchere, LocalDateTime dateFinEnchere,
			Utilisateur utilisateur, Categorie categorie, String etatVente, Retrait retrait, Enchere enchere) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.etatVente = etatVente;
		this.retrait = retrait;
		this.enchere = enchere;
	}
	/**
	 * @param noArticle
	 * @param nomArticle
	 * @param description
	 * @param dateDebutEnchere
	 * @param dateFinEnchere
	 * @param miseAPrix
	 * @param prixVente
	 * @param utilisateur
	 * @param categorie
	 * @param etatVente
	 * @param image
	 * @param retrait
	 */
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEnchere,
			LocalDateTime dateFinEnchere, int miseAPrix, int prixVente, Utilisateur utilisateur, Categorie categorie,
			String etatVente, String image, Retrait retrait) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.etatVente = etatVente;
		this.image = image;
		this.retrait = retrait;
	}

	/**
	 * @param nomArticle
	 * @param description
	 * @param dateDebutEnchere
	 * @param dateFinEnchere
	 * @param miseAPrix
	 * @param prixVente
	 * @param utilisateur
	 * @param categorie
	 * @param etatVente
	 * @param image
	 * @param retrait
	 */
	public ArticleVendu(String nomArticle, String description, LocalDateTime dateDebutEnchere, LocalDateTime dateFinEnchere,
			int miseAPrix, int prixVente, Utilisateur utilisateur, Categorie categorie, String etatVente,
			String image, Retrait retrait) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.etatVente = etatVente;
		this.image = image;
		this.retrait = retrait;
	}
	
	/**
	 * @param nomArticle
	 * @param description
	 * @param dateDebutEnchere
	 * @param dateFinEnchere
	 * @param miseAPrix
	 * @param categorie
	 * @param etatVente
	 * @param retrait
	 */
	public ArticleVendu(String nomArticle, String description, LocalDateTime dateDebutEnchere, LocalDateTime dateFinEnchere,
			int miseAPrix, Categorie categorie, String etatVente, Retrait retrait) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
		this.categorie = categorie;
		this.etatVente = etatVente;
		this.retrait = retrait;
	}

	public int getNoArticle() {
		return noArticle;
	}



	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}



	public String getNomArticle() {
		return nomArticle;
	}



	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public LocalDateTime getDateDebutEnchere() {
		return dateDebutEnchere;
	}



	public void setDateDebutEnchere(LocalDateTime dateDebutEnchere) {
		this.dateDebutEnchere = dateDebutEnchere;
	}



	public LocalDateTime getDateFinEnchere() {
		return dateFinEnchere;
	}



	public void setDateFinEnchere(LocalDateTime dateFinEnchere) {
		this.dateFinEnchere = dateFinEnchere;
	}



	public int getMiseAPrix() {
		return miseAPrix;
	}



	public void setMiseAPrix(Integer miseAPrix) {
		this.miseAPrix = miseAPrix;
	}



	public int getPrixVente() {
		return prixVente;
	}



	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}



	public Utilisateur getUtilisateur() {
		return utilisateur;
	}



	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}



	public Categorie getCategorie() {
		return categorie;
	}
	

	public void setCategorie(Categorie categorie) {

		this.categorie = categorie;
	}



	public String getEtatVente() {
		return etatVente;
	}



	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the retrait
	 */
	public Retrait getRetrait() {
		return retrait;
	}

	/**
	 * @param retrait the retrait to set
	 */
	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}



	public String toString() {
		return "ArticleVendu[noArticle : " +noArticle+ ",nomArticle : " + nomArticle+ ",description : " +description+ ",dateDebutEnchere : " +dateDebutEnchere+ ",dateFinEnchere :" +dateFinEnchere+ ",miseAPrix :" +miseAPrix+",prixVente :" +prixVente+ ",noUtilisateur :" +utilisateur+ ",Categorie :" +categorie+ ",etatVente :" +etatVente+ ",image : " +image+"]";
	}

	/**
	 * @param nomArticle
	 * @param dateFinEnchere
	 * @param miseAPrix
	 * @param categorie
	 */
	public ArticleVendu(String nomArticle, LocalDateTime dateFinEnchere, int miseAPrix, Categorie categorie) {
		super();
		this.nomArticle = nomArticle;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
		this.categorie = categorie;
	}

	/**
	 * @param nomArticle
	 * @param dateFinEnchere
	 * @param miseAPrix
	 * @param utilisateur
	 */
	public ArticleVendu(String nomArticle, LocalDateTime dateFinEnchere, int miseAPrix) {
		super();
		this.nomArticle = nomArticle;
		this.dateFinEnchere = dateFinEnchere;
		this.miseAPrix = miseAPrix;
	}

	/**
	 * @return the enchere
	 */
	public Enchere getEnchere() {
		return enchere;
	}

	/**
	 * @param enchere the enchere to set
	 */
	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}
	
}
