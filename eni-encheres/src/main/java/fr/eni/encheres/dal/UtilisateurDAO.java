package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	public void insert(Utilisateur utilisateur) throws BusinessException;
	public void update(Utilisateur utilisateur) throws BusinessException;
	public void delete(int id) throws BusinessException;
	
	public Utilisateur selectFromId(int id) throws BusinessException;
	
	//méthode qui nous renvoie la personne si le pseudo existe dans la BDD
	public Utilisateur selectByPseudo(String pseudo)throws BusinessException;
	
	//méthode qui nous renvoie la personne si le pseudo existe dans la BDD
	public Utilisateur selectByEmail(String mail)throws BusinessException;
	
	//public Utilisateur checkConnection(String pseudo, String motDePasse) throws BusinessException;
	
}
