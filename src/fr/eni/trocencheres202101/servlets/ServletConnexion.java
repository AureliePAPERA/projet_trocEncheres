package fr.eni.trocencheres202101.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bll.CodesResultatBLL;
import fr.eni.trocencheres202101.bll.UtilisateurManager;
import fr.eni.trocencheres202101.bo.Utilisateur;
import fr.eni.trocencheres202101.dal.CodesResultatDAL;

@WebServlet("/Connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletConnexion() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// on va récupérer le Cookie identifiant transporté dans la requête
		Cookie[] cookies = request.getCookies();
		String inputId = null;
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				inputId = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
				request.setAttribute("identifiant", inputId);
				break;
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Connexion.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = null;
		String identifiant= null;
		List<Integer> listeCodesErreur = new ArrayList<>();
		// Première étape : je lis les paramètres 
		String inputId = null;
		String inputMdp = null;
		// Lecture identifiant 
		inputId = request.getParameter("identifiant");
		if(inputId==null || inputId.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.IDENTIFIANT_OBLIGATOIRE);
		}
		// Si se souvenir de moi est coché
		if(request.getParameter("memoriser") != null)
		{
			// on crée un cookie
			Cookie cookie = new Cookie("identifiant", java.net.URLEncoder.encode(inputId, "UTF-8"));
			// on lui donne une durée de vie
			cookie.setMaxAge(-1); // le cookie disparaîtra à la fermeture du navigateur
			// on lui associe un commentaire 
			cookie.setComment("votre identifiant");
			// on l'associe à la reponse pour pouvoir le transporter
			response.addCookie(cookie);
		}
		// Lecture mot de passe
		inputMdp = request.getParameter("motDePasse");		
		if(inputMdp==null || inputMdp.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.MOT_DE_PASSE_OBLIGATOIRE);
		}
		// Réalisation du traitement
		if(listeCodesErreur.size()>0) {
			// Je renvoie les codes d'erreur et on reste sur la même page
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Connexion.jsp");
			rd.forward(request, response);
		} 
		else 
		{
			// Je vais d'abord déterminer si l'identifiant saisi est un pseudo ou un mail 
			if(inputId.contains("@") && inputId.contains(".")) {
				try {
					utilisateur = utilisateurManager.selectByEmail(inputId);
					identifiant = utilisateur.getEmail();
				} catch (BusinessException e) {
					request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
					listeCodesErreur.add(CodesResultatBLL.ERREUR_EMAIL);
				}
			} 
			else 
			{
				try {
					utilisateur = utilisateurManager.selectByPseudo(inputId);
					identifiant = utilisateur.getPseudo();
				} catch (BusinessException e) {
					request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
					listeCodesErreur.add(CodesResultatBLL.ERREUR_PSEUDO);
				}
			}
			if(listeCodesErreur.size()>0) {
				// Je renvoie les codes d'erreur et on reste sur la même page
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Connexion.jsp");
				rd.forward(request, response);
			}
			else
			{
					// Je vais m'assurer que l'identifiant saisi par l'utilisateur correspond bien au mot de passe qui lui est associé
					if(identifiant.equals(inputId) && utilisateur.getMotDePasse().equals(inputMdp)) {
						
						HttpSession session = request.getSession();
						session.setAttribute("identifiant", inputId);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/Accueil_Abonne");
						rd.forward(request, response);
					} 
					else
					{
						listeCodesErreur.add(CodesResultatDAL.LECTURE_UTILISATEUR_INEXISTANT);
						request.setAttribute("listeCodesErreur", listeCodesErreur);
						RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Connexion.jsp");
						rd.forward(request, response);
					}
			}	
		}
		
		}


	/* TESTS REGEX :
	 * String pattern = "(\\w)(\\s+)([\\.,])"; String txtChange =
	 * txtIdentifiant.replaceAll(pattern, ""); if()
	 */	
}
