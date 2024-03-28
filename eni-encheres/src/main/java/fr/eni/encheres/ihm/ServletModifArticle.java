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
 * Servlet implementation class ServletModifArticle
 */
@WebServlet("/modifArticle")
public class ServletModifArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// récupération de l'ID article à modifier depuis la requête
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		
		System.out.println(idArticle);
		
		// récupérer l'article à partir de l'ID
		ArticleManager articleManager = new ArticleManager();
		try {
			ArticlesVendus articleAModifier = articleManager.selectArticle(idArticle);
			request.setAttribute("articleAModifier", articleAModifier);
			System.out.println();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// délégation de l'affichage à la JSP
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ModifArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		// je récupère l'action souhaitée : modifier, supprimer
		String action = request.getParameter("action");
		
		try {
			ArticleManager articleManager = new ArticleManager();
			
			// on récupère l'article existant à partir de la bdd
			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			if (request.getParameter("idArticle") != null) {
				System.out.println(request.getParameter("idArticle"));
			}
			
			ArticlesVendus articleAModif = articleManager.selectArticle(idArticle);
			
			// mise à jour des propriétés de mon objet articleAModif
			articleAModif.setNoArticle(idArticle);
			articleAModif.setNomArticle(request.getParameter("nouvelArticle"));
			articleAModif.setDescriptionArticle(request.getParameter("descriptionArticle"));
			articleAModif.setCategorieArticle(Integer.parseInt(request.getParameter("categorie")));
			articleAModif.setPrixInitial(Integer.parseInt(request.getParameter("miseAPrix")));
			articleAModif.setPrixVente(0);
			articleAModif.setDateDebutEnchere(LocalDate.parse(request.getParameter("debutEnchere")));
			articleAModif.setDateFinEnchere(LocalDate.parse(request.getParameter("finEnchere")));

			// mise à jour de l'article dans la bdd
			if("Modifier".equals(action)) 
			{
				articleManager.modifierArticle(articleAModif);
				request.setAttribute("articleposte", "Votre article a bien été mis à jour.");
			}
			
			// suppression de l'article dans la bdd
			if("Supprimer".equals(action)) 
			{
				articleManager.supprimerArticle(idArticle);
				request.setAttribute("articleposte", "Votre article a bien été supprimé.");
			}


		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ModifArticle.jsp");
			rd.forward(request, response);
		}

		doGet(request, response);
	}

}
