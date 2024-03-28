package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.EncheresDAO;


public class EncheresDAOJDBCImpl implements EncheresDAO {
 private static String sqlInsert = "INSERT INTO encheres (date_enchere, montant_enchere,no_article, no_utilisateur)VALUES (?,?,?,?) ";
 private static String sqlSelectByIdEnchere = "SELECT * FROM encheres WHERE no_enchere =?";
 private static String sqlSelectByIdUtilisateur ="SELECT * FROM encheres WHERE no_utilisateur =?";
 private static String sqlSelectByIdArticle ="SELECT * FROM encheres WHERE no_article =?";
 private static String sqlUpdate = "UPDATE encheres SET date_enchere = ?, montant_enchere = ? , no_article = ? , no_utilisateur = ? ";
 private static String sqlSelectByIdArticleAndMontant ="SELECT e.no_utilisateur FROM encheres e JOIN articles_vendus a ON e.no_article = a.no_article AND e.montant_enchere = a.prix_vente AND a.prix_vente = ? AND a.no_article = ?";
 
 @Override 
 public void insert (Encheres encheres)throws BusinessException {
	 try ( Connection cnx = ConnectionProvider.getConnection();
		 PreparedStatement stmt = cnx.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS)) { 
		
			
		stmt.setDate (1, java.sql.Date.valueOf(encheres.getDateEnchere())) ;
		stmt.setInt(2, encheres.getMontantEnchere());
		stmt.setInt(3, encheres.getNoArticle());
		stmt.setInt(4, encheres.getNoUtilisateur());
		
		int result = stmt.executeUpdate();
		try(ResultSet rs = stmt.getGeneratedKeys()){
		if(result==1)
		{
			 //on récupère la clé qui a été généré automatiquement
			if(rs.next()) {encheres.setNoEnchere(rs.getInt(1));} 
			
		}
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}

 }



@Override
public Encheres selectFromIdEnchere(int id) throws BusinessException {
	Encheres copieEncheres = new Encheres ();
	try (Connection cnx = ConnectionProvider.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(sqlSelectByIdEnchere)) {
		
		
		stmt.setInt(1, id);
		try(ResultSet rs = stmt.executeQuery()){
		
		if(rs.next())
		{
			copieEncheres.setDateEnchere(rs.getDate(1).toLocalDate());
			copieEncheres.setMontantEnchere(rs.getInt(2));
			copieEncheres.setNoArticle(rs.getInt(3));
			copieEncheres.setNoUtilisateur(rs.getInt(4));
		}
	
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return copieEncheres;
	
}




@Override
//renvoie un utilisateur si il a participé à une enchère (quelconque), à modifier pour rajouter l'id de l'enchere auquel il a participé
public Encheres selectFromIdUtilisateur(int id) throws BusinessException {
	Encheres copieEncheres = new Encheres ();
	
	try(Connection cnx = ConnectionProvider.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(sqlSelectByIdUtilisateur)){
		
		stmt.setInt(1, id);
		try(ResultSet rs = stmt.executeQuery()){
		
		if(rs.next())
		{
			copieEncheres.setDateEnchere(rs.getDate(1).toLocalDate());
			copieEncheres.setMontantEnchere(rs.getInt(2));
			copieEncheres.setNoArticle(rs.getInt(3));
			copieEncheres.setNoUtilisateur(rs.getInt(4));
		}
	
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return copieEncheres;
	
}
@Override
//renvoie le premier resultat d'enchère si il trouve une enchere avec l'id article correspondant
public Encheres selectFromIdArticle(int id) throws BusinessException {
	Encheres copieEncheres = new Encheres ();
	try (Connection cnx = ConnectionProvider.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(sqlSelectByIdArticle)) {
		
		
		stmt.setInt(1, id);
		try(ResultSet rs = stmt.executeQuery()){
		
		if(rs.next())
		{
			copieEncheres.setNoEnchere(rs.getInt(1));
			copieEncheres.setDateEnchere(rs.getDate(2).toLocalDate());
			copieEncheres.setMontantEnchere(rs.getInt(3));
			copieEncheres.setNoArticle(id);
			copieEncheres.setNoUtilisateur(rs.getInt(5));
		}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return copieEncheres;
}



@Override
public void update(Encheres encheres) throws BusinessException {

	try(Connection cnx = ConnectionProvider.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(sqlUpdate)) {
		
		
	stmt.setDate (1,java.sql.Date.valueOf( encheres.getDateEnchere()));
	stmt.setInt(2, encheres.getMontantEnchere());
	stmt.setInt(3, encheres.getNoArticle());
	stmt.setInt(4, encheres.getNoUtilisateur());
	stmt.executeUpdate();
	
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}



@Override
public int selectByIDAarticleAndMontant(int noArticle, int montant) throws BusinessException {
	int idUtilisateur = 0;
	try (Connection cnx = ConnectionProvider.getConnection();
		PreparedStatement stmt = cnx.prepareStatement(sqlSelectByIdArticleAndMontant)){
		
		
		stmt.setInt(1, montant);
		stmt.setInt(2, noArticle);
		try(ResultSet rs = stmt.executeQuery()){
		
		if(rs.next())
		{
			idUtilisateur = (rs.getInt(1));
		}
	
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return idUtilisateur;
}
}







