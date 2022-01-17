package fr.eni.trocencheres202101.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
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
import fr.eni.trocencheres202101.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleEnchere
 */
@WebServlet(urlPatterns = {
		"/Nouvelle_Enchere",
		"/Nouvelle_Enchere_Enregistrement"
		})
public class ServletNouvelleEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletNouvelleEnchere() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		request.setAttribute("utilisateur", utilisateur);
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/NouvelleEnchere.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getServletPath().equals("/Nouvelle_Enchere")) {
			
			
			doGet(request, response);
		}
		
		if(request.getServletPath().equals("/Nouvelle_Enchere_Enregistrement")) {
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			Utilisateur utilisateur = null;
			HttpSession session = request.getSession();
			try {
				String identifiant =  String.valueOf(session.getAttribute("identifiant"));
				
				if(identifiant.contains("@") && identifiant.contains(".")) {
					utilisateur = utilisateurManager.selectByEmail(identifiant);
				}else {
					utilisateur = utilisateurManager.selectByPseudo(identifiant);
				}
				
			} catch (BusinessException e) {
				//
				e.printStackTrace();
			}
			request.setAttribute("utilisateur", utilisateur);
			// je lis les paramètres
			String nomArticle = null;
			String description = null;
			String libelleCategorie = null;
			String image = null;
			int miseAPrix;
			LocalDateTime dateDebutEnchere = null;
			LocalDateTime dateFinEnchere = null;
			String etatVente = "CR";
			String rue = null;
			String codePostal = null;
			String ville = null;
			request.setCharacterEncoding("UTF-8");
			List<Integer> listeCodesErreur = new ArrayList<>();
			
			// lecture du nom de l'artilce
			nomArticle = request.getParameter("nomArticle");
			if(nomArticle.equals("") || nomArticle.trim().equals("")) {
				listeCodesErreur.add(CodesResultatServlets.NOM_ARTICLE_OBLIGATOIRE);	
			}
			
			// lecture de la description
			description = request.getParameter("description");
			if(description.equals("") || description.trim().equals("")) {
				listeCodesErreur.add(CodesResultatServlets.DESCRITION_ARTICLE_OBLIGATOIRE);
			}
			
			// lecture de la catégorie
			libelleCategorie = request.getParameter("libelleCategorie");
			if(libelleCategorie == null) {
				
			}
			
			// lecture de l'image
			image = request.getParameter("image");
			
			// lecture de la mise à prix
			String retour = request.getParameter("miseAPrix");
			miseAPrix = Integer.parseInt(retour);
			System.out.println();
			
			// lecture de la date et heure du début de l'enchère
			dateDebutEnchere = LocalDateTime.parse(request.getParameter("dateDebutEnchere"));
			if(dateDebutEnchere==null || 
				dateDebutEnchere.isBefore(LocalDateTime.now())){

				listeCodesErreur.add(CodesResultatServlets.DATE_DEBUT_ENCHERE_OBLIGATOIRE);
				dateDebutEnchere = null;
			}
			
			// lecture de la date et heure de fin de l'enchère
			dateFinEnchere = LocalDateTime.parse(request.getParameter("dateFinEnchere"));
			if(dateFinEnchere==null || 
					dateFinEnchere.isBefore(LocalDateTime.now())){

				listeCodesErreur.add(CodesResultatServlets.DATE_FIN_ENCHERE_OBLIGATOIRE);
				dateFinEnchere = null;
			}
			
			// lecture de la rue
			rue = request.getParameter("rue");
			if(rue.equals("") || rue.trim().equals("")) {
				listeCodesErreur.add(CodesResultatServlets.RUE_RETRAIT_OBLIGATOIRE);	
			}
			
			// lecture du code postal
			codePostal = request.getParameter("codePostal");
			if(codePostal.equals("") || codePostal.trim().equals("")) {
				listeCodesErreur.add(CodesResultatServlets.CODE_POSTAL_RETRAIT_OBLIGATOIRE);	
			}
			
			// lecture de la ville
			ville = request.getParameter("ville");
			if(ville.equals("") || ville.trim().equals("")) {
				listeCodesErreur.add(CodesResultatServlets.VILLE_RETRAIT_OBLIGATOIRE);	
			}
			
			//Réalisation du traitement
			if(listeCodesErreur.size()>0)
			{
				//Je renvoie les codes d'erreurs
				request.setAttribute("listeCodesErreur",listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/NouvelleEnchere.jsp");
				rd.forward(request, response);
			}else {
				
				// on ajoute l'article et le point de retrait
				ArticleVenduManager articleVenduManager = new ArticleVenduManager();
				try {
					articleVenduManager.ajouterArticleVendu(nomArticle, description, dateDebutEnchere, dateFinEnchere, miseAPrix,utilisateur, 
							libelleCategorie, etatVente, rue, codePostal, ville);
					
					request.setAttribute("message", "l'article a bien été enregistré!");
					// si tout se passe bien, on revient vers la page Accueil Abonné
					RequestDispatcher rd = request.getRequestDispatcher("/Accueil_Abonne");
					rd.forward(request, response);
				}catch (BusinessException e){
					//Sinon on retourne à la page Nouvelle Vente pour indiquer les problèmes:
					e.printStackTrace();
					request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
					RequestDispatcher rd = request.getRequestDispatcher("/Nouvelle_Enchere");
					rd.forward(request, response);
				}
			}
				
		}
			
	}
	
}
