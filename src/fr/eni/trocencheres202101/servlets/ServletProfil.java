package fr.eni.trocencheres202101.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bll.UtilisateurManager;
import fr.eni.trocencheres202101.bo.Utilisateur;

/**
 * Servlet implementation class ServletProfil
 */
@WebServlet("/Profil")
public class ServletProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public ServletProfil() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// J'envoie mon utilisateur à la page Profil.jsp qui affichera les informations souhaitées
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
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Profil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
