package fr.eni.encheres.ihm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.ArticlesVendus;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/nouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//je mets en place l'attribut date actuelle pour avoir la date du jour
		request.setAttribute("dateDuJour",LocalDate.now());
		
		// délégation de l'affichage à la JSP
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/NouvelleVente.jsp");
				rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//on stocke l'utilisateur connecté dans la session sous le nom "utilisateurConnecte"
		Utilisateur utilisateurConnecte = (Utilisateur) request.getSession().getAttribute("utilisateur");
		
		// je récupère l'action souhaitée : créer
		String action = request.getParameter("action");
		
		try {
			//je crée un objet de type articleManager pour accéder ensuite aux méthodes d'articleManager
			ArticleManager articleManager = new ArticleManager(); 
			
			//je récupère les informations saisies dans le formulaire nouvelle vente
			String articleEnVente = request.getParameter("nouvelArticle");
			String descriptionArticle = request.getParameter("descriptionArticle");
			int categorieArticle = Integer.parseInt(request.getParameter("categorie")); 
			int miseAPrix = Integer.parseInt(request.getParameter("miseAPrix")); 
			
			//pour les dates de début et fin d'enchères, on doit les récupérer puis les convertir en objets LocalDate
			String debutEnchereString = request.getParameter("debutEnchere");
			String finEnchereString = request.getParameter("finEnchere");
			
			LocalDate debutEnchere = LocalDate.parse(debutEnchereString);
			LocalDate finEnchere = LocalDate.parse(finEnchereString);
			
			//j'alimente mon nouvel objet article
			ArticlesVendus article = new ArticlesVendus();
			
			
			article.setNomArticle(articleEnVente);
			article.setDescriptionArticle(descriptionArticle);
			article.setDateDebutEnchere(debutEnchere);
			article.setDateFinEnchere(finEnchere);
			article.setPrixInitial(miseAPrix);
			article.setPrixVente(0);
			article.setCategorieArticle(categorieArticle);
			article.setUtilisateur(utilisateurConnecte);
			
			//appel des méthodes d'articleManager en fonction de l'action effectuée sur le formulaire
			if("Enregistrer".equals(action)) 
			{
				articleManager.ajouterArticle(article);
				
			}			
			request.setAttribute("articleposte", "Votre article a bien été mis en ligne.");
			
			}
			catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/NouvelleVente.jsp");
			rd.forward(request, response);
		}
		
		doGet(request,response);
		
	

}
}
