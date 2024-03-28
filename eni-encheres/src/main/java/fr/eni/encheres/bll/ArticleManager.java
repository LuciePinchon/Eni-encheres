package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

public class ArticleManager {
	
	private ArticleDAO articleDAO;
	
	//Constructeur
	public ArticleManager() {
		this.articleDAO = DAOFactory.getArticleDAO(); 
		
	}
	
	public void verifierChampsVides(BusinessException be, ArticlesVendus article)
	{
		if(article.getNomArticle().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.NOM_ARTICLE_VIDE);
		}
		
		if(article.getDescriptionArticle().equals(""))
		{
			be.ajouterErreur(CodeResultatBLL.DESCRIPTION_VIDE);
		}
		
		if(article.getCategorieArticle()== 0)
		{
			be.ajouterErreur(CodeResultatBLL.CATEGORIE_VIDE);
		}
		
		if(article.getPrixInitial() == 0)
		{
			be.ajouterErreur(CodeResultatBLL.PRIX_INITIAL_VIDE);
		}
		
		
		if(article.getDateDebutEnchere()== null)
		{
			be.ajouterErreur(CodeResultatBLL.DATE_DEBUT_VIDE);
		}
		
		if(article.getDateFinEnchere()== null)
		{
			be.ajouterErreur(CodeResultatBLL.DATE_FIN_VIDE);
		}	
	}
	
	public void verifierLongueurChamp(BusinessException be,ArticlesVendus article) throws BusinessException{
		if(article.getNomArticle().length() > 30)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_NOM_ARTICLE);
		}
		
		if(article.getDescriptionArticle().length()>300)
		{
			be.ajouterErreur(CodeResultatBLL.LONGUEUR_DESCRIPTION);
		}
		
	}
	
	public void verifierDates(BusinessException be,ArticlesVendus article) throws BusinessException{
		LocalDate dateDuJour = LocalDate.now();
		if(article.getDateDebutEnchere().isAfter(article.getDateFinEnchere()))
		{
			be.ajouterErreur(CodeResultatBLL.DATE_DEBUT_INCORRECTE);
		}
		if(article.getDateDebutEnchere().isBefore(dateDuJour))
		{
			be.ajouterErreur(CodeResultatBLL.DATE_DEBUT_ERRONEE);
		}
		
		if(article.getDateFinEnchere().isBefore(dateDuJour))
		{
			be.ajouterErreur(CodeResultatBLL.DATE_FIN_ERRONEE);
		}	
		
	}
	
	public void verifierPrixArticle (BusinessException be, ArticlesVendus article) throws BusinessException {
		if(article.getPrixInitial()<0) {
			be.ajouterErreur(CodeResultatBLL.PRIX_INIT_ARTICLE);
		}
	}
	
	
	public void ajouterArticle(ArticlesVendus article) throws BusinessException {
		BusinessException be = new BusinessException();
		verifierChampsVides(be,article);
		verifierLongueurChamp(be, article);
		verifierPrixArticle(be, article);
		verifierDates(be,article);
		
		if(!be.hasErreurs()) {
			this.articleDAO.insert(article);
		} else {
			throw be;
		}
	}
	
	public void supprimerArticle(int noArticle) throws BusinessException {
		this.articleDAO.delete(noArticle);
	}
	
	public void modifierArticle(ArticlesVendus article) throws BusinessException {
		BusinessException be = new BusinessException();
		verifierChampsVides(be,article);
		verifierLongueurChamp(be, article);
		verifierPrixArticle(be, article);
		//on veut le faire seulement si l'enchère n'a pas commencé
		//on doit récupérer la date de debut de base de l'enchère
		LocalDate dateDuJour = LocalDate.now();
		ArticlesVendus articleOriginal = selectArticle(article.getNoArticle());
		if(!dateDuJour.isAfter(articleOriginal.getDateDebutEnchere()))
		{verifierDates(be,article);}
		
		
		if(be.hasErreurs()) {
			throw be;
		}
		
		this.articleDAO.update(article); 
	}
	
	public ArticlesVendus selectArticle(int noArticle) throws BusinessException {
		return this.articleDAO.selectFromId(noArticle);
	}
	
	public ArticlesVendus selectByNomArticle(String nomArticle) throws BusinessException  {
		return this.articleDAO.selectByNom(nomArticle);
	}
	
	//retourne la liste d'ID de tous les articles
	public List<Integer> selectAllArticleId() throws BusinessException {
		return this.articleDAO.selectAllId();
	}
	
	//retourne la liste de tous les articles
	public List<ArticlesVendus> selectAllArticles() throws BusinessException {
		List<ArticlesVendus> listeAllArticles = new ArrayList<ArticlesVendus>();
		List<Integer> listeAllArticleIds = selectAllArticleId();
		
		for(int id :listeAllArticleIds) {
			ArticlesVendus a = selectArticle(id);
			listeAllArticles.add(a);
		}
		return listeAllArticles;
		
	}
	
	// Retourne la liste des numéros d'articles dont les enchères sont en cours
	public List<Integer> selectEncoursArticleId() throws BusinessException {
		List<Integer> listeArticleId = selectAllArticleId();
		LocalDate dateDuJour = LocalDate.now();
		List<Integer> listeIdEncours = new ArrayList<Integer>();
		
		for(int id : listeArticleId) {
			ArticlesVendus a = selectArticle(id);
			if(dateDuJour.isBefore(a.getDateFinEnchere()) && dateDuJour.isAfter(a.getDateDebutEnchere())) {
				listeIdEncours.add(id);
			}
		}
		return listeIdEncours;
	}
	
	// Retourne la liste des numéros d'articles dont les enchères terminées
	// ie la date du jour se trouve après la date de fin d'enchère
		public List<Integer> selectIdTerminees() throws BusinessException {
			List<Integer> listeArticleId = selectAllArticleId();
			LocalDate dateDuJour = LocalDate.now();
			List<Integer> listeIdTermines = new ArrayList<Integer>();
			
			for(int id : listeArticleId) {
				ArticlesVendus a = selectArticle(id);
				if(dateDuJour.isAfter(a.getDateFinEnchere())) {
					listeIdTermines.add(id);
				}
			}
			return listeIdTermines;
		}
	
	/*
	 * Fonctions et méthodes partie Achats	
	 */
		
	// Retourne la liste des articles dont les enchères sont ouvertes
	public List<ArticlesVendus> selectEncheresOuvertes() throws BusinessException {
		List<ArticlesVendus> listeEncheresOuvertes = new ArrayList<ArticlesVendus>();
		List<Integer> listeIdEncours = selectEncoursArticleId();
		
		for(int id :listeIdEncours) {
			ArticlesVendus a = selectArticle(id);
			if(a.getPrixVente()==0) {
				a.setPrixVente(a.getPrixInitial());
			}
			listeEncheresOuvertes.add(a);
			
		}
		return listeEncheresOuvertes;
	}
	
	// Retourne la liste des articles dont les enchères sont terminées
		public List<ArticlesVendus> selectEncheresTerminees() throws BusinessException {
			List<ArticlesVendus> listeEncheresTerminees = new ArrayList<ArticlesVendus>();
			List<Integer> listeIdTerminees = selectIdTerminees();
			
			for(int id :listeIdTerminees) {
				ArticlesVendus a = selectArticle(id);
				listeEncheresTerminees.add(a);
				
			}
			return listeEncheresTerminees;
		}
	
	
	/*
	// Retourne la liste des articles dont les enchères sont encours, et pour lesquels
	// l'utilisateur a fait au moins une offre
		public List<ArticlesVendus> selectMesEncheresEncours(Utilisateur utilisateurSession) throws BusinessException{
			// j'obtiens l'utilisateur connecté à ma session
			int utilisateurId = utilisateurSession.getNoUtilisateur();
			
			//je crée une liste d'articles vide
			List<ArticlesVendus> listeMesEncheresEncours = new ArrayList<ArticlesVendus>();
			
			//je récupère la liste des articles dont les enchères sont encours
			List<ArticlesVendus> listeEncheresOuvertes = selectEncheresOuvertes();
			
			//je boucle à travers la liste d'enchères en cours, et vais filter pour ne retenir
			// que les articles sur lesquels l'utilisateur a fait une enchère
			for(ArticlesVendus a : listeEncheresOuvertes) {
				if(utilisateurId == enchere.getNoUtilisateur()) {
						listeMesEncheresEncours.add(a);
				}
			}
			return listeMesEncheresEncours;
		}
		
		
	// Retourne la liste des articles que l'utilisateur a acquis (enchère remportée)
		public List<ArticlesVendus> selectMesEncheresRemportees(Utilisateur utilisateurSession) throws BusinessException{
			// j'obtiens l'utilisateur connecté à ma session
			int utilisateurId = utilisateurSession.getNoUtilisateur();
			
			//je crée une liste d'articles vide
			List<ArticlesVendus> listeMesEncheresRemportees = new ArrayList<ArticlesVendus>();
			
			//je récupère la liste des articles dont les enchères sont terminées
			List<ArticlesVendus> listeEncheresTerminees = selectEncheresTerminees();
			
			//critères pour remporter une enchère :
			//pour chaque article, regarder si l'ID utilisateur apparait sur la dernière enchère ?
			
			return listeMesEncheresRemportees;
		}
		*/
	/*
	 * Fonctions et méthodes partie Ventes
	 */
	
	// retourne la liste de toutes les ventes de l'utilisateur	
	public List<ArticlesVendus> selectMesVentes(Utilisateur utilisateurSession) throws BusinessException{
		// j'obtiens l'utilisateur connecté à ma session
		int utilisateurId = utilisateurSession.getNoUtilisateur();
		
		//je crée une liste d'articles vide
		List<ArticlesVendus> listeMesVentes = new ArrayList<ArticlesVendus>();
		
		//je récupère la liste de toutes les ventes
		List<ArticlesVendus> listeVentes = selectAllArticles();
		
		for(ArticlesVendus a :listeVentes) {
			if(a.getUtilisateur().getNoUtilisateur()==utilisateurId)
			listeMesVentes.add(a);
		}
		return listeMesVentes;
	}
	
	// retourne la liste de toutes les ventes en cours de l'utilisateur
	public List<ArticlesVendus> selectMesVentesEnCours(Utilisateur utilisateurSession) throws BusinessException{
		// je crée une liste des ID articles des ventes en cours
		List<Integer> listeVentesEnCoursId = selectEncoursArticleId();
		
		//je crée une liste d'articles vide
		List<ArticlesVendus> listeMesVentesEnCours = new ArrayList<ArticlesVendus>();
		
		//je récupère la liste de toutes mes ventes
		List<ArticlesVendus> listeMesVentes = selectMesVentes(utilisateurSession);
		
		//je parcours la liste de mes ventes, et pour chaque article de la liste, on vérifie 
		//si l'ID article est contenu dans la liste ventesEnCoursID 
		for(ArticlesVendus a :listeMesVentes) {
			if(listeVentesEnCoursId.contains(a.getNoArticle()))
				listeMesVentesEnCours.add(a);
		}
		return listeMesVentesEnCours;
	}	
	
	//retourne la liste des ventes non débutées de l'utilisateur
	public List<ArticlesVendus> selectMesVentesNonDebutees (Utilisateur utilisateurSession) throws BusinessException{
		
		List<ArticlesVendus> listeMesVentesNonDebutees = new ArrayList<ArticlesVendus>();
		List<ArticlesVendus> listeMesVentes = selectMesVentes(utilisateurSession);
		LocalDate dateDuJour = LocalDate.now();
		
		for(ArticlesVendus a : listeMesVentes) {
			if(dateDuJour.isBefore(a.getDateDebutEnchere())) {
				listeMesVentesNonDebutees.add(a);
			}
		}
		return listeMesVentesNonDebutees;
	}
	
	//retourne la liste des ventes terminées de l'utilisateur
		public List<ArticlesVendus> selectMesVentesTerminees (Utilisateur utilisateurSession) throws BusinessException{
			List<ArticlesVendus> listeMesVentesTerminees = new ArrayList<ArticlesVendus>();

			List<Integer> listeVentesTermineesId = selectIdTerminees();
			List<ArticlesVendus> listeMesVentes = selectMesVentes(utilisateurSession);
			
			for(ArticlesVendus a : listeMesVentes) {
				if(listeVentesTermineesId.contains(a.getNoArticle())) {
					listeMesVentesTerminees.add(a);
				}
			}
			return listeMesVentesTerminees;
		}
}
