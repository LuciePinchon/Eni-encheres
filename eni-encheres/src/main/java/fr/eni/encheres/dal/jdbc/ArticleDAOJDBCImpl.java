package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.dal.ArticleDAO;

public class ArticleDAOJDBCImpl implements ArticleDAO {

	private static String sqlInsert = "INSERT INTO articles_vendus(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?)";
	private static String sqlUpdate = "UPDATE articles_vendus SET nom_article=?,description=?,date_debut_encheres=?,date_fin_encheres=?,prix_initial=?,prix_vente=?,no_categorie=? WHERE no_article= ?";
	private static String sqlDelete = "DELETE FROM articles_vendus WHERE no_article= ?";
	private static String sqlSelectById = "SELECT * FROM articles_vendus WHERE no_article= ?";
	private static String sqlSelectByNom = "SELECT * FROM articles_vendus WHERE nom_article= ?";
	private static String sqlSelectAllId = "SELECT no_article FROM articles_vendus";
	
	
	@Override
	public void insert(ArticlesVendus article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS)){
			
			
			stmt.setString(1, article.getNomArticle());
		    stmt.setString(2, article.getDescriptionArticle());
		    stmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEnchere()));
		    stmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEnchere()));
		    stmt.setInt(5, article.getPrixInitial());
		    stmt.setInt(6, article.getUtilisateur().getNoUtilisateur());
		    stmt.setInt(7, article.getCategorieArticle());
		   		    
			int result = stmt.executeUpdate();
			
			try(ResultSet rs = stmt.getGeneratedKeys();){
			
			if(result==1)
			{
				 //on récupère la clé qui a été généré automatiquement
				if(rs.next()) {article.setNoArticle(rs.getInt(1));} 
				
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement stmt = cnx.prepareStatement(sqlDelete)) {
			
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ArticlesVendus selectFromId(int id) throws BusinessException {
		ArticlesVendus copieArticle = new ArticlesVendus();
		try (Connection cnx = ConnectionProvider.getConnection();
			 PreparedStatement stmt = cnx.prepareStatement(sqlSelectById))
			{
			
		
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery())
			{
			
			
			if(rs.next())
			{
				copieArticle.setNoArticle(rs.getInt(1));
				copieArticle.setNomArticle(rs.getString(2));
				copieArticle.setDescriptionArticle(rs.getString(3));
				copieArticle.setDateDebutEnchere(rs.getDate(4).toLocalDate());
				copieArticle.setDateFinEnchere(rs.getDate(5).toLocalDate());
				copieArticle.setPrixInitial(rs.getInt(6));
				copieArticle.setPrixVente(rs.getInt(7));
				copieArticle.setUtilisateur((new UtilisateurDAOJDBCImpl()).selectFromId(rs.getInt(8)));
				copieArticle.setCategorieArticle(rs.getInt(9));
			}		

			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copieArticle;
	}
	
	@Override
	public ArticlesVendus selectByNom(String nomArticle) throws BusinessException {
		ArticlesVendus copieArticle = new ArticlesVendus();
		
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlSelectByNom)){
			
			
			stmt.setString(1, nomArticle);
			try(ResultSet rs = stmt.executeQuery()){
			
			if(rs.next())
			{
				
				copieArticle.setNoArticle(rs.getInt(1));
				copieArticle.setNomArticle(rs.getString(2));
				copieArticle.setDescriptionArticle(rs.getString(3));
				copieArticle.setDateDebutEnchere(rs.getDate(4).toLocalDate());
				copieArticle.setDateFinEnchere(rs.getDate(5).toLocalDate());
				copieArticle.setPrixInitial(rs.getInt(6));
				copieArticle.setPrixVente(rs.getInt(7));
				copieArticle.setUtilisateur((new UtilisateurDAOJDBCImpl()).selectFromId(rs.getInt(8)));
				copieArticle.setCategorieArticle(rs.getInt(9));
			}		
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copieArticle;
	}
	

	@Override
	public void update(ArticlesVendus article) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlUpdate)){
			
			
			int idArticle = article.getNoArticle();
			
			stmt.setInt(8, idArticle);
		    stmt.setString(1, article.getNomArticle());
		    stmt.setString(2, article.getDescriptionArticle());
		    stmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEnchere()));
		    stmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEnchere()));
		    stmt.setInt(5, article.getPrixInitial());
		    stmt.setInt(6, article.getPrixVente());
		    stmt.setInt(7, article.getCategorieArticle());
		    
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Integer> selectAllId() throws BusinessException {
		List<Integer> listeArticlesIds = new ArrayList<>();
		
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(sqlSelectAllId)){
			
			try(ResultSet rs = stmt.executeQuery();)
			{
			
			while(rs.next())
			{
				int articleId = rs.getInt(1);
				listeArticlesIds.add(articleId);
			}		
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeArticlesIds;
	}

	
	

	

}
