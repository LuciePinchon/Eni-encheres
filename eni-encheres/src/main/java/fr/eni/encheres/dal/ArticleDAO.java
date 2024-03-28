package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Utilisateur;

public interface ArticleDAO {
	public void insert(ArticlesVendus article) throws BusinessException;
	public void update(ArticlesVendus article) throws BusinessException;
	public void delete(int noArticle) throws BusinessException;
	
	public ArticlesVendus selectFromId(int noArticle) throws BusinessException;
	public ArticlesVendus selectByNom(String nomArticle) throws BusinessException;
	public List<Integer> selectAllId() throws BusinessException;
}
