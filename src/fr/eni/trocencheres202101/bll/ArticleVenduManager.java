package fr.eni.trocencheres202101.bll;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.trocencheres202101.dal.ArticleVenduDAO;
import fr.eni.trocencheres202101.dal.CategorieDAO;
import fr.eni.trocencheres202101.dal.DAOFactory;
import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.ArticleVendu;
import fr.eni.trocencheres202101.bo.Categorie;
import fr.eni.trocencheres202101.bo.Retrait;
import fr.eni.trocencheres202101.bo.Utilisateur;

public class ArticleVenduManager {
	
	private ArticleVenduDAO articleVenduDAO;
	private CategorieDAO categorieDAO;

	public ArticleVenduManager() {

		this.articleVenduDAO = DAOFactory.getArticleVenduDAO();
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}
	
	public List<ArticleVendu> selectionnerListeArticleVendu() throws BusinessException{
		return this.articleVenduDAO.selectAll();
	}
	
	public ArticleVendu selectionnerArticleVendu(String nomArticle) throws BusinessException {
		return this.articleVenduDAO.selectByNomArticle(nomArticle);
		
	}
	
	public List<ArticleVendu> selectionnerArticleVendu(Categorie categorie) throws BusinessException {
		return this.articleVenduDAO.selectByCategorie(categorie);
	}
	
	public List<ArticleVendu> selectionnerArticleVendu(LocalDateTime dateDebutEnchere) throws BusinessException {
		return this.articleVenduDAO.selectByDate(dateDebutEnchere);
	}
	
	public List<ArticleVendu> selectionArticleVendu(Utilisateur utilisateur) throws BusinessException {
		return articleVenduDAO.selectByUtilisateur(utilisateur);
	}
	
	public void supprimerArticleVendu(int noArticleVendu) throws BusinessException {
		this.articleVenduDAO.delete(noArticleVendu);
	}
	
	public List<ArticleVendu> selectionArticleVendu(String etatVente) throws BusinessException {
		return this.articleVenduDAO.selectByEtat(etatVente);
	}
	
	public List<ArticleVendu> selectionArticleVenduRecherche(String nomArticle, String libelleCategorie) throws BusinessException{
		return this.articleVenduDAO.selectByRecherche(nomArticle, libelleCategorie);
	}
	
	public void ajouterArticleVendu(String nomArticle, String description, LocalDateTime dateDebutEnchere, LocalDateTime dateFinEnchere,
			int miseAPrix,Utilisateur utilisateur, String categorie, String etatVente, String rue, String codePostal, String ville) throws BusinessException{
		
		BusinessException businessException = new BusinessException();
		this.validerDateDebutEnchere(dateDebutEnchere, businessException);
		this.validerDateFinEnchere(dateFinEnchere, businessException);
		
		ArticleVendu articleVendu = null;
		Retrait pointRetrait = null;
		
		if(!businessException.hasErreurs()) {
			articleVendu = new ArticleVendu();
			articleVendu.setNomArticle(nomArticle);
			articleVendu.setDescription(description);
			articleVendu.setDateDebutEnchere(dateDebutEnchere);
			articleVendu.setDateFinEnchere(dateFinEnchere);
			articleVendu.setMiseAPrix(miseAPrix);
			articleVendu.setUtilisateur(utilisateur);
			Categorie categorie1 = this.categorieDAO.selectByLibelle(categorie);
			articleVendu.setCategorie(categorie1);
			articleVendu.setEtatVente(etatVente);
			
			pointRetrait = new Retrait(rue, codePostal, ville);
			
			articleVendu.setRetrait(pointRetrait);
			this.articleVenduDAO.insert(articleVendu);

		}else
		{
			throw businessException;
		}
				
	}
	
	private void validerDateDebutEnchere(LocalDateTime dateDebutEnchere, BusinessException businessException) {
		if(dateDebutEnchere==null || 
				dateDebutEnchere.isBefore(LocalDateTime.now()))
		{
			businessException.ajouterErreur(CodesResultatBLL.DATE_DEBUT_ENCHERE_ERREUR);
		}
		
	}
	
	private void validerDateFinEnchere(LocalDateTime dateFinEnchere, BusinessException businessException) {
		if(dateFinEnchere==null || 
				dateFinEnchere.isBefore(LocalDateTime.now()))
		{
			businessException.ajouterErreur(CodesResultatBLL.DATE_FIN_ENCHERE_ERREUR);
		}
		
	}
	
}
