package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateur;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;


public class EncheresManager {
	
     private EncheresDAO encheresDAO;
     
  //Constructeur 
     
	  public EncheresManager () {
		  this.encheresDAO = DAOFactory.getEncheresDAO();
	 }
	  
	public void ajouterEncheres (Encheres encheres)	 throws BusinessException {
		this.encheresDAO.insert(encheres);
		}

	public void updateEncheres (Encheres encheres)	 throws BusinessException {
		this.encheresDAO.update(encheres);
		}
		
	
	public Encheres selectFromIdEnchere(int id) throws BusinessException
		{return this.encheresDAO.selectFromIdEnchere(id);}
		
		
	public Encheres selectFromIdUtilisateur(int id)throws BusinessException
		{return this.encheresDAO.selectFromIdUtilisateur(id);}
		
	public Encheres selectFromIdArticle (int id )throws BusinessException
		{return this.encheresDAO.selectFromIdArticle(id);}
	
	public int selectByIDAarticleAndMontant(int noArticle,int montant) throws BusinessException
	{
		return this.encheresDAO.selectByIDAarticleAndMontant(noArticle, montant);
	}
	
	// Verifie si acheteur propose un montant supérier , assez de crédit 
	//Met  alors à jour vente avec la meilleure offre 
public void encherir (Encheres enchere)throws BusinessException {
	

		BusinessException be = new BusinessException();
	    UtilisateurManager utilisateurManager =new UtilisateurManager();
		
		//utilisateur 2 passé en paramètre via l'objet enchere
		Utilisateur utilisateur2 = utilisateurManager.selectUtilisateur(enchere.getNoUtilisateur());
		
		//sert à modifier l'article en base de données (prix de vente)
		ArticleManager articleManager = new ArticleManager();
		ArticlesVendus articleEnchere = articleManager.selectArticle(enchere.getNoArticle());
		
		//on regarde si on a déjà cet id article dans la table enchere
		int idArticle = selectFromIdArticle(articleEnchere.getNoArticle()).getNoArticle();
		int credit2 = utilisateur2.getCredit();
		
		//si non -> première enchère sur cette article
		if(idArticle==0){
			
			if (credit2>=enchere.getMontantEnchere() && enchere.getMontantEnchere()>=articleEnchere.getPrixInitial()) 
			{
				utilisateur2.setCredit(credit2-enchere.getMontantEnchere());
				utilisateurManager.updateUtilisateur(utilisateur2, utilisateur2.getMotDePasse());
	    		ajouterEncheres(enchere);
	    		articleEnchere.setPrixVente(enchere.getMontantEnchere());
	    		articleManager.modifierArticle(articleEnchere);
			}
			else {
				if(credit2<enchere.getMontantEnchere()) {be.ajouterErreur(CodeResultatBLL.PAS_ASSEZ_CREDIT);
				throw be;}
				else {be.ajouterErreur(CodeResultatBLL.PROPOSITION_INVALIDE);
				throw be;}
			}
			
		}
		
		else {
			//je vais chercher le prix de vente (dernière enchère) de l'article
			int enchere1 = articleEnchere.getPrixVente();
			int enchere2 = enchere.getMontantEnchere();
			
			if(credit2<enchere2) {be.ajouterErreur(CodeResultatBLL.PAS_ASSEZ_CREDIT);
			throw be;}
			
			if(enchere2<=enchere1){be.ajouterErreur(CodeResultatBLL.PROPOSITION_INVALIDE);
			throw be;}
			if (credit2>=enchere2 && enchere2>enchere1) 
			{
			//on va chercher l'utilisateur qui avait mis l'enchere1 via un selectByIDArticleAndMontant
			Utilisateur utilisateur1 = utilisateurManager.selectUtilisateur(selectByIDAarticleAndMontant(idArticle, enchere1));
			
			
			
			//on vérifie que utilisateur1 et utilisateur2 soient deux personnes différentes
			if(utilisateur1.getNoUtilisateur()==utilisateur2.getNoUtilisateur())
			{
				be.ajouterErreur(CodeResultatBLL.UTILISATEUR_INVALIDE_ENCHERE);
				throw be;
			}
			
			//on met à jour le crédit2
			utilisateur2.setCredit(credit2-enchere2);
			

		
			
			utilisateurManager.updateUtilisateur(utilisateur2, utilisateur2.getMotDePasse());
			
			//on met à jour le credit1
			int credit1 = utilisateur1.getCredit();
			
			

			utilisateur1.setCredit(credit1+enchere1);
			
			utilisateurManager.updateUtilisateur(utilisateur1, utilisateur1.getMotDePasse());
			
			//on met à jour l'article
			articleEnchere.setPrixVente(enchere2);
			articleManager.modifierArticle(articleEnchere);
			
			//on rajoute l'enchere
			ajouterEncheres(enchere);
			}
	
		}

}


}

