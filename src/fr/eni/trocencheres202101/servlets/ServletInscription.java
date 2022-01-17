package fr.eni.trocencheres202101.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bll.UtilisateurManager;
import fr.eni.trocencheres202101.bo.Utilisateur;
import fr.eni.trocencheres202101.dal.CodesResultatDAL;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet("/Inscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ServletInscription() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/Inscription.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur = new Utilisateur();
		List<Integer> listeCodesErreur = new ArrayList<>();

		String inputPseudo = null;
		String inputNom = null;
		String inputPrenom = null;
		String inputEmail = null;
		String inputTelephone = null;
		String inputRue = null;
		String inputCodePostal = null;
		String inputVille = null;
		String inputMdp = null;
		String inputConfMdp = null;

		// Première étape : je lis les paramètres 
		inputPseudo = request.getParameter("pseudo");
		// J'utilise une RegEx pour détecter les caractères spéciaux
		if(checkSpecChar(inputPseudo))
		{
			listeCodesErreur.add(CodesResultatServlets.SAISIE_INCORRECTE);
		}
		if(inputPseudo==null || inputPseudo.trim().equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		inputNom = request.getParameter("nom");
		if(inputNom==null || inputNom.equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		inputPrenom = request.getParameter("prenom");
		if(inputPrenom==null || inputPrenom.trim().equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		inputEmail = request.getParameter("email");
		if(inputEmail==null || inputEmail.trim().equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		inputTelephone = request.getParameter("telephone");
		if(inputTelephone ==null)
		{
			inputTelephone ="";
		}
		inputRue = request.getParameter("rue");
		if(inputRue==null || inputRue.equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		inputCodePostal = request.getParameter("codePostal");
		if(inputCodePostal==null || inputCodePostal.trim().equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}

		inputVille = request.getParameter("ville");
		if(inputVille==null || inputVille.equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		inputMdp = request.getParameter("motDePasse");
		if(inputMdp==null || inputMdp.trim().equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}

		inputConfMdp = request.getParameter("confirmationMdp");
		if(inputConfMdp==null || inputConfMdp.trim().equals(""))
		{
			listeCodesErreur.add(CodesResultatServlets.CHAMP_OBLIGATOIRE);
		}
		// On compare la saisie des champs Mot de Passe et ConfirmationMdp
		if(!inputMdp.equals(inputConfMdp)) 
		{
			listeCodesErreur.add(CodesResultatServlets.MISMATCH);
		}
		// Réalisation du traitement
		if(listeCodesErreur.size()>0)
		{
			// Je renvoie les codes d'erreurs et je reste sur la même page
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
			rd.forward(request, response);
		}
		else {
			// Je vais envoyer les informations récupérées à la BLL pour créer le nouvel utilisateur
			try {
				utilisateur = utilisateurManager.creerUtilisateur(inputPseudo, inputNom, inputPrenom, inputEmail, inputTelephone, inputRue, inputCodePostal, inputVille, inputMdp);
				
				// En cas de succès, je suis redirigé vers la page d'accueil en mode connecté
				this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/Connexion.jsp").forward(request, response);
			} catch (BusinessException e) {
				// En cas d'échec, je renvoie un message d'erreur et je reste sur la même page
				listeCodesErreur.add(CodesResultatDAL.INSERT_OBJET_ECHEC);
				request.setAttribute("listeCodesErreur",listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
				rd.forward(request, response);
			}
		} 
		
	}
	
	// Méthode pour détecter la présence de caractères spéciaux à l'aide d'une RegEx
	public boolean checkSpecChar(String string) {
		boolean result;
		Pattern p = Pattern.compile("^(\\p{Alnum})+$");
		Matcher m = p.matcher(string);
		if (m.find())
		{
			result = false;
		} 
		else
		{
			result = true;
		}
		return result;
	}

}
