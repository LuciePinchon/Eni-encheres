package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.ArticleDAOJDBCImpl;
import fr.eni.encheres.dal.jdbc.EncheresDAOJDBCImpl;
import fr.eni.encheres.dal.jdbc.UtilisateurDAOJDBCImpl;


public abstract class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJDBCImpl();
	}
	
	public static EncheresDAO getEncheresDAO() {
		return new EncheresDAOJDBCImpl();
	}
	
	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJDBCImpl();
	}

}
