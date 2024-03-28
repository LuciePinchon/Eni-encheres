package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDAO {
	public void insert(Encheres enchere) throws BusinessException;
	public void update (Encheres encheres) throws BusinessException;
	
	//méthode qui nous permet de sélectionner une enchère grâce à l'id enchère
	public Encheres selectFromIdEnchere(int id) throws BusinessException;
	
	
	////méthode qui nous permet de sélectionner une enchère grâce à l'id de l'utilisateur
	public Encheres selectFromIdUtilisateur(int id)throws BusinessException;
	
	//méthode qui nous permet de sélectionner une enchère grâce à l'id de l'article 
	public Encheres selectFromIdArticle (int id )throws BusinessException ; 
	
	public int selectByIDAarticleAndMontant(int noArticle,int montant) throws BusinessException;
}
