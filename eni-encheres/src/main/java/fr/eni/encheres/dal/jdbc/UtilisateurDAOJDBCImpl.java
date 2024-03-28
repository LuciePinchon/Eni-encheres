package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;


public class UtilisateurDAOJDBCImpl implements UtilisateurDAO {

	private static String sqlInsert = "INSERT INTO utilisateurs(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static String sqlUpdate = "UPDATE utilisateurs SET pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=?,administrateur=? WHERE no_utilisateur= ?";
	private static String sqlDelete = "DELETE FROM utilisateurs WHERE no_utilisateur= ?";
	private static String sqlSelectByEmail= "SELECT * FROM utilisateurs WHERE email= ?";
	private static String sqlSelectByPseudo="SELECT * FROM utilisateurs WHERE pseudo= ?";
	private static String sqlSelectById = "SELECT * FROM utilisateurs WHERE no_utilisateur= ?";

	
	@Override
	public void insert(Utilisateur utilisateur) throws BusinessException {
		
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS)) {
			
			
			stmt.setString(1, utilisateur.getPseudo());
		    stmt.setString(2, utilisateur.getNom());
		    stmt.setString(3, utilisateur.getPrenom());
		    stmt.setString(4, utilisateur.getEmail());
		    stmt.setString(5, utilisateur.getTelephone());
		    stmt.setString(6, utilisateur.getRue());
		    stmt.setString(7, utilisateur.getCodePostal());
		    stmt.setString(8, utilisateur.getVille());
		    stmt.setString(9, utilisateur.getMotDePasse());
		    stmt.setInt(10, utilisateur.getCredit());
		    stmt.setBoolean(11, utilisateur.isAdministrateur());
		    

		    
			int result = stmt.executeUpdate();
			try(ResultSet rs = stmt.getGeneratedKeys()){
			
			if(result==1)
			{
				 //on récupère la clé qui a été généré automatiquement
				if(rs.next()) {utilisateur.setNoUtilisateur(rs.getInt(1));} 
				
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	public void update(Utilisateur utilisateur) throws BusinessException {

		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlUpdate)) {
			
			
			int id = utilisateur.getNoUtilisateur();
			
			stmt.setString(1, utilisateur.getPseudo());
		    stmt.setString(2, utilisateur.getNom());
		    stmt.setString(3, utilisateur.getPrenom());
		    stmt.setString(4, utilisateur.getEmail());
		    stmt.setString(5, utilisateur.getTelephone());
		    stmt.setString(6, utilisateur.getRue());
		    stmt.setString(7, utilisateur.getCodePostal());
		    stmt.setString(8, utilisateur.getVille());
		    stmt.setString(9, utilisateur.getMotDePasse());
		    stmt.setInt(10, utilisateur.getCredit());
		    stmt.setBoolean(11, utilisateur.isAdministrateur());
		    stmt.setInt(12, id);
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlDelete)){
			
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public Utilisateur selectFromId(int id) throws BusinessException {
		
		Utilisateur copieUtilisateur = new Utilisateur();
		
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlSelectById)) {
			
			
			stmt.setInt(1, id);
			try(ResultSet rs = stmt.executeQuery()){
			
			if(rs.next())
			{
				
				copieUtilisateur.setNoUtilisateur(rs.getInt(1));
				copieUtilisateur.setPseudo(rs.getString(2));
				copieUtilisateur.setNom(rs.getString(3));
				copieUtilisateur.setPrenom(rs.getString(4));
				copieUtilisateur.setEmail(rs.getString(5));
				copieUtilisateur.setTelephone(rs.getString(6));
				copieUtilisateur.setRue(rs.getString(7));
				copieUtilisateur.setCodePostal(rs.getString(8));
				copieUtilisateur.setVille(rs.getString(9));
				copieUtilisateur.setMotDePasse(rs.getString(10));
				copieUtilisateur.setCredit(rs.getInt(11));
				copieUtilisateur.setAdministrateur(rs.getBoolean(12));
			}
			
			
			
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copieUtilisateur;
		
	}

	
	public Utilisateur selectByValeur(String requete, String champ) throws BusinessException {
			
			Utilisateur copieUtilisateur = new Utilisateur();
			
			
			try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement stmt = cnx.prepareStatement(requete)){
				
				
				stmt.setString(1, champ);
				try(ResultSet rs = stmt.executeQuery()){
				
				if(rs.next())
				{
					
					copieUtilisateur.setNoUtilisateur(rs.getInt(1));
					copieUtilisateur.setPseudo(rs.getString(2));
					copieUtilisateur.setNom(rs.getString(3));
					copieUtilisateur.setPrenom(rs.getString(4));
					copieUtilisateur.setEmail(rs.getString(5));
					copieUtilisateur.setTelephone(rs.getString(6));
					copieUtilisateur.setRue(rs.getString(7));
					copieUtilisateur.setCodePostal(rs.getString(8));
					copieUtilisateur.setVille(rs.getString(9));
					copieUtilisateur.setMotDePasse(rs.getString(10));
					copieUtilisateur.setCredit(rs.getInt(11));
					copieUtilisateur.setAdministrateur(rs.getBoolean(12));
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return copieUtilisateur;
			
		}
	

	@Override
	public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
		
		return selectByValeur(sqlSelectByPseudo,pseudo);
	}



	@Override
	public Utilisateur selectByEmail(String email) throws BusinessException {
		
		return selectByValeur(sqlSelectByEmail,email);
	}

}
