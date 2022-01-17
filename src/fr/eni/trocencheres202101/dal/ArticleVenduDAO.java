/**
 * 
 */
package fr.eni.trocencheres202101.dal;


import java.time.LocalDateTime;
import java.util.List;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.ArticleVendu;
import fr.eni.trocencheres202101.bo.Categorie;
import fr.eni.trocencheres202101.bo.Utilisateur;

public interface ArticleVenduDAO {

	public void insert(ArticleVendu articleVendu) throws BusinessException;
	public void update(ArticleVendu articleVendu) throws BusinessException;
	public void delete(int noArticleVendu) throws BusinessException;
	public void deleteRetrait(ArticleVendu articleVendu) throws BusinessException;
	public void deleteEnchere(ArticleVendu articleVendu) throws BusinessException;
	public List<ArticleVendu> selectAll() throws BusinessException;
	public ArticleVendu selectByNomArticle(String nomArticle) throws BusinessException;
	public List<ArticleVendu> selectByCategorie(Categorie categorie) throws BusinessException;
	public List<ArticleVendu> selectByDate(LocalDateTime dateDebutEnchere) throws BusinessException;
	public List<ArticleVendu> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException;
	public List<ArticleVendu> selectByEtat(String etatVente) throws BusinessException;
	public List<ArticleVendu> selectByRecherche(String nomArticle, String libelleCategorie) throws BusinessException;
	
}
