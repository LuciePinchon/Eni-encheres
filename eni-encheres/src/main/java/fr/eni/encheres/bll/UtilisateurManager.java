package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.ihm.CodeResultatIHM;

public class UtilisateurManager {
	
	private UtilisateurDAO utilisateurDAO;
	
	// Constructeur
	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	
	
	public void vérifierChampsVides(BusinessException be, Utilisateur utilisateur)
	{
		if(utilisateur.getPseudo().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.PSEUDO_VIDE);
		}
		
		if(utilisateur.getPrenom().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.PRENOM_VIDE);
		}
		
		if(utilisateur.getNom().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.NOM_VIDE);
		}
		
		if(utilisateur.getCodePostal().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.CODE_POSTAL_VIDE);
		}
		
		if(utilisateur.getRue().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.RUE_VIDE);
		}
		
		if(utilisateur.getVille().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.VILLE_VIDE);
		}
		
		if(utilisateur.getEmail().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.EMAIL_VIDE);
		}
		
		if(utilisateur.getTelephone().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.TELEPHONE_VIDE);
		}
		
	}
	public void vérifierChampsLongueur(BusinessException be, Utilisateur utilisateur)
	{
		if(utilisateur.getPseudo().length()>30 ||(!utilisateur.getPseudo().matches("^[a-zA-Z0-9]*$")) )
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_PSEUDO);
		}
		
		if(utilisateur.getPrenom().length()>30)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_PRENOM);
		}
		
		if(utilisateur.getNom().length()>30)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_NOM);
		}
		
		if(utilisateur.getCodePostal().length()>10)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_CODE_POSTAL);
		}
		
		if(utilisateur.getRue().length()>30)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_RUE);
		}
		
		if(utilisateur.getVille().length()>50)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_VILLE);
		}
		
		if(utilisateur.getEmail().length()>50)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_EMAIL);
		}
		
		if(utilisateur.getTelephone().length()>15)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_TELEPHONE);
		}
		if(utilisateur.getMotDePasse().length()>30)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_MOT_DE_PASSE);
		}
		
	}
	
	public void ajouterUtilisateur(Utilisateur utilisateur, String motDePasseConfirm) throws BusinessException {

		BusinessException be = new BusinessException();
		String pseudo = utilisateur.getPseudo();
        
		
	
	vérifierChampsVides(be,utilisateur);
	vérifierChampsLongueur(be,utilisateur);

		
    //si erreur dans la confirmation du mot de passe
   if (!motDePasseConfirm.equals(utilisateur.getMotDePasse())){
    	be.ajouterErreur(CodeResultatBLL.MOT_DE_PASSE_CHAOS);
    }
    if (selectByEmailUtilisateur(utilisateur.getEmail()).getEmail()!=null){
    	be.ajouterErreur(CodeResultatBLL.EMAIL_DEJA_UTILISE);
    }
    if (selectByPseudoUtilisateur(utilisateur.getPseudo()).getPseudo()!=null){
    	be.ajouterErreur(CodeResultatBLL.PSEUDO_DEJA_UTILISE);
    }
    if(be.hasErreurs()) {throw be;}
    
    this.utilisateurDAO.insert(utilisateur);
    utilisateur.setNoUtilisateur(selectByPseudoUtilisateur(pseudo).getNoUtilisateur());
    
    }
		
	
	
	public Utilisateur selectUtilisateur(int noUtilisateur) throws BusinessException {
		return this.utilisateurDAO.selectFromId(noUtilisateur);
	}
	
	public Utilisateur selectByPseudoUtilisateur(String pseudo) throws BusinessException  {
		return this.utilisateurDAO.selectByPseudo(pseudo);
	}
	
	
	public Utilisateur selectByEmailUtilisateur(String mail) throws BusinessException {
		return this.utilisateurDAO.selectByEmail(mail);
	}
	
	
	public void updateUtilisateur(Utilisateur utilisateur, String motDePasseConfirm) throws BusinessException  {
		BusinessException be = new BusinessException();
		
		vérifierChampsVides(be,utilisateur);
		
		if (!motDePasseConfirm.equals(utilisateur.getMotDePasse())){
	    	be.ajouterErreur(CodeResultatBLL.MOT_DE_PASSE_CHAOS);
	    }
		//on récupère l'id de l'utilisateur
		//on regarde si ils ont changé leur email et si c'est le cas, que cet email soit valide
	    if ((!selectUtilisateur(utilisateur.getNoUtilisateur()).getEmail().equals(utilisateur.getEmail()))&&selectByEmailUtilisateur(utilisateur.getEmail()).getEmail()!=null){
	    	be.ajouterErreur(CodeResultatBLL.EMAIL_DEJA_UTILISE);
	    }
	  //on regarde si ils ont changé leur pseudo et si c'est le cas, que ce pseudo soit valide
	    if ((!selectUtilisateur(utilisateur.getNoUtilisateur()).getPseudo().equals(utilisateur.getPseudo()))&&selectByPseudoUtilisateur(utilisateur.getPseudo()).getPseudo()!=null){
	    	be.ajouterErreur(CodeResultatBLL.PSEUDO_DEJA_UTILISE);
	    }
	    if(be.hasErreurs()) {throw be;}
		this.utilisateurDAO.update(utilisateur);
	}
	
	public void deleteUtilisateur(int noUtilisateur) throws BusinessException {
		this.utilisateurDAO.delete(noUtilisateur);
	}

	//vérifier dans pseudo si arobase ou pas -> checkpseudo ou email en fonction (mais qui renvoie un utilisateur -> select, pas check)
	//utilisateur renvoyé.getMotDePasse.equals motDePasse
	
	public Utilisateur checkConnection(String pseudo, String motDePasse) throws BusinessException {
		BusinessException be = new BusinessException();
		Utilisateur utilisateur = new Utilisateur();
		if(pseudo.contains("@")) {utilisateur = selectByEmailUtilisateur(pseudo);}
		else {utilisateur = selectByPseudoUtilisateur(pseudo);}
		if(utilisateur.getEmail()==null) {
			be.ajouterErreur(CodeResultatBLL.CONNEXION_ECHOUEE);
			throw be;
		}
		if(utilisateur.getMotDePasse().equals(motDePasse)) {return utilisateur;}
		else {be.ajouterErreur(CodeResultatBLL.MAUVAIS_MDP);
			throw be;}
		
		}
		
	public List<Utilisateur> getUtilisateurEncheresEnCours() throws BusinessException {
		ArticleManager articleManager = new ArticleManager();
		List<Integer> listeArticles = articleManager.selectEncoursArticleId();
		List<Utilisateur> listeUtilisateur = new ArrayList<>();
		for(int id : listeArticles) {
			 {
				ArticlesVendus a = articleManager.selectArticle(id);
				//TODO : vérifier ce que fait selectUtilisateur
				Utilisateur u = selectUtilisateur(a.getUtilisateur().getNoUtilisateur());
				int compteur = 0;
				for(Utilisateur v : listeUtilisateur) 
					 {
					if(v.getNoUtilisateur()==u.getNoUtilisateur()){compteur++;}
					
					 }
			if(compteur==0) {listeUtilisateur.add(u);}
			 	
			}
		}
		return listeUtilisateur;
		}
	

}