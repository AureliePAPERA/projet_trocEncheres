package fr.eni.trocencheres202101.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.Categorie;

public class CategorieDAOJdbcImpl implements CategorieDAO{

	private static final String SELECT_CATEGORIE_BY_LIBELLE = "SELECT * FROM CATEGORIES WHERE libelle = ?;";
	@Override
	public Categorie selectByLibelle(String libelle) throws BusinessException {
		Categorie categorie = new Categorie();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORIE_BY_LIBELLE);
			pstmt.setString(1, libelle);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			while(rs.next()) {
				if(premiereLigne)
				{
					categorie.setNoCategorie(rs.getInt("no_categorie"));
					categorie.setLibelle(rs.getString("libelle"));
					premiereLigne = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIES_ECHEC);
			throw businessException;
		}
		
		return categorie;
	}

}
