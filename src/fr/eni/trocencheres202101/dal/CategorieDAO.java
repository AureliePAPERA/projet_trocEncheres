package fr.eni.trocencheres202101.dal;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.Categorie;

public interface CategorieDAO {

	public Categorie selectByLibelle(String libelle) throws BusinessException;
}
