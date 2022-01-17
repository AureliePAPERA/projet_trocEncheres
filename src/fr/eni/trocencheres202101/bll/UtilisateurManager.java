package fr.eni.trocencheres202101.bll;

import java.util.List;

import fr.eni.trocencheres202101.dal.DAOFactory;
import fr.eni.trocencheres202101.dal.UtilisateurDAO;
import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.Utilisateur;

public class UtilisateurManager {
	
	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {

		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public Utilisateur creerUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse) throws BusinessException {
		BusinessException businessException = new BusinessException();
		this.validerPseudo(pseudo, businessException);
		this.validerEmail(email, businessException);
		Utilisateur utilisateur = new Utilisateur();
		if(!businessException.hasErreurs())
		{
			utilisateur.setPseudo(pseudo);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			utilisateur.setTelephone(telephone);
			utilisateur.setRue(rue);
			utilisateur.setCodePostal(codePostal);
			utilisateur.setVille(ville);
			utilisateur.setMotDePasse(motDePasse);
			// Je mets le crédit à 100 et le statut d'admin à faux par défaut à la création de l'utilisateur
			utilisateur.setCredit(100);
			utilisateur.setAdministrateur(false);
			this.utilisateurDAO.insert(utilisateur);
		}
		else 
		{
			throw businessException;
		}
		return utilisateur;
	}
	
	public void modifierUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse) throws BusinessException {
		BusinessException businessException = new BusinessException();
		this.validerPseudo(pseudo, businessException);
		this.validerEmail(email, businessException);
		Utilisateur utilisateur;
		if(!businessException.hasErreurs())
		{
			utilisateur = new Utilisateur();
			utilisateur.setPseudo(pseudo);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			utilisateur.setTelephone(telephone);
			utilisateur.setRue(rue);
			utilisateur.setCodePostal(codePostal);
			utilisateur.setVille(ville);
			utilisateur.setMotDePasse(motDePasse);
			// Je ne mets pas le setCredit ni le setAdministrateur dans l'update car je pense que ça devra faire l'objet de méthodes à part
			this.utilisateurDAO.update(utilisateur);
		}
		else 
		{
			throw businessException;
		}
		
	}
	
	public void supprimerUtilisateur(int noUtilisateur) throws BusinessException {
		this.utilisateurDAO.delete(noUtilisateur);
	}
	
	public List<Utilisateur> selectAll() throws BusinessException {
		return this.utilisateurDAO.selectAll();
	}
 	
	public Utilisateur selectById(int noUtilisateur) throws BusinessException {
		return this.utilisateurDAO.selectById(noUtilisateur);
	}
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		return this.utilisateurDAO.selectByPseudo(pseudo);
	}
	
	public Utilisateur selectByEmail(String email) throws BusinessException {
		return this.utilisateurDAO.selectByEmail(email);
	}
	
	private void validerPseudo(String pseudo, BusinessException businessException) throws BusinessException {
		Utilisateur utilisateur = this.utilisateurDAO.selectByPseudo(pseudo);
		if(pseudo.equals("") || utilisateur.getNoUtilisateur()!= 0 )
		{
			businessException.ajouterErreur(CodesResultatBLL.ERREUR_PSEUDO);
		}
	}
	
	private void validerEmail(String email, BusinessException businessException) throws BusinessException {
		Utilisateur utilisateur = this.utilisateurDAO.selectByEmail(email);
		if(email.equals("") || utilisateur.getNoUtilisateur()!= 0 )
		{
			businessException.ajouterErreur(CodesResultatBLL.ERREUR_EMAIL);
		}
	}
	
	
	
	
}
