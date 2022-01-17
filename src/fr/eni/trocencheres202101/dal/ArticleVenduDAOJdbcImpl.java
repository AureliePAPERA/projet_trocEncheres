package fr.eni.trocencheres202101.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.ArticleVendu;
import fr.eni.trocencheres202101.bo.Categorie;
import fr.eni.trocencheres202101.bo.Utilisateur;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO{
	
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, prix_vente, \r\n" + 
			"no_utilisateur, no_categorie, etat_vente, image)\r\n" + 
			"	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue,code_postal, ville) VALUES (?, ?, ?, ?);";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?);";
	// les méthodes update sont encore à faire
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_enchere=?, date_fin_enchere=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=?, etat_vente=?, image=? WHERE no_article=?;";
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue=?,code_postal=?, ville=? WHERE no_article=?;";
	private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET date_enchere=?, montant_enchere=? WHERE nom_article=?";
	private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE noArticle = ?;";
	private static final String DELETE_RETRAIT = "DELETE FROM RETRAITS WHERE noArticle = ?;";
	private static final String DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE noArticle = ?;";
	private static final String SELECT_ALL_ARTICLE = "SELECT * FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur INNER JOIN CATEGORIES c ON av.no_categorie = c.no_categorie WHERE;";
	private static final String SELECT_ARTICLE_BY_NOM = "SELECT * FROM ARTICLES_VENDUS WHERE (nom_article = '?');";
	private static final String SELECT_ARTICLE_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS av INNER JOIN CATEGORIES c ON av.no_utilisateur = c.no_utilisateur WHERE av.no_utilisateur = ?;";
	private static final String SELECT_ARTICLE_BY_DATE = "SELECT * FROM ARTICLES_VENDUS WHERE (date_debut_enchere = ?);";
	private static final String SELECT_ARTICLE_BY_UTILISATEUR = "SELECT * FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur WHERE av.no_utilisateur = ?;";
	private static final String SELECT_ARTICLE_BY_ETAT_VENTE = "SELECT * FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur INNER JOIN CATEGORIES c ON av.no_categorie = c.no_categorie WHERE (etat_vente = 'EC')";
	
	@Override
	public void insert(ArticleVendu articleVendu) throws BusinessException {
		
		if(articleVendu==null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			try
			{
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;
				if(articleVendu.getNoArticle() == 0) 
				{
					pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, articleVendu.getNomArticle());
					pstmt.setString(2, articleVendu.getDescription());
					pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(articleVendu.getDateDebutEnchere()));
					pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(articleVendu.getDateFinEnchere()));
					pstmt.setInt(5, articleVendu.getMiseAPrix());
					pstmt.setInt(6, articleVendu.getPrixVente());
					pstmt.setInt(7, articleVendu.getUtilisateur().getNoUtilisateur());
					pstmt.setInt(8, articleVendu.getCategorie().getNoCategorie());
					pstmt.setString(9, articleVendu.getEtatVente());
					pstmt.setString(10, articleVendu.getImage());
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if(rs.next())
					{
						articleVendu.setNoArticle(rs.getInt(1));
					}
					rs.close();
					pstmt.close();

					pstmt = cnx.prepareStatement(INSERT_RETRAIT);
					pstmt.setInt(1, articleVendu.getNoArticle());
					pstmt.setString(2, articleVendu.getRetrait().getRue());
					pstmt.setString(3, articleVendu.getRetrait().getCodePostal());
					pstmt.setString(4, articleVendu.getRetrait().getVille());
					pstmt.executeUpdate();
					pstmt.close();
					
					//pstmt = cnx.prepareStatement(INSERT_ENCHERE);
					//pstmt.setInt(1, articleVendu.getUtilisateur().getNoUtilisateur());
					//pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(articleVendu.getDateDebutEnchere()));
					//pstmt.setInt(4, articleVendu.getMiseAPrix());
					//pstmt.close();	
				}
				cnx.commit();
				
			} catch (SQLException e) {
					e.printStackTrace();
					cnx.rollback();
			}
		}catch(SQLException e) {

			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
			
		}
	}

	@Override
	public void update(ArticleVendu articleVendu) throws BusinessException {
		// à faire
	}

	@Override
	public void delete(int noArticleVendu) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, noArticleVendu);
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ARTICLE_ERREUR);
			throw businessException;
		}
	}
	
	

	@Override
	public List<ArticleVendu> selectAll() throws BusinessException {
		// on veut récupérer une liste d'articles vendus
		List<ArticleVendu> listeArticleVendu = new ArrayList<ArticleVendu>();
		// on tente une connexion
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// on lance la requête selectAll dans SQL server
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ARTICLE);
			// on récupère le résultat
			ResultSet rs = pstmt.executeQuery();
			// on parcours le résultat
			while(rs.next())
			{
				// on crée un nouvel article
				ArticleVendu articleVendu = new ArticleVendu();
				// on récupère les données pour chaque attributs
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEnchere(rs.getTimestamp("date_debut_enchere").toLocalDateTime());
				articleVendu.setDateFinEnchere(rs.getTimestamp("date_fin_enchere").toLocalDateTime());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				// on a besoin de créer un objet de type utilisateur pour récupérer le pseudo
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				articleVendu.setUtilisateur(utilisateur);
				
				// on a besoin de créer un objet de type catégorie pour récupérer le libellé
				Categorie categorie = new Categorie();
				categorie.setLibelle(rs.getString("libelle"));
				articleVendu.setCategorie(categorie);
				
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setImage(rs.getString("image"));
				// on ajoute l'article à la liste
				listeArticleVendu.add(articleVendu);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw businessException;
		}
		// on retourne la liste des articles
		return listeArticleVendu;
	}

	@Override
	public ArticleVendu selectByNomArticle(String nomArticle) throws BusinessException {
		ArticleVendu articleVendu = new ArticleVendu();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_NOM);
			pstmt.setString(1, "nom_article");
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			while(rs.next())
			{
				if(premiereLigne) {
					articleVendu.setNomArticle(rs.getString("nom_article"));
					articleVendu.setPrixVente(rs.getInt("prix_vente"));
					articleVendu.setDateFinEnchere(rs.getTimestamp("date_fin_enchere").toLocalDateTime());
					articleVendu.getUtilisateur().setPseudo(rs.getString("pseudo"));
					premiereLigne = false;	
				}
				
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw businessException;
		}
		return articleVendu;
	}

	@Override
	public List<ArticleVendu> selectByCategorie(Categorie categorie) throws BusinessException {
		// on crée la liste qui va contenir les articles
		List<ArticleVendu> listeArticleVendu = new ArrayList<ArticleVendu>();
		// on cré un nouvel article
		ArticleVendu articleVendu = new ArticleVendu();
		// on tente de se connecter
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// on lance la requête selectByCategorie sur SQL server
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_CATEGORIE);
			// on récupère le numéro de catégorie
			pstmt.setInt(1, articleVendu.getCategorie().getNoCategorie());
			// on récupère le résultat
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			// on parcours le résultat
			while(rs.next())
			{
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEnchere(rs.getTimestamp("date_debut_enchere").toLocalDateTime());
				articleVendu.setDateFinEnchere(rs.getTimestamp("date_fin_enchere").toLocalDateTime());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				// on a besoin de créer un objet de type utilisateur pour récupérer le pseudo
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				articleVendu.setUtilisateur(utilisateur);
				
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setImage(rs.getString("image"));
				listeArticleVendu.add(articleVendu);
				premiereLigne = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw businessException;
		}
		
		return listeArticleVendu;
	}

	@Override
	public List<ArticleVendu> selectByDate(LocalDateTime dateDebutEnchere) throws BusinessException {
		List<ArticleVendu> listeArticleVendu = new ArrayList<ArticleVendu>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_DATE);
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(dateDebutEnchere));
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			while(rs.next())
			{
				listeArticleVendu.add(new ArticleVendu(rs.getString("nom_article"), rs.getTimestamp("date_debut_enchere").toLocalDateTime(), rs.getInt("prix_vente")));
				premiereLigne = false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw businessException;
		}
		return listeArticleVendu;
	}

	@Override
	public List<ArticleVendu> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		List<ArticleVendu> listeArticleVendu = new ArrayList<ArticleVendu>();
		ArticleVendu articleVendu = new ArticleVendu();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_UTILISATEUR);
			pstmt.setInt(1, articleVendu.getUtilisateur().getNoUtilisateur());
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne=true;
			while(rs.next())
			{
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEnchere(rs.getTimestamp("date_debut_enchere").toLocalDateTime());
				articleVendu.setDateFinEnchere(rs.getTimestamp("date_fin_enchere").toLocalDateTime());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				// on a besoin de créer un objet de type catégorie pour récupérer le libellé
				Categorie categorie = new Categorie();
				categorie.setLibelle(rs.getString("libelle"));
				articleVendu.setCategorie(categorie);
				
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setImage(rs.getString("image"));
				// on ajoute l'article à la liste
				listeArticleVendu.add(articleVendu);
				premiereLigne = false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw businessException;
		}
		return listeArticleVendu;
	}

	@Override
	public void deleteRetrait(ArticleVendu articleVendu) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_RETRAIT);
			pstmt.setInt(1, articleVendu.getNoArticle());
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_RETRAIT_ERREUR);
			throw businessException;
		}
		
	}

	@Override
	public void deleteEnchere(ArticleVendu articleVendu) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ENCHERE);
			pstmt.setInt(1, articleVendu.getNoArticle());
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ENCHERE_ERREUR);
			throw businessException;
		}
		
	}
	@Override
	public List<ArticleVendu> selectByEtat(String etatVente) throws BusinessException {
		List<ArticleVendu> listeArticleVendu = new ArrayList<ArticleVendu>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_ETAT_VENTE);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			while(rs.next())
			{
				// on crée un nouvel article
				ArticleVendu articleVendu = new ArticleVendu();
				// on récupère les données pour chaque attributs
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEnchere(rs.getTimestamp("date_debut_enchere").toLocalDateTime());
				articleVendu.setDateFinEnchere(rs.getTimestamp("date_fin_enchere").toLocalDateTime());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				// on a besoin de créer un objet de type utilisateur pour récupérer le pseudo
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				articleVendu.setUtilisateur(utilisateur);
				
				// on a besoin de créer un objet de type catégorie pour récupérer le libellé
				Categorie categorie = new Categorie();
				categorie.setLibelle(rs.getString("libelle"));
				articleVendu.setCategorie(categorie);
				
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setImage(rs.getString("image"));
				// on ajoute l'article à la liste
				listeArticleVendu.add(articleVendu);
				premiereLigne = false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw businessException;
		}
		return listeArticleVendu;
	}

	@Override
	public List<ArticleVendu> selectByRecherche(String nomArticle, String libelleCategorie) throws BusinessException {
		// on crée la liste qui va contenir les articles
		List<ArticleVendu> listeArticleVendu = new ArrayList<ArticleVendu>();
		// on crée la requête de base
		String requeteDeBase = SELECT_ARTICLE_BY_ETAT_VENTE;
		// on crée un String qui contiendra la suite de la requête de base
		String whereString;
		// on tente de se connecter
		try(Connection cnx = ConnectionProvider.getConnection()){
		
			// on vérifie si on doit faire une recherche par nom, par catégorie ou les deux
			if(!nomArticle.equals("") && !libelleCategorie.equals("toutes")) {
				whereString = " and nom_article like ? and libelle = ?";
			}else if(!nomArticle.equals("")) {
				whereString = " and nom_article like ?";
			}else if (!libelleCategorie.equals("toutes")) {
				whereString = " and libelle = ?"; 
			}else {
				whereString = null;
			}
			// on vient compléter la requête de base
			requeteDeBase = requeteDeBase + whereString;
			//System.out.println(requeteDeBase);
			// on execute la requête sur SQL Server
			PreparedStatement pstmt = cnx.prepareStatement(requeteDeBase);
			//
			if(!nomArticle.equals("") && !libelleCategorie.equals("toutes")) {
				pstmt.setString(1, "%"+nomArticle+"%");
				pstmt.setString(2, libelleCategorie);
			}else if(!nomArticle.equals("")) {
				pstmt.setString(1, "%"+nomArticle+"%");
			}else if (!libelleCategorie.equals("toutes")) {
				pstmt.setString(1, libelleCategorie);
			}
			// on récupère le résultat
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			// on parcourt le résultat
			while(rs.next())
			{
				// on crée un nouvel article
				ArticleVendu articleVendu = new ArticleVendu();
				// on récupère les données pour chaque attributs
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateDebutEnchere(rs.getTimestamp("date_debut_enchere").toLocalDateTime());
				articleVendu.setDateFinEnchere(rs.getTimestamp("date_fin_enchere").toLocalDateTime());
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				// on a besoin de créer un objet de type utilisateur pour récupérer le pseudo
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				articleVendu.setUtilisateur(utilisateur);
				
				// on a besoin de créer un objet de type catégorie pour récupérer le libellé
				Categorie categorie = new Categorie();
				categorie.setLibelle(rs.getString("libelle"));
				articleVendu.setCategorie(categorie);
				
				articleVendu.setEtatVente(rs.getString("etat_vente"));
				articleVendu.setImage(rs.getString("image"));
				// on ajoute l'article à la liste
				listeArticleVendu.add(articleVendu);
				premiereLigne = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeArticleVendu;
	}

}
