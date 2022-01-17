package fr.eni.trocencheres202101.dal;

import java.util.List;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.Utilisateur;

public interface UtilisateurDAO  {
	
	public void insert(Utilisateur utilisateur) throws BusinessException;
	
	public void update(Utilisateur utilisateur) throws BusinessException;
	
	public void delete(int noUtilisateur) throws BusinessException;
	
	public List<Utilisateur> selectAll() throws BusinessException;
	
	public Utilisateur selectById(int noUtilisateur) throws BusinessException;
	
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException;

	public Utilisateur selectByEmail(String email) throws BusinessException;
	
}
