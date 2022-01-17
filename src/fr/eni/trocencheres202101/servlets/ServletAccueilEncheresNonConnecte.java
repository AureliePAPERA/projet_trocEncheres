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


import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bll.ArticleVenduManager;
import fr.eni.trocencheres202101.bo.ArticleVendu;



/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/Accueil")
public class ServletAccueilEncheresNonConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/AccueilEncheresNonConnecte.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleVenduManager articleVenduManager = new ArticleVenduManager();
		List<Integer> listeCodesErreur = new ArrayList<>();
		String nomArticleFiltre = null;
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
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/AccueilEncheresNonConnecte.jsp");
		rd.forward(request, response);
	}

}
