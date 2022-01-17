package fr.eni.trocencheres202101.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.trocencheres202101.BusinessException;
import fr.eni.trocencheres202101.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	private static final String INSERT_UTILISATEUR = "insert into UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) values(?,?,?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_UTILISATEUR = "update UTILISATEURS set pseudo =?, nom =?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? where no_utilisateur=?;";	
	private static final String DELETE_UTILISATEUR = "delete from UTILISATEURS where no_utilisateur=?";
	private static final String SELECT_ALL = "select * from UTILISATEURS";
	private static final String SELECT_BY_ID =	"select * from UTILISATEURS where no_utilisateur=?";
	private static final String SELECT_BY_PSEUDO = "select * from UTILISATEURS where pseudo=?";
	private static final String SELECT_BY_EMAIL = "select * from UTILISATEURS where email=?";
	
	@Override
	public void insert(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur==null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			try
			{
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;
				if(utilisateur.getNoUtilisateur()==0)
				{
					pstmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, utilisateur.getPseudo());
					pstmt.setString(2, utilisateur.getNom());
					pstmt.setString(3, utilisateur.getPrenom());
					pstmt.setString(4, utilisateur.getEmail());
					pstmt.setString(5, utilisateur.getTelephone());
					pstmt.setString(6, utilisateur.getRue());
					pstmt.setString(7, utilisateur.getCodePostal());
					pstmt.setString(8, utilisateur.getVille());
					pstmt.setString(9, utilisateur.getMotDePasse());
					pstmt.setInt(10, utilisateur.getCredit());
					pstmt.setBoolean(11, utilisateur.isAdministrateur());
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if(rs.next())
					{
						utilisateur.setNoUtilisateur(rs.getInt(1));
					}
					rs.close();
					pstmt.close();
				}
				cnx.commit();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		
	}

	@Override
	public void update(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur==null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			try
			{
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				if(utilisateur.getNoUtilisateur()==0)
				{
					pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
					pstmt.setString(1, utilisateur.getPseudo());
					pstmt.setString(2, utilisateur.getNom());
					pstmt.setString(3, utilisateur.getPrenom());
					pstmt.setString(4, utilisateur.getEmail());
					pstmt.setString(5, utilisateur.getTelephone());
					pstmt.setString(6, utilisateur.getRue());
					pstmt.setString(7, utilisateur.getCodePostal());
					pstmt.setString(8, utilisateur.getVille());
					pstmt.setString(9, utilisateur.getMotDePasse());
					pstmt.setInt(10, utilisateur.getNoUtilisateur());
					pstmt.executeUpdate();
					pstmt.close();
				}
				cnx.commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		
	}

	@Override
	public void delete(int noUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			pstmt.setInt(1, noUtilisateur);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_UTILISATEUR_ERREUR);
			throw businessException;
		}
		
	}

	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				listeUtilisateurs.add(new Utilisateur(rs.getString("pseudo")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEURS_ECHEC);
			throw businessException;
		}
		return listeUtilisateurs;
	}

	@Override
	public Utilisateur selectById(int noUtilisateur) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			/* Le retour du rs.next peut prendre du temps (selon la qualité de la connexion par exemple). 
			 * Mais à côté de ça, pendant l'attente de cette réponse, le code suivant continue à s'exécuter.
			 * Pour éviter de passer à la suite du code sans avoir eu la réponse, le  while permet d'attendre cette réponse 
			 * mais l'inconvénient, c'est qu'il va boucler. Donc en gros, le fait de rajouter un booléen initialisé à true et 
			 * qui va passer à false une fois qu'on aura récupéré la réponse (en l'occurence, le no_utilisateur) 
			 * va permettre de bien récupérer la valeur de la première ligne, une seule fois puisque il va sortir de la boucle.
			 * C'est comme une sorte de contrôleur de fait !*/
			boolean premiereLigne=true;
			while(rs.next())
			{
				if(premiereLigne)
				{
					utilisateur.setNoUtilisateur((rs.getInt("no_utilisateur")));
					utilisateur.setPseudo((rs.getString("pseudo")));
					utilisateur.setNom((rs.getString("nom")));
					utilisateur.setPrenom((rs.getString("prenom")));
					utilisateur.setEmail((rs.getString("email")));
					utilisateur.setTelephone((rs.getString("telephone")));
					utilisateur.setRue((rs.getString("rue")));
					utilisateur.setCodePostal((rs.getString("code_postal")));
					utilisateur.setVille((rs.getString("ville")));
					utilisateur.setMotDePasse((rs.getString("mot_de_passe")));
					utilisateur.setCredit((rs.getInt("credit")));
					utilisateur.setAdministrateur((rs.getBoolean("administrateur")));
					premiereLigne=false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
			throw businessException;
		}
		if(utilisateur.getNoUtilisateur()==0)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEUR_INEXISTANT);
			throw businessException;
		}
		return utilisateur;
	}

	@Override
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne=true;
			while(rs.next())
			{
				if(premiereLigne)
				{
					utilisateur.setNoUtilisateur((rs.getInt("no_utilisateur")));
					utilisateur.setPseudo((rs.getString("pseudo")));
					utilisateur.setNom((rs.getString("nom")));
					utilisateur.setPrenom((rs.getString("prenom")));
					utilisateur.setEmail((rs.getString("email")));
					utilisateur.setTelephone((rs.getString("telephone")));
					utilisateur.setRue((rs.getString("rue")));
					utilisateur.setCodePostal((rs.getString("code_postal")));
					utilisateur.setVille((rs.getString("ville")));
					utilisateur.setMotDePasse((rs.getString("mot_de_passe")));
					utilisateur.setCredit((rs.getInt("credit")));
					utilisateur.setAdministrateur((rs.getBoolean("administrateur")));
					premiereLigne=false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
			throw businessException;
		}
		
		return utilisateur;
	}
	
	@Override
	public Utilisateur selectByEmail(String email) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_EMAIL);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne=true;
			while(rs.next())
			{
				if(premiereLigne)
				{
					utilisateur.setNoUtilisateur((rs.getInt("no_utilisateur")));
					utilisateur.setPseudo((rs.getString("pseudo")));
					utilisateur.setNom((rs.getString("nom")));
					utilisateur.setPrenom((rs.getString("prenom")));
					utilisateur.setEmail((rs.getString("email")));
					utilisateur.setTelephone((rs.getString("telephone")));
					utilisateur.setRue((rs.getString("rue")));
					utilisateur.setCodePostal((rs.getString("code_postal")));
					utilisateur.setVille((rs.getString("ville")));
					utilisateur.setMotDePasse((rs.getString("mot_de_passe")));
					utilisateur.setCredit((rs.getInt("credit")));
					utilisateur.setAdministrateur((rs.getBoolean("administrateur")));
					premiereLigne=false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
			throw businessException;
		}
	
		return utilisateur;
	}

}
