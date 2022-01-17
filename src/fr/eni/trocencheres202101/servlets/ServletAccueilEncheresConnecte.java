package fr.eni.trocencheres202101.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bll.ArticleVenduManager;
import fr.eni.trocencheres202101.bll.UtilisateurManager;
import fr.eni.trocencheres202101.bo.ArticleVendu;
import fr.eni.trocencheres202101.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueilEncheresConnecte
 */
@WebServlet(urlPatterns={
		"/Accueil_Abonne",
		"/Accueil_Abonne_Selection"
})
public class ServletAccueilEncheresConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueilEncheresConnecte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		List<Integer> listeCodesErreur = new ArrayList<>();
		try {
			
			String etatVente = null;
			List<ArticleVendu> listeArticleVendu = articleVenduManager.selectionArticleVendu(etatVente);
			if(listeArticleVendu.size()>0) {
				request.setAttribute("les_encheres", listeArticleVendu);
				System.out.println(listeArticleVendu);
			}else {
				String message = "il n'y a pas d'enchère en cours pour le moment!";
				request.getAttribute(message);
				System.out.println(message);
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/AccueilEncheresConnecte.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getServletPath().equals("/Accueil_Abonne")) {
			
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur utilisateur = null;
			HttpSession session = request.getSession();
			try {
				String identifiant = (String)session.getAttribute("identifiant");
				if(identifiant.contains("@") && identifiant.contains(".")) {
					utilisateur = utilisateurManager.selectByEmail(identifiant);
				}else {
					utilisateur = utilisateurManager.selectByPseudo(identifiant);
				}
				
			} catch (BusinessException e) {
				//
				e.printStackTrace();
			}
			
			doGet(request, response);
			
		} else if(request.getServletPath().equals("/Accueil_Abonne_Selection")) {

			ArticleVenduManager articleVenduManager = new ArticleVenduManager();
			List<Integer> listeCodesErreur = new ArrayList<>();
			String nomArticleFiltre = "";
			String libelleCategorieFiltre = "toutes";			
				
			if(!request.getParameter("nomArticle").equals(""))
			{
				nomArticleFiltre = request.getParameter("nomArticle");
			}
			if(!request.getParameter("libelleCategorie").equals("toutes"))
			{
				libelleCategorieFiltre = request.getParameter("libelleCategorie");
			}
			
			try {
				
				String etatVente = null;
				List<ArticleVendu> listeArticleVendu = articleVenduManager.selectionArticleVenduRecherche(nomArticleFiltre, libelleCategorieFiltre);
				
				if(listeArticleVendu.size()>0) 
				{
					request.setAttribute("les_encheres", listeArticleVendu);
					System.out.println(listeArticleVendu);
					
				}else 
				{
					String message = "Aucune enchère ne correspond à votre recherche";
					request.getAttribute(message);
					System.out.println(message);
				}
				
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/AccueilEncheresConnecte.jsp");
			rd.forward(request, response);
		}
		
	}

}
